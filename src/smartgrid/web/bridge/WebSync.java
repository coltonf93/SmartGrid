package smartgrid.web.bridge;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.corba.se.impl.orbutil.ObjectWriter;

import smartgrid.agents.*;
import smartgrid.utilities.SmartPrint; 

public class WebSync {
	ArrayList<Agent> agents = new ArrayList<Agent>();
	SmartPrint smartPrint = SmartPrint.getInstance();
	public WebSync(ArrayList<Agent> agents){
	 this.agents=agents;
	}
	DecimalFormat df = new DecimalFormat("#####.##");
	public void writeJS() throws FileNotFoundException, UnsupportedEncodingException{
		System.out.println("write js start");
		ArrayList<Agent> connAgents=new ArrayList<Agent>();
		SmartPrint smartPrint = SmartPrint.getInstance();
		File f = new File(getServletContext().getRealPath("js/gridBuilder.js"));
		PrintWriter writer = new PrintWriter(getServletContext().getRealPath("js/gridBuilder.js"));
		String nodes="";
		String docReady="";
		String edges="";
		int st=0,gen=0,cons=0;
		writer.println("document.addEventListener('DOMContentLoaded', function(){ var cy = cytoscape({ container: document.querySelector('#cy'), boxSelectionEnabled: false, zoomingEnabled: false, panningEnabled: true, autounselectify: true, style: cytoscape.stylesheet() .selector('node') .css({ 'content': 'data(id)', width: '60px', height: '60px', 'text-valign': 'center', 'color': '#333', 'text-outline-width': 2, 'text-outline-color': 'white', 'pie-size': '80%', 'pie-1-background-color': '#E8747C', 'pie-1-background-size': 'mapData(red, 0, 10, 0, 100)', 'pie-2-background-color': '#74E883', 'pie-2-background-size': 'mapData(green, 0, 10, 0, 100)' }) .selector('edge') .css({ 'target-arrow-shape': 'triangle' }) .selector(':selected') .css({ 'background-color': 'black', 'line-color': 'black', 'target-arrow-color': 'green', 'source-arrow-color': 'green' }) .selector('.faded') .css({ 'opacity': 0.08, 'text-opacity': 0 }),");
		for(int i=0;i<this.agents.size();i++){
			Agent agent = this.agents.get(i);
			connAgents=agent.getBuysFrom();
			if(agent instanceof Sellers & agent instanceof Buyers){//main grid or grid storage
				docReady+="$('#agentData tbody').append('<tr id=\"tid"+agent.getName()+"\"><td>"+agent.getName()+"</td><td>$"+df.format(((Buyers)agent).getBuyPrice())+"</td><td>$"+df.format(((Sellers)agent).getSellPrice())+"</td><td>$"+df.format((((Sellers)agent).getDailyProfit()-((Buyers)agent).getDailyExpense()))+"</td><td>$"+df.format((((Sellers)agent).getProfit()-((Buyers)agent).getExpense()))+"</td></tr>');\n";
				nodes+="{ data: { id: '"+agent.getName()+"', red: 5, green: 5 } },\n";
				st++;
			}
			else if(agent instanceof Sellers){
				docReady+="$('#agentData tbody').append('<tr id=\"tid"+agent.getName()+"\"><td>"+agent.getName()+"</td><td>$0.00</td><td>$"+df.format(((Sellers)agent).getSellPrice())+"</td><td>$"+df.format(((Sellers)agent).getDailyProfit())+"</td><td>$"+df.format(((Sellers)agent).getProfit())+"</td></tr>');\n";
				nodes+="{ data: { id: '"+agent.getName()+"', red: 0, green: 10 } },\n";
				gen++;
			}
			else if(agent instanceof Buyers){
				docReady+="$('#agentData tbody').append('<tr id=\"tid"+agent.getName()+"\"><td>"+agent.getName()+"</td><td>$"+df.format(((Buyers)agent).getBuyPrice())+"</td><td>$0.00</td><td>-$"+df.format(((Buyers)agent).getDailyExpense())+"</td><td>-$"+df.format(((Buyers)agent).getExpense())+"</td></tr>');\n";
				nodes+="{ data: { id: '"+agent.getName()+"', red: 10, green: 0 } },\n";
				cons++;
			}
			else{
				smartPrint.println(0,"Warning: Agent detected that is not buyer or seller.");
			}
			for(int j=0;j<connAgents.size();j++){
				edges+="{ data: { id: 'link"+i+""+j+"', weight: 1, source: '"+connAgents.get(j).getName()+"', target: '"+agent.getName()+"' } },\n";
			}
		}
		docReady+="$('#cCount').text('"+cons+"');$('#gCount').text('"+gen+"');$('#sCount').text('"+(st-1)+"');$('#dCount').text('"+SmartGridDriver.getGlobal('D')+"');";
		writer.println("elements: {nodes:["+nodes+"],");
		writer.println("edges:["+edges+"]},");
		writer.println("layout: { /*ring, grid and*/ name: 'grid', padding: 10 } }); cy.on('tap', 'node', function(e){ var node = e.cyTarget; var neighborhood = node.neighborhood().add(node); cy.elements().addClass('faded'); neighborhood.removeClass('faded'); }); cy.on('tap', function(e){ if( e.cyTarget === cy ){ cy.elements().removeClass('faded'); } }); });");
		writer.println("$( document ).ready(function() {"+docReady+"});");
		
		writer.close();
	}
	
