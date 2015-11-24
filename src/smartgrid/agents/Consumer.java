package smartgrid.agents;

import java.util.Random;

public class Consumer extends Agent implements Buyers{
	double[] consumptionRate= {0.25,0.25,0.25,0.25,0.25,0.25,0.25,0.25,1.0,1.0,1.0,0.5,0.5,0.5,0.5,0.5,0.5,0.5,2.0,2.0,2.0,2.0,0.25,.25};
	double buyPrice, expense, buyPower, dailyExpense, hourlyExpense;
	double[] lastBuyBids = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};//How much the agent bid to buy for yesterday at this time Recommended 0
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
	
	//TODO find a way to prevent this from being called, exchange or something rather than buy sell e.g. exchange(Seller, quantity, price)
	public double offer(Sellers seller, double units){
		//smartPrint.println(this.name+" offered to by "+units+" at "+this.buyPrice+" to seller "+seller.getName());
		double price=(this.buyPrice+seller.getSellPrice())/2;//The price is the average of the sellers price and this buyers price
		if(this.buyPower<=units){//If the seller is selling more than the buyer needs, the buyer only buys what it needs
			this.buy(this.buyPower,price);
			seller.sell(buyPower,price);	
		}
		else if(this.buyPower>0){//If the seller doesn't have enough to satisfy the need, buy all that is offered.
			this.buy(units,price);
			seller.sell(units,price);
		}
		return price;
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
		this.buyPower=4*this.consumptionRate[t]*(1+rand.nextDouble());//randomScalar between -1cR to 1cR simulating consumption of user
		smartPrint.println(2,this.name+" consumed and requires "+this.buyPower+" units of power");
		
		
		//Calculates the buy price for this round at this specific time
		if(Math.abs(this.lastPrices[t]-this.lastPrices2[t])>=lastPriceDifference){//Check if the difference between the pricing in the last two rounds at this time is greater than timeDiffence don't change price if it is
			this.setBuyPrice(this.lastBuyBids[t]+bidRatio*(this.lastPrices[t]-this.lastBuyBids[t]));//modify bid price according to previous bid, price and bid ratio
			smartPrint.println(2,this.name+" changed it's buyBid price from "+this.getLastBuyBid(t)+" to "+this.getBuyPrice()+"/unit.");
		}
		else{
			this.setBuyPrice(this.lastBuyBids[t]);//bid the same amount you did last round		
			smartPrint.println(2,this.name+" did not change its buyBid and is set at "+this.getBuyPrice()+"/unit.");
		}
	}
	
	public void stepEnd(int t){
		this.hourlyExpense=0;
		
	//TODO put print statements for tic totals here
		this.lastBuyBids[t]=this.buyPrice;
		this.lastPrices2[t]=this.lastPrices[t];
		this.lastPrices[t]=this.getAvgPrice();
	}
}