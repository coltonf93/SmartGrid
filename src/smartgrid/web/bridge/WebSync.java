package smartgrid.web.bridge;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import smartgrid.agents.*;
import smartgrid.utilities.SmartPrint; 

public class WebSync {
	ArrayList<Agent> agents = new ArrayList<Agent>();

	public WebSync(ArrayList<Agent> agents){
	 this.agents=agents;
	}
	DecimalFormat df = new DecimalFormat("#####.##");
	public void writeJS(int day, int hour) throws FileNotFoundException, UnsupportedEncodingException{
		ArrayList<Agent> connAgents=new ArrayList<Agent>();
		SmartPrint smartPrint = SmartPrint.getInstance();
		PrintWriter writer = new PrintWriter("WebContent\\js\\gridBuilder.js", "UTF-8");
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
		docReady+="$('#cCount').text('"+cons+"');$('#gCount').text('"+gen+"');$('#sCount').text('"+(st-1)+"');$('#dCount').text('"+day+"');";
		writer.println("elements: {nodes:["+nodes+"],");
		writer.println("edges:["+edges+"]},");
		writer.println("layout: { /*ring, grid and*/ name: 'grid', padding: 10 } }); cy.on('tap', 'node', function(e){ var node = e.cyTarget; var neighborhood = node.neighborhood().add(node); cy.elements().addClass('faded'); neighborhood.removeClass('faded'); }); cy.on('tap', function(e){ if( e.cyTarget === cy ){ cy.elements().removeClass('faded'); } }); });");
		writer.println("$( document ).ready(function() {"+docReady+"});");
		
		writer.close();
	}
	
	//Reset and start method
	public void intializeNodes(){
		
	}
	
	
	
	public void updateNodes(){
		//runs every time the program completes a scheduled run (skip hour, skip day, skip to, pause)
		//updates buySell Ratio's, buyPrice's, sellPrice's
	}
	
	public void getNodeDetails(String agentId){
		//use agentID to 
	}
}