package smartgrid.web.bridge;
import java.util.ArrayList;
import smartgrid.agents.*; 

public class WebSync {
	ArrayList<Agent> agents = new ArrayList<Agent>();
	
	public WebSync(ArrayList<Agent> agents){
	 this.agents=agents;
	}
	
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