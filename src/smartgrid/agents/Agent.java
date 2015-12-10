package smartgrid.agents;

import java.util.ArrayList;

import smartgrid.utilities.SmartPrint;
import smartgrid.web.bridge.SmartGridDriver;

public abstract class Agent {
	private transient ArrayList<Agent> buysFrom = new ArrayList<Agent>();
	private transient ArrayList<Agent> sellsTo = new ArrayList<Agent>();
	double quantity=0;
	double priceSum=0;//accumulation of all prices in this instance of time TODO rethink design later
	double[][] avgPrices = new double[24][SmartGridDriver.getGlobal('D')];
	double avgPrice=0;  
	double lastPriceDifference=.005; //Must be less than .8 recommended .005
	double bidRatio = .1;
	protected String name;
	transient SmartPrint smartPrint = SmartPrint.getInstance();
	
	
	public Agent(String name){
		this.buysFrom=new ArrayList<Agent>();
		this.sellsTo=new ArrayList<Agent>();
		this.name=name;
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
	
	public void stepBegin(){
		smartPrint.println(0,"Warning: Agent superclass does not have a defined action [stepBegin(int t) method].");
	}
	
	public void stepEnd(){
		smartPrint.println(0,"Warning: Agent superclass does not have a defined action [stepEnd(int t) method].");
	}
	
	public double[][] getPriceMatrix(){
		return this.avgPrices;
	}
	
	public void setPrice(int time, int day, double avgPrice){
		this.avgPrices[time][day]=avgPrice;
	}
	
	public void addExchange( double quantity, double price){
		this.priceSum+=(price*quantity);
		this.quantity+=quantity;
	}
	
	public double getAvgPrice(){
		return (this.priceSum)/(this.quantity);
	}
	
	public void resetExchange(){
		this.priceSum=0;
		this.quantity=0;
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
