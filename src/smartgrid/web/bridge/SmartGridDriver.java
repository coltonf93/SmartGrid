package smartgrid.web.bridge;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import smartgrid.agents.*;
import smartgrid.utilities.*;
import smartgrid.web.bridge.WebSync;

public class SmartGridDriver{
	private static int days=1000;
	private static int d=0;
	private static int t=0;
	static SmartPrint smartPrint=SmartPrint.getInstance();
	public static void main(String [] args){
		
		int solarCount=6;
		int windCount=6;
		int consumerCount=10;
		int storageCount=10;
		double connectivity=0.2;//Computes to about 50%
		double[] consumerConsumption={.5,.45,.4,.4,.4,.42,.43,.48,.55,.72,.85,.9,.92,1,1,1,1,.95,.97,.95,.8,.6,.5,.45};
		double[] solarGeneration={.0,.0,.0,.0,.0,.05,.2,.3,.38,.38,.7,1,.9,1,.6,.62,.3,.1,.01,.0,.0,.0,.0,.0};
		double[] windGeneration={.5,.5,.7,.2,.61,.4,.38,.1,.27,.27,.2,.2,.2,.2,.38,.42,.7,.4,.38,.42,.4,.38,.43,.4};
		Consumer.setStartBuyBid(.01);
		SolarPower.setStartSellBid(1);
		WindPower.setStartSellBid(1);
		GridStorage.setStartBuyBid(.01);
		GridStorage.setStartSellBid(1);
		smartPrint.enableTypes(new int[] {0,1,6});//Modify this to show different print statements, recommend to leave 0 and 7 on
		
		
		
		AuctionMaster ac = new AuctionMaster();
		smartPrint.enableTypes(new int[] {0,1,6});//Modify this to show different print statements, recommend to leave 0 and 7 on

		DecimalFormat df = new DecimalFormat("#####.####");
		Random rand=new Random();
		ArrayList<Agent> generators=new ArrayList<Agent>();
		ArrayList<Agent> consumers = new ArrayList<Agent>();
		ArrayList<Agent> solar = new ArrayList<Agent>();
		ArrayList<Agent> wind = new ArrayList<Agent>();
		ArrayList<Agent> storage = new ArrayList<Agent>();
		ArrayList<Agent> allAgents = new ArrayList<Agent>();

		SolarPower.setGeneration(solarGeneration,0);
		WindPower.setGeneration(windGeneration,0);
		Consumer.setConsumption(consumerConsumption,0);
		WebSync webSync = new WebSync(allAgents);
		smartPrint.println(4,"Building the Main Grid");
		//TODO make MainGrid configurable from the web interface that is to come
		MainGrid mainGrid = new MainGrid(.01,1.0);
		allAgents.add(mainGrid);//creates a main grid with unlimited supply and demand buy:.05 and sell .30
		smartPrint.println(4,"Building Consumers.");
		//Creates all the consumers
		for(int i=0;i<consumerCount;i++){
			consumers.add(new Consumer("Consumer "+(i+1)));
			allAgents.add(consumers.get(i));
		}
		smartPrint.println(4,"Building Solar Generators");
		
		//Creates all the solar generators and links to all consumers
		
		for(int i=0;i<solarCount;i++){
			solar.add(new SolarPower("Solar "+(i+1)));
			generators.add(solar.get(i));
			allAgents.add(solar.get(i));
		}
		smartPrint.println(4,"Building Wind Generators and linking to Consumers.");
		//Creates all the wind generators
		for(int i=0;i<windCount;i++){
			wind.add(new WindPower("Wind "+(i+1)));
			generators.add(wind.get(i));
			allAgents.add(wind.get(i));
		}
		smartPrint.println(4,"Building Grid Stroage's and linking to Consumers, Wind Generators, and Solar Generators.");
		//Creates all the storage facilities and links to all consumers and power generators
		for(int i=0;i<storageCount;i++){
			storage.add(new GridStorage("Storage "+(i+1),5,30,.2));
			allAgents.add(storage.get(i));
		}
		ConnectionBuilder connBuild=new ConnectionBuilder(connectivity,generators,consumers,storage,mainGrid);
		//EXPERIMENTAL FEATURE
		//smartPrint.println(4,"Linking all storage units to eachother.");
		/*for(int i=0;i<storageCount;i++){
			for(int j=0;j<storageCount;j++){
				if(i!=j){
					storage.get(i).setBuyFrom(storage.get(j));
					storage.get(j).setBuyFrom(storage.get(i));
					storage.get(i).setSellTo(storage.get(j));
					storage.get(j).setSellTo(storage.get(i));
				}
			}
		}*/
		WebSync ws = new WebSync(allAgents);
		//Adds all buy capable agents to buy list
		ac.addBuyers((Collection<? extends Agent>)consumers);//adds all the consumers to the list of buyers
		ac.addBuyers((Collection<? extends Agent>)storage);//adds all the storage units to the list of buyers
		ac.addBuyer(mainGrid);
		//PROCESS THE STEP FUNCTIONS FOR ALL AGENTS
		for(d=0;d<days;d++){
			smartPrint.println(7,"\nBegin Day "+d+": \n=========================================================\n=========================================================\n");
			for(t=0;t<24;t++){
				smartPrint.println(7,"\nBegin Hour "+t+": \n=========================================================");
				//Process' all agent begin actions
				for(int a=0;a<allAgents.size();a++){
					allAgents.get(a).stepBegin();
				}
				ac.processExchanges();//Runs all of the auction and buy sell exchanges for agents
		
				//TODO remove if statement and nested loops when done with analysis
				/*
				if(d==days-1&&t==23 || d==0 && t==23){
					System.out.print("Buyers[Day:"+d+", Hour:"+t+"]:\n==========================================================");
					for(int i=0;i<buyers.size();i++){
						buyer=((Buyers)buyers.get(i));
						System.out.println("\n"+buyer.getName()+"($"+df.format(buyer.getBuyPrice())+") Possible Sellers:\n--------------------------------");
						for(int j=0;j<sellers.size();j++){
							seller=(Sellers)sellers.get(j);
							if(seller.getSellPower()>0){
								System.out.println(seller.getName()+"($"+df.format(seller.getSellPrice())+")");
							}
						}	
					}
				}*/
				
				
				
				
				
				smartPrint.println(4,"\nHour("+t+") Hourly Totals\n----------------------------------------");
				for(int i=0;i<consumers.size();i++){
					Consumer consumerA = (Consumer)consumers.get(i);
					smartPrint.println(4,consumerA.getName()+" earned a hourly total of -"+consumerA.getHourlyExpense()+".");
				}
				
				for(int i=0;i<wind.size();i++){
					WindPower windA = (WindPower)wind.get(i);
					smartPrint.println(4,windA.getName()+" earned an hourly total of "+windA.getHourlyProfit()+".");
				}
				
				for(int i=0;i<solar.size();i++){
					SolarPower solarA = (SolarPower)solar.get(i);
					smartPrint.println(4,solarA.getName()+" earned an hourly total of "+solarA.getHourlyProfit()+".");
				}
				
				for(int i=0;i<storage.size();i++){
					GridStorage storageA = (GridStorage)storage.get(i);
					smartPrint.println(4,storageA.getName()+" earned an hourly total of "+storageA.getHourlyProfit()+" and spent an hourly total of "+storageA.getHourlyExpense()+" netting "+storageA.getHourlyNetProfit()+".");
				}	
				smartPrint.println(4,mainGrid.getName()+" earned an hourly total of "+mainGrid.getHourlyProfit()+" and spent an hourly total of "+mainGrid.getHourlyExpense()+" netting "+mainGrid.getHourlyNetProfit()+".");
				
				for(int a=0;a<allAgents.size();a++){
					Agent agent=allAgents.get(a);
					if(agent.getExchangeCount()>=1){
						agent.setAvgPrice((double)(agent.getPriceSum()/agent.getExchangeCount()));
					}
					else{
						agent.setAvgPrice(0);
					}
					//TODO sloppy code start refactor resets the exchange counter and summation
					//System.out.println(agent.getName()+" exchanged with "+agent.getExchangeCount()+" other's with a price sum of "+agent.getPriceSum());
					agent.setPriceSum(0);
					agent.setExchangeCount(0);
					//sloppy code end refactor
					agent.stepEnd();
				}
				
			}
			//TODO For each expense and profit, create a daily profit and daily reward that resets when t=0, the other accumulate the entire time
			smartPrint.println(5,"\nDay("+d+") Daily Totals\n========================================");
			for(int i=0;i<consumers.size();i++){
				Consumer consumerA = (Consumer)consumers.get(i);
				smartPrint.println(5,consumerA.getName()+" earned a daily total of -"+consumerA.getDailyExpense()+" today.");
			}
			
			for(int i=0;i<wind.size();i++){
				WindPower windA = (WindPower)wind.get(i);
				smartPrint.println(5,windA.getName()+" earned a daily total of "+windA.getDailyProfit()+" today.");
			}
			
			for(int i=0;i<solar.size();i++){
				SolarPower solarA = (SolarPower)solar.get(i);
				smartPrint.println(5,solarA.getName()+" earned a daily total of "+solarA.getDailyProfit()+" today.");
			}
			
			for(int i=0;i<storage.size();i++){
				GridStorage storageA = (GridStorage)storage.get(i);
				smartPrint.println(5,storageA.getName()+" earned a daily total of "+storageA.getDailyProfit()+" and spent a daily total of "+storageA.getDailyExpense()+" today netting "+storageA.getDailyNetProfit()+".");
			}	
			smartPrint.println(5,mainGrid.getName()+" earned a daily total of "+mainGrid.getDailyProfit()+" and spent a daily total of "+mainGrid.getDailyExpense()+" today netting "+mainGrid.getDailyNetProfit()+".");
	
		}
		
		
		//GlobalPrint Totals
		smartPrint.println(6,"\nGlobal Totals over "+days+" days\n========================================");
		for(int i=0;i<consumers.size();i++){
			Consumer consumerA = (Consumer)consumers.get(i);
			smartPrint.println(6,consumerA.getName()+" earned a total of -"+df.format(consumerA.getExpense())+" and had a final price array of: ");
		}
		
		for(int i=0;i<wind.size();i++){
			WindPower windA = (WindPower)wind.get(i);
			smartPrint.println(6,windA.getName()+" earned a total of "+df.format(windA.getProfit())+".");
		}
		
		for(int i=0;i<solar.size();i++){
			SolarPower solarA = (SolarPower)solar.get(i);
			smartPrint.println(6,solarA.getName()+" earned a total of "+df.format(solarA.getProfit())+".");
		}
		
		for(int i=0;i<storage.size();i++){
			GridStorage storageA = (GridStorage)storage.get(i);
			smartPrint.println(6,storageA.getName()+" earned a total of "+df.format(storageA.getProfit())+" and spent a total of "+df.format(storageA.getExpense())+" today netting "+df.format(storageA.getNetProfit())+".");
		}
		
		smartPrint.println(6,mainGrid.getName()+" earned a total of "+df.format(mainGrid.getProfit())+" and spent a total of "+df.format(mainGrid.getExpense())+" today netting "+df.format(mainGrid.getNetProfit())+".");
	    //Writes a js file for graphics rendering
		try {
			ws.writeJS((days-1),23);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}	
	public static int getGlobal(char c){
		if(c=='d'){//return current day
			return d;
		}
		else if(c=='t'){//return current time
			return t;
		}
		else if(c=='D'){//return  the total number of days
			return days+1;
		}
		smartPrint.println(0, "Error: SmartGridDriver.getGlobal('"+c+"'); not found.");
		return -1;
	}
}
//TODO Consider: randomize the order of agents with the same price.
/*
 * Ideas to consider implementing primarily analytics data.
 * -------------------------------------------------------------------------
 * Implement heuristic model for buy and sell prices of Storage
 * Implement a heuristic model for buy prices of Consumer
 * Implement a heuristic model for sell prices of Solar and Wind
 * Implement heuristic model or keep current for quantity of buy/sell of Storage
 * Make Capacity, and Low High Threshold Variable for storage 
 * [Done]Add another agent, main grid with unlimited sellPower, and buyPower and configurable static pricing
 * [Done]Main sells to any buyer with remaining buyPower and sells to any agent with remaining sellPower
 * [Done]Add another field to all agents daily power consumption/generation both variables for storage and main (Resets each day)
 * Add another field to all agents, get average buyPrice per unit, average sellPrice per unit, both for storage and main.
 * [Done]Add another field to all agents, allExpense, allProfit, and both + allNetProfit for storage and main.
 * [Done]Add another field to all agents, currentExpense/currentProfit/currentNet that resets at the beginning of each time increment.
 * [Done]Change Expense, Profit, and NetProfit to Daily___.
 * [Done]Implement a Debug print level class, and replace all print statements with SmartPrint(a,"String"); where an index of a boolean array. It will only print if it is set to true.
 * Implement a global class to track avg buy sell by current/daily/all and by type e.g.  solarGenerators averaged $0.09/unit while wind generators average $0.11
 * Implement a instance Count that resets at the beginning of each tic, tracking profit/expense/net per hour.
 * Make the primary smartGridDriver into a class, with a singleton method that runs indefinately, until an ajax call stops it.
 * 			- If user refreshes page same data is there and they can continue where they left off.
 * Make a set of ajax calls for initiation of objects, before any tics have been made. 
 * Make a set of ajax calls for on node, click that transfers detailed information about an individual node dynamically
 * Make a set of ajax calls for the controls, pause, skip day, skip hour, play, reset and implement java functionality to handle each
 * Make a set of ajax calls to update the browser each time the browser tells it to stop. Pause, skip hour (1 tic) skip day (23 tics) etc..
 * Make a class that tracks exchanges and links to the users Exchange(Buyer/Seller, price, Quantity), invoice of agent for each tic.
 * [Current consume/generate/net][Daily consume/generate/net][All consume/generate/net]
 * [Current profit/expense/net][Daily profit/expense/net][All profit/expense/net]
 * [Current buy/sellPrice avg][Daily buy/sellPrice avg][ All buy/sellPrice avg]
 */
