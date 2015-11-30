package smartgrid.web.bridge;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import smartgrid.agents.*; 

public class WebSync {
	ArrayList<Agent> agents = new ArrayList<Agent>();

	public WebSync(ArrayList<Agent> agents){
	 this.agents=agents;
	}
	
	public void writeJS() throws FileNotFoundException, UnsupportedEncodingException{
		ArrayList<Agent> connAgents=new ArrayList<Agent>();
		PrintWriter writer = new PrintWriter("WebContent\\js\\gridBuilder.js", "UTF-8");
		String nodes="";
		String edges="";
		writer.println("document.addEventListener('DOMContentLoaded', function(){ var cy = cytoscape({ container: document.querySelector('#cy'), boxSelectionEnabled: false, autounselectify: true, style: cytoscape.stylesheet() .selector('node') .css({ 'content': 'data(id)', width: '60px', height: '60px', 'text-valign': 'center', 'color': 'white', 'text-outline-width': 2, 'text-outline-color': '#888', 'pie-size': '80%', 'pie-1-background-color': '#E8747C', 'pie-1-background-size': 'mapData(foo, 0, 10, 0, 100)', 'pie-2-background-color': '#74CBE8', 'pie-2-background-size': 'mapData(bar, 0, 10, 0, 100)', 'pie-3-background-color': '#74E883', 'pie-3-background-size': 'mapData(baz, 0, 10, 0, 100)' }) .selector('edge') .css({ 'target-arrow-shape': 'triangle' }) .selector(':selected') .css({ 'background-color': 'black', 'line-color': 'green', 'target-arrow-color': 'green', 'source-arrow-color': 'green' }) .selector('.faded') .css({ 'opacity': 0.15, 'text-opacity': 0 }),");
		for(int i=0;i<this.agents.size();i++){
			Agent agent = this.agents.get(i);
			nodes+="{ data: { id: '"+agent.getName()+"', foo: 3, bar: 3, baz: 4 } },\n";
			connAgents=agent.getBuysFrom();
			for(int j=0;j<connAgents.size();j++){
				edges+="{ data: { id: 'link"+i+""+j+"', weight: 1, source: '"+connAgents.get(j).getName()+"', target: '"+agent.getName()+"' } },\n";
			}
		}
		writer.println("elements: {nodes:["+nodes+"],");
		writer.println("edges:["+edges+"]},");
		writer.println("layout: { /*ring, grid and*/ name: 'grid', padding: 10 } }); cy.on('tap', 'node', function(e){ var node = e.cyTarget; var neighborhood = node.neighborhood().add(node); cy.elements().addClass('faded'); neighborhood.removeClass('faded'); }); cy.on('tap', function(e){ if( e.cyTarget === cy ){ cy.elements().removeClass('faded'); } }); }); /* on dom ready*/");
		writer.close();
	}
	
	//Reset and start method
	public void intializeNodes(){
		//Runs every time program is reset
		//intialize all the nodes and connections
	}
	
	
	
	public void updateNodes(){
		//runs every time the program completes a scheduled run (skip hour, skip day, skip to, pause)
		//updates buySell Ratio's, buyPrice's, sellPrice's
	}
	
	public void getNodeDetails(String agentId){
		//use agentID to 
	}
}