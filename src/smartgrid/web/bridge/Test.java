package smartgrid.web.bridge;

import java.util.ArrayList;

import smartgrid.agents.Agent;
//this class is used to organize all the data in preparation of the json serialization
public class Test {
	Configuration configs;
	ArrayList<Agent> agents;
	ArrayList<String[]> links;
	public Test(Configuration configs, ArrayList<Agent> agents, ArrayList<String[]> links){
		this.configs=configs;
		this.links=links;
		this.agents=agents;
	}
}
