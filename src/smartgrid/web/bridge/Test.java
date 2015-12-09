package smartgrid.web.bridge;

import java.util.ArrayList;

import smartgrid.agents.Agent;
//this class is used to organize all the data in preperation for the json serialization
public class Test {
	int days;
	int cCount;
	int wCount;
	int sCount;
	int stCount;
	double connectivity;
	String testName;
	String description;
	ArrayList<Agent> agents;
	ArrayList<String[]> links;
	public Test(String testName, String description, ArrayList<Agent> agents, int days, int cCount, int wCount, int sCount, int stCount, double connectivity, ArrayList<String[]> links, double[] cCons, double[] wGen, double[] sGen ){
		this.days=days;
		this.cCount=cCount;
		this.wCount=wCount;
		this.sCount=sCount;
		this.stCount=stCount;
		this.agents=agents;
		this.testName=testName;
		this.description=description;
		this.connectivity=connectivity;
		this.links=links;
	}
}
