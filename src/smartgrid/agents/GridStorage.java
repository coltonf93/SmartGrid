package smartgrid.agents;

import java.util.Random;

import smartgrid.web.bridge.SmartGridDriver;

public class GridStorage extends Agent implements Buyers, Sellers{
	double sellPrice, buyPrice, profit, expense, sellPower, buyPower, decayRate, storedPower, capacity, dailyExpense,dailyProfit,hourlyProfit,hourlyExpense;
	static double startSellBid=1,startBuyBid=0;
	double[][] sellBids = new double[24][SmartGridDriver.getGlobal('D')]; //How much the agent bided to sell the power yesterday at this time recommended slightly under the sell price of main grid
	double[][] buyBids = new double[24][SmartGridDriver.getGlobal('D')];//How much the agent bid to buy for yesterday at this time recommended slightly above main grid buy price
	Random rand=new Random();
	public GridStorage(String name, double capacity, double capVar, double decay){
		super(name);
		this.capacity=capacity +rand.nextDouble()*(capVar+1)*Math.random() ;
		this.decayRate=decay;
		this.profit=0;
		this.dailyProfit=0;
		this.expense=0;
		this.dailyExpense=0;
		this.storedPower=.5*this.capacity;//starts half full
		this.hourlyProfit=0;
		this.hourlyExpense=0;
		smartPrint.println(1,this.name+" was created and has "+.5*this.capacity+" units of power stored and a max capacity of "+this.capacity);
	}
	
	public static void setStartSellBid(double bid){
		startSellBid=bid;
	}
	
	public static void setStartBuyBid(double bid){
		startBuyBid=bid;
	}
	
	public double getSellPower(){
		return this.sellPower;
	}
	
	public double getBuyPower(){
		return this.buyPower;
	}
	
	public void setSellPrice(double sellPrice) {
		this.sellPrice=sellPrice;
	}
	
	public void setBuyPrice(double buyPrice){
		this.buyPrice=buyPrice;
	}
	
	public double getSellPrice(){
		return this.sellPrice;
	}
	
	public double getBuyPrice(){
		return this.buyPrice;
	}

	@Override
	public void sell(double units, double price) {
		if(sellPower>=units){
			smartPrint.println(3,this.name+" sold "+units+"/"+this.sellPower+" and has "+(this.sellPower-units)+" remaining at "+price+"/unit.");
			this.sellPower-=units;
			this.storedPower-=units;
			this.profit+=units*price;
			this.dailyProfit+=units*price;
			this.hourlyProfit+=units*price;
			this.addExchange(units, price);
		}
		else{
			smartPrint.println(0,"ERROR: "+this.getName()+" tried to sell "+units+" units, but only has "+sellPower+" available.");
		}
	}
	
	public void setSellPower(double sp){
		this.sellPower=sp;
	}
	
	public void setBuyPower(double bp){
		this.buyPower=bp;
	}
	
	@Override
	public void buy(double units, double price){//TODO consider passing the seller object, to verify the seller
		if(this.buyPower>=units){
			smartPrint.println(3,this.name+" bought "+units+"/"+this.buyPower+" units of power and has "+(this.buyPower-units)+" remaining at "+price+"/unit.");
			this.buyPower-=units;
			this.storedPower+=units;
			this.expense+=units*price;
			this.dailyExpense+=units*price;
			this.hourlyExpense+=units*price;
			this.addExchange(units, price);
		}
		else{
			smartPrint.println(0,"ERROR: "+this.name+" tried to buy more units than it needs.");
			//TODO throw exception if true
		}
	}
	
	public double getExpense(){
		return this.expense;
	}
	
	public double getDailyExpense(){
		return this.dailyExpense;
	}
	
	public double getProfit(){
		return this.profit;
	}
	
	public double getDailyProfit(){
		return this.dailyProfit;
	}
	
	public double getNetProfit(){
		return this.profit-this.expense;
	}
	
	public double getDailyNetProfit(){
		return this.dailyProfit-this.dailyExpense;
	}
	
	@Override 
	public double getHourlyProfit(){
		return this.hourlyProfit;
	}
	
	@Override
	public double[][] getBuyBidMatrix(){
		return this.buyBids;
	}
	
	@Override
	public double[][] getSellBidMatrix(){
		return this.sellBids;
	}
	
	@Override
	public double getHourlyExpense(){
		return this.hourlyExpense;
	}
	
	public double getHourlyNetProfit(){
		return this.hourlyProfit-this.hourlyExpense;
	}
	
	@Override
	public double getLastSellBid() {
		return this.sellBids[SmartGridDriver.getGlobal('t')][(SmartGridDriver.getGlobal('d')-1)];
	}

