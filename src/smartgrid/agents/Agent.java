package smartgrid.agents;

import java.util.ArrayList;

import smartgrid.utilities.SmartPrint;

public abstract class Agent {
	protected ArrayList<Agent> buysFrom = new ArrayList<Agent>();
	protected ArrayList<Agent> sellsTo = new ArrayList<Agent>();
	int exchanges=0;//how many exchanges this agent made in this instance of time TODO rethink design later
	double priceSum=0;//accumulation of all prices in this instance of time TODO rethink design later
	double[] lastPrices = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};//The average price power was exchanged yesterday at this time for this agent
	double[] lastPrices2 = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};//The average price power was exchanged for two days ago at this time for this agent
	double avgPrice=0;  
	double lastPriceDifference=.005; //Must be less than .8 recommended .005
	double bidRatio = .1; 
	protected String name;
	SmartPrint smartPrint;
	
	public Agent(String name){
		this.buysFrom=new ArrayList<Agent>();
		this.sellsTo=new ArrayList<Agent>();
		this.name=name;
		smartPrint=smartPrint.getInstance();
	}
	
	public ArrayList<Agent> getBuysFrom(){
		return this.buysFrom; 
	}
	
	public ArrayList<Agent> getSellsTo(){
		return this.sellsTo;
	}
	
	public void setSellTo(Agent agent){
		smartPrint.println(1, this.name+" can now sell to "+agent.name);
		sellsTo.add(agent);
	}
	
	public void setBuyFrom(Agent agent){
		smartPrint.println(1, this.name+" can now buy from "+agent.name);
		buysFrom.add(agent);
	}
	
	public String getName(){
		return this.name;
	}
	
	public void stepBegin(int t){
		smartPrint.println(0,"Warning: Agent superclass does not have a defined action [stepBegin(int t) method].");
	}
	
	public void stepEnd(int t){
		smartPrint.println(0,"Warning: Agent superclass does not have a defined action [stepEnd(int t) method].");
	}
	
	public double getLastPrice(int t){
		return this.lastPrices[t];
	}
	
	public double getLastPrice2(int t){
		return this.lastPrices2[t];
	}
	
	public double[] getLastPrices(){
		return this.lastPrices;
	}
	
	public double[] getLastPrices2(){
		return this.lastPrices2;
	}
	
	public int getExchangeCount(){
		return exchanges;
	}
	
	//BEGIN OF SLOPPY CODE RETHINK DESIGN
	public void setExchangeCount(int count){
		this.exchanges=count;
	}
	
	public double getPriceSum(){
		return this.priceSum;
	}
	
	public void setPriceSum(double price){
		this.priceSum=price;
	}
	
	public void setAvgPrice(double avgPrice){
		this.avgPrice=avgPrice;
	}
	
	public double getAvgPrice(){
		return avgPrice;
	}
	
	public boolean isConnected(Agent agent){//TODO inefficient consider a refactor.
		for(int i=0;i<buysFrom.size();i++){
			if(agent.getName().equals(buysFrom.get(i).getName())){
				return true;
			}
		}
		for(int i=0;i<sellsTo.size();i++){
			if(agent.getName().equals(sellsTo.get(i).getName())){
				return true;
			}
		}
		return false;
	}
	//END OF SLOPPY CODE RETHINK DESIGN
	
}
