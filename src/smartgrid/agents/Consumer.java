package smartgrid.agents;

import java.util.Random;

import smartgrid.web.bridge.SmartGridDriver;

public class Consumer extends Agent implements Buyers{
	static double[] consumption;
	double buyPrice, expense, buyPower, dailyExpense, hourlyExpense;
	static double consVar=0,startBuyBid=0;
	double[] lastBuyBids = new double[24];//How much the agent bid to buy for yesterday at this time recommended slightly above main grid buy price
	String name;
	Random rand = new Random();
	
	public Consumer(String name){
		super(name);
		this.name=name;
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
			//TODO next 2 lines sloppy code please refactor
			this.setExchangeCount(this.getExchangeCount()+1);
			this.setPriceSum(this.getPriceSum()+price);
		}
		else{
			smartPrint.println(0,"ERROR "+this.name+" tried to buy "+(this.buyPower-units)+" more power than it needs.");
			//TODO throw exception if true
		}
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
	public double getLastBuyBid(int t){
		return this.lastBuyBids[t];
	}
	
	@Override
	public double[] getLastBuyBids(){
		return this.lastBuyBids;
	}
	
	@Override
	public double getHourlyExpense(){
		return this.hourlyExpense;
	}
		
	@Override
	public void stepBegin(int t){
		if(t==0){
			this.dailyExpense=0;
		}
		//TODO modify consumption scalar
		//this.buyPower=4*this.consumptionRate[t]*(1+rand.nextDouble());//randomScalar between -1cR to 1cR simulating consumption of user
		this.buyPower=consumption[t]+rand.nextDouble()*(this.consVar+1)*Math.random() < 0.5 ? -1 : 1;//Base consumption +/- the variability
		smartPrint.println(2,this.name+" consumed and requires "+this.buyPower+" units of power");
		
		//Calculates the buy price for this round at this specific time
		if(SmartGridDriver.getDay()>0){
			if(Math.abs(this.lastPrices[t]-this.lastPrices2[t])>=lastPriceDifference){//Check if the difference between the pricing in the last two rounds at this time is greater than timeDiffence don't change price if it is
				this.setBuyPrice(this.lastBuyBids[t]+bidRatio*(this.lastPrices[t]-this.lastBuyBids[t]));//modify bid price according to previous bid, price and bid ratio
				smartPrint.println(2,this.name+" changed it's buyBid price from "+this.getLastBuyBid(t)+" to "+this.getBuyPrice()+"/unit.");
			}
			else{
				this.setBuyPrice(this.lastBuyBids[t]);//bid the same amount you did last round		
				smartPrint.println(2,this.name+" did not change its buyBid and is set at "+this.getBuyPrice()+"/unit.");
			}
		}
		else{
			this.buyPrice=startBuyBid;
		}
	}
	
	public void stepEnd(int t){
		this.hourlyExpense=0;
		
		if(buyPower>0){
			smartPrint.println(0, "Error: Consumer did not get enough power.");
		}
		
	//TODO put print statements for tic totals here
		this.lastBuyBids[t]=this.buyPrice;
		this.lastPrices2[t]=this.lastPrices[t];
		this.lastPrices[t]=this.getAvgPrice();
	}
}