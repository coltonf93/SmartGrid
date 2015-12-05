package smartgrid.agents;

import java.util.Random;

public class WindPower extends Agent implements Sellers{
	int t0,t1;
	double sellPrice,sellPower,profit,dailyProfit,hourlyProfit;
	static double genVar;
	static double generation[];
	double[] lastSellBids = {1.98,1.98,1.98,1.98,1.98,1.98,1.98,1.98,1.98,1.98,1.98,1.98,1.98,1.98,1.98,1.98,1.98,1.98,1.98,1.98,1.98,1.98,1.98,1.98}; //How much the agent bided to sell the power yesterday at this time recommended slightly under the sell price of main grid
	String name;
	Random rand = new Random();
	
	public WindPower(String name){
		super(name);
		this.sellPrice=0.1;
		this.sellPower=0;
		this.name=name;
		this.profit=0;
		this.dailyProfit=0;
		this.hourlyProfit=0;
		smartPrint.println(1,this.name+" was created.");
	}
	
	public static void setGeneration(double[] generationS, double genVarS){
		generation=generationS;
		genVar=genVarS;
	}

	@Override
	public double getSellPower() {
		return sellPower;
	}

	@Override
	public void setSellPrice(double sellPrice) {
		this.sellPrice=sellPrice;
	}

	@Override
	public double getSellPrice() {
		return this.sellPrice;
	}

	@Override
	public void sell(double units, double price) {
		if(this.sellPower>=units){
			smartPrint.println(3,this.name+" sold "+units+"/"+this.sellPower+" and has "+(this.sellPower-units)+" remaining at "+price+"/unit.");
			this.sellPower-=units;
			this.profit+=units*price;
			this.dailyProfit+=units*price;
			this.hourlyProfit+=units*price;
			//TODO next 2 lines sloppy code please refactor
			this.setExchangeCount(this.getExchangeCount()+1);
			this.setPriceSum(this.getPriceSum()+price);
		}
		else{
			smartPrint.println(0,"ERROR: "+this.getName()+" tried to sell "+units+" units, but only has "+sellPower+" available.");
		}
		
	}

	@Override
	public double getProfit() {
		return this.profit;
	}
	
	@Override
	public double getDailyProfit(){
		return this.dailyProfit;
	}
	
	@Override
	public String getName(){
		return this.name;
	}
	
	public double[] getLastSellBids(){
		return this.lastSellBids;
	}
	
	public double getLastSellBid(int t){
		return this.lastSellBids[t];
	}
	
	@Override 
	public double getHourlyProfit(){
		return this.hourlyProfit;
	}
	
	@Override
	public void stepBegin(int t){	
		if(t==0){
			dailyProfit=0;
		}
		this.hourlyProfit=0;
		this.sellPower=generation[t]+rand.nextDouble()*(genVar+1)*Math.random() < 0.5 ? -1 : 1;//Base generation +/- the variability
		if(this.sellPower<0){
			this.sellPower=0;
		}
		smartPrint.println(2,this.name+" generated "+this.sellPower+" units of power.");
		
		if(Math.abs(this.lastPrices[t]-this.lastPrices2[t])>=lastPriceDifference){//Check if the difference between the pricing in the last two rounds at this time is greater than timeDiffence don't change price if it is
			this.setSellPrice(this.lastSellBids[t]+bidRatio*(this.lastPrices[t]-this.lastSellBids[t]));//modify bid price according to previous bid, price and bid ratio
			smartPrint.println(2,this.name+" changed it's sellBid price from "+this.getLastSellBid(t)+" to "+this.getSellPrice()+"/unit.");
		}
		
		else{
			this.setSellPrice(this.lastSellBids[t]);//bid the same amount you did last round	
			smartPrint.println(2,this.name+" kept its sellBid the same as the last round at "+this.getSellPrice()+"/unit.");
		}
	}
	
	public void stepEnd(int t){
		if(sellPower>0){
			smartPrint.println(0, "Error: Consumer did not get enough power.");
		}
		if(this.dailyProfit>0){
			this.lastSellBids[t]=this.sellPrice;
		}
		else{
			this.lastSellBids[t]=0;
		}
		//TODO put print statements for tic totals here
		//TODO reset daily values for wind
		this.lastSellBids[t]=this.sellPrice;
		this.lastPrices2[t]=this.lastPrices[t];
		this.lastPrices[t]=this.getAvgPrice();
	}
}
