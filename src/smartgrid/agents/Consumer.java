package smartgrid.agents;

import java.util.Random;
import smartgrid.web.bridge.SmartGridDriver;

public class Consumer extends Agent implements Buyers{
	static double[] consumption;
	double buyPrice, expense, buyPower, dailyExpense, hourlyExpense;
	static double consVar=0,startBuyBid=0;
	double[][] buyBids = new double[24][SmartGridDriver.getGlobal('D')];//How much the agent bid to buy for yesterday at this time recommended slightly above main grid buy price
	Random rand = new Random();
	
	public Consumer(String name){
		super(name);
		this.expense=0;
		this.dailyExpense=0;
		this.hourlyExpense=0;
		smartPrint.println(1, this.name+" was created with a variable class defined consumption rate.");
	}
	
	public static void setStartBuyBid(double bid){
		startBuyBid=bid;
	}
	
	public static void setConsumption(double[] consumptionS, double consVarS){
		consumption=consumptionS;
		consVar=consVarS;
	}
	
	@Override
	public double getBuyPrice(){//TODO load buy price auction when power implemented
		return this.buyPrice;
	}
	
	@Override
	public void setBuyPrice(double buyPrice){
		this.buyPrice=buyPrice;
	}
	
	@Override
	public void buy(double units, double price){
		if(this.buyPower>=units){
			smartPrint.println(3,this.name+" bought "+units+"/"+this.buyPower+" units of power and has "+(this.buyPower-units)+" remaining at "+price+"/unit.");
			this.expense+=units*price;
			this.dailyExpense+=units*price;
			this.hourlyExpense+=units*price;
			this.buyPower-=units;
			this.addExchange(units, price);
			
		}
		else{
			smartPrint.println(0,"ERROR "+this.name+" tried to buy "+(this.buyPower-units)+" more power than it needs.");
			//TODO throw exception if true
		}
	}
	
	@Override
	public double[][] getBuyBidMatrix(){
		return this.buyBids;
	}

	@Override
	public double getExpense() {
		return expense;
	}
	
	@Override
	public double getDailyExpense(){
		return this.dailyExpense;
	}
	
	@Override
	public double getBuyPower(){
		return this.buyPower;
	}
	
	@Override
	public String getName(){
		return this.name;
	}
	
	@Override
	public double getLastBuyBid(){
		return this.buyBids[SmartGridDriver.getGlobal('t')][(SmartGridDriver.getGlobal('d')-1)];
	}
	
	@Override
	public double getHourlyExpense(){
		return this.hourlyExpense;
	}
		
	@Override
	public void stepBegin(){
		if(SmartGridDriver.getGlobal('t')==0){
			this.dailyExpense=0;
		}
		this.resetExchange();
		//this.buyPower=4*this.consumptionRate[t]*(1+rand.nextDouble());//randomScalar between -1cR to 1cR simulating consumption of user
		this.buyPower=consumption[SmartGridDriver.getGlobal('t')]+rand.nextDouble()*(consVar+1)*Math.random();//Base consumption + the variability
		smartPrint.println(2,this.name+" consumed and requires "+this.buyPower+" units of power");
		
		//Calculates the buy price for this round at this specific time
		if(SmartGridDriver.getGlobal('d')>0){
			if(SmartGridDriver.getGlobal('d')>1&&Math.abs(avgPrices[SmartGridDriver.getGlobal('t')][(SmartGridDriver.getGlobal('d')-1)]-avgPrices[SmartGridDriver.getGlobal('t')][(SmartGridDriver.getGlobal('d')-2)])>=lastPriceDifference){//Check if the difference between the pricing in the last two rounds at this time is greater than timeDiffence don't change price if it is
				this.setBuyPrice(this.buyBids[(SmartGridDriver.getGlobal('t'))][(SmartGridDriver.getGlobal('d')-1)]+bidRatio*(this.avgPrices[SmartGridDriver.getGlobal('t')][(SmartGridDriver.getGlobal('d')-1)]-this.buyBids[(SmartGridDriver.getGlobal('t'))][SmartGridDriver.getGlobal('d')-1]));//modify bid price according to previous bid, price and bid ratio
				smartPrint.println(2,this.name+" changed it's buyBid price from "+this.getLastBuyBid()+" to "+this.getBuyPrice()+"/unit.");
			}
			else{
				this.setBuyPrice(this.buyBids[SmartGridDriver.getGlobal('t')][(SmartGridDriver.getGlobal('d')-1)]);//bid the same amount you did last round		
				smartPrint.println(2,this.name+" did not change its buyBid and is set at "+this.getBuyPrice()+"/unit.");
			}
		}
		else{
			this.buyPrice=startBuyBid;
		}
	}
	
	public void stepEnd(){
		this.hourlyExpense=0;
		
		if(buyPower>0){
			smartPrint.println(0, "Error: Consumer did not get enough power.");
		}
		
		this.buyBids[SmartGridDriver.getGlobal('t')][SmartGridDriver.getGlobal('d')]=this.buyPrice;
		this.avgPrices[SmartGridDriver.getGlobal('t')][SmartGridDriver.getGlobal('d')]=this.getAvgPrice();

	}
}