	public void loadTest(){
		
	}
	
	public void saveTest(String testName, String description, int cCount, int wCount, int sCount, int stCount, double connectivity) throws IOException{
		smartPrint.println(8,"Saving Test to json file.");
		System.out.println("save test start");
		//Hackish workaround to solve the cyclic serilization issue
		ArrayList<String[]>links = new ArrayList<String[]>();
		for(int i=0;i<agents.size();i++){
			ArrayList<Agent>sellers=agents.get(i).getBuysFrom();
			for(int j=0;j<sellers.size();j++){
				String[] temp=new String[2];
				temp[0]=agents.get(i).getName();
				temp[1]=sellers.get(j).getName();
				links.add(temp);
			}
		}
		
		String fileName="";
		Gson gson = new GsonBuilder().setExclusionStrategies(new SerializationExclusion()).create();
		try {
			//TODO prevent files from being overwritten because config file will still show them
			testName=testName.replace(" ", "_");
			File f = new File("WebContent\\tests\\"+testName+".json");
			if(f.exists() && !f.isDirectory()) { 
			    int i=-1;
			    while(f.exists() && !f.isDirectory())
			    {
			    	i++;
			    	f=new File("WebContent\\tests\\"+testName+"("+i+").json");	
			    }
			    testName=testName+"("+i+")";
			}
			Test test = new Test(testName, description, agents, SmartGridDriver.getGlobal('D'), cCount, wCount, sCount, stCount, connectivity, links );
			String json =gson.toJson(test);// gson.toJson(agents);
			FileWriter writer = new FileWriter("WebContent\\tests\\"+testName+".json");
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			smartPrint.println(0,"Error Saving the Json file.");
			e.printStackTrace();
		}
		//Updates the test.config file so tests can be loaded dynamically
		String testConfigString=testName+","+description+","+cCount+","+sCount+","+wCount+","+stCount+","+SmartGridDriver.getGlobal('D');
		File testConfig = new File("WebContent\\tests\\tests.config");
	    BufferedWriter configwriter = new BufferedWriter(new FileWriter(testConfig,true));
	    configwriter.write(testConfigString);
	    configwriter.newLine();
	    configwriter.close();
		smartPrint.println(8,"Done Saving");
		
	}
	
	public void updateNodes(){
		//runs every time the program completes a scheduled run (skip hour, skip day, skip to, pause)
		//updates buySell Ratio's, buyPrice's, sellPrice's
	}
	
	public void getNodeDetails(String agentId){
		//use agentID to 
	}

}

class SerializationExclusion implements ExclusionStrategy {

   @Override
	public boolean shouldSkipField(FieldAttributes fa) {                
        String className = fa.getDeclaringClass().getName();
        String fieldName = fa.getName();
        return 
            ((className.equals("smartgrid.agents.MainGrid")
                && fieldName.equals("buysFrom")) || (className.equals("smartgrid.agents.MainGrid")
                && fieldName.equals("sellsTo")));
    }

    @Override
    public boolean shouldSkipClass(Class<?> type) {
        // never skips any class
        return false;
    }
}