	@Override
	public double getLastBuyBid() {
		return this.buyBids[(SmartGridDriver.getGlobal('t'))][SmartGridDriver.getGlobal('d')-1];
	}
	
	@Override
	public void stepBegin(){
		if(SmartGridDriver.getGlobal('t')==0){
			dailyProfit=0;
			dailyExpense=0;
		}
		this.resetExchange();
		this.hourlyExpense=0;
		this.hourlyProfit=0;
		if(SmartGridDriver.getGlobal('d')>0){
			if(SmartGridDriver.getGlobal('d')>1&&Math.abs(avgPrices[SmartGridDriver.getGlobal('t')][(SmartGridDriver.getGlobal('d')-1)]-avgPrices[SmartGridDriver.getGlobal('t')][(SmartGridDriver.getGlobal('d')-2)])>=lastPriceDifference){//Check if the difference between the pricing in the last two rounds at this time is greater than timeDiffence don't change price if it is
				this.setBuyPrice(this.getLastBuyBid()+bidRatio*(this.avgPrices[SmartGridDriver.getGlobal('t')][(SmartGridDriver.getGlobal('d')-1)]-this.getLastBuyBid()));//modify buyBid according to previous bid, price and bid ratio
				smartPrint.println(2,this.name+" changed it's buyBid price from "+this.getLastBuyBid()+" to "+this.getBuyPrice()+"/unit.");
				this.setSellPrice(this.getLastSellBid()+bidRatio*(this.avgPrices[SmartGridDriver.getGlobal('t')][(SmartGridDriver.getGlobal('d')-1)]-this.getLastSellBid()));//modify sellBid according to previous bid, price and bid ratio
				smartPrint.println(2,this.name+" changed it's sellBid price from "+this.getLastSellBid()+" to "+this.getSellPrice()+"/unit.");
			}		
			else{
				this.setSellPrice(this.getLastSellBid());//sellBid the same amount as yesterday at this time	
				smartPrint.println(2,this.name+" kept its sellBid the same as the last round at "+this.getSellPrice()+"/unit.");
				this.setBuyPrice(this.getLastBuyBid());//buyBid the same amount as today at this time
				smartPrint.println(2,this.name+" kept its buyBid the same as the last round at "+this.getBuyPrice()+"/unit.");
			}
		}
		else{
			this.setBuyPrice(startBuyBid);
			this.setSellPrice(startSellBid);
		}
		
		
		smartPrint.println(2,this.name+" lost "+this.storedPower*this.decayRate+" due to decay.");
		this.storedPower=this.storedPower-this.storedPower*this.decayRate;//Some power is lost do to decay
		//Temporary buy sell quantity decision
		if(this.storedPower>.8*this.capacity){
			this.sellPower=this.storedPower-.5*this.capacity;//Selling down to 1/2 of the capacity
			this.buyPower=0;//buying nothing until it's below the max threshold.
			smartPrint.println(2,this.name+" has "+this.storedPower+" and is over its threshold only selling "+this.sellPower+" units.");
		}
		else if(this.storedPower<.2*this.capacity){
			this.buyPower=.5*this.capacity-this.storedPower;//Buying up to 1/2 of the capacity
			this.sellPower=0;//selling nothing until its above the min threshold.
			smartPrint.println(2,this.name+" has "+this.storedPower+" and is under it's threshold, only buying "+this.buyPower+"units.");
		}
		else{
			this.buyPower=.79*this.capacity-this.storedPower;//Will buy up to slightly above the max threshold
			this.sellPower=this.storedPower-(.21*this.capacity);//Will sell up to the slightly below the min threshold
			smartPrint.println(2,this.name+" has "+this.storedPower+" and is buying "+this.buyPower+" units, and is selling "+this.sellPower+" units.");
		}
	}
	
	public void stepEnd(){
		//Daily totals print statements should go here.
		//Put print statements for tic totals here
		if(this.buyPower>0){
			smartPrint.println(0, "Warning: "+this.name+" did not buy as much as it allocated.");
		}
		if(this.sellPower>0){
			smartPrint.println(0, "Warning: "+this.name+" did not sell as much as it wanted.");
		}
		this.buyBids[SmartGridDriver.getGlobal('t')][SmartGridDriver.getGlobal('d')]=this.getBuyPrice();
		this.sellBids[SmartGridDriver.getGlobal('t')][SmartGridDriver.getGlobal('d')]=this.getSellPrice();
		this.avgPrices[SmartGridDriver.getGlobal('t')][(SmartGridDriver.getGlobal('d'))]=this.getAvgPrice();
		
	}
}
