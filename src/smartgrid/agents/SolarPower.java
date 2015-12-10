package smartgrid.agents;

import java.util.Random;

import smartgrid.web.bridge.SmartGridDriver;

public class SolarPower extends Agent implements Sellers{
		int t0,t1;
		double sellPrice,sellPower,profit,dailyProfit,hourlyProfit;
		static double genVar=0,startSellBid=1;;
		static double[] generation;
		double[][] sellBids = new double[24][SmartGridDriver.getGlobal('D')]; //How much the agent bided to sell the power yesterday at this time recommended slightly under the sell price of main grid
		Random rand = new Random();
		
		public SolarPower(String name){
			super(name);
			this.profit=0;
			this.dailyProfit=0;
			this.sellPrice=0.1;
			this.sellPower=0;
			smartPrint.println(1, this.name+" was created.");
		}
		
		
		public static void setGeneration(double[] generationS, double genVarS){
			generation=generationS;
			genVar=genVarS;
		}
		
		public static void setStartSellBid(double bid){
			startSellBid=bid;
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
			if(sellPower>=units){
				smartPrint.println(2,this.name+" sold "+units+"/"+this.sellPower+" and has "+(this.sellPower-units)+" remaining at "+price+"/unit.");
				this.sellPower-=units;
				this.profit+=units*price;
				this.dailyProfit+=units*price;
				this.hourlyProfit+=units*price;
				this.addExchange(units, price);
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
		public double getLastSellBid(){
			return this.sellBids[SmartGridDriver.getGlobal('t')][(SmartGridDriver.getGlobal('d')-1)];
		}
		
		@Override
		public double getHourlyProfit(){
			return this.hourlyProfit;
		}
		
		@Override
		public double[][] getSellBidMatrix(){
			return this.sellBids;
		}
		
		@Override
		public void stepBegin(){
			if(SmartGridDriver.getGlobal('t')==0){
				this.dailyProfit=0;
			}
			this.resetExchange();
			this.hourlyProfit=0;
			this.sellPower=generation[SmartGridDriver.getGlobal('t')]+rand.nextDouble()*(genVar+1)*Math.random();//Base generation + the variability
			if(this.sellPower<0){
				this.sellPower=0;
			}
			smartPrint.println(2,this.name+" generated "+this.sellPower+" units of power.");
			//Calculates the buy price for this round at this specific time
			if(SmartGridDriver.getGlobal('d')>0){
				if(SmartGridDriver.getGlobal('d')>1&&Math.abs(this.avgPrices[SmartGridDriver.getGlobal('t')][(SmartGridDriver.getGlobal('d')-1)]-this.avgPrices[SmartGridDriver.getGlobal('t')][(SmartGridDriver.getGlobal('d')-2)])>=lastPriceDifference){//Check if the difference between the pricing in the last two rounds at this time is greater than timeDiffence don't change price if it is
					this.setSellPrice(this.getLastSellBid()+bidRatio*(this.avgPrices[SmartGridDriver.getGlobal('t')][(SmartGridDriver.getGlobal('d')-1)]-this.getLastSellBid()));//modify bid price according to previous bid, price and bid ratio
					smartPrint.println(2,this.name+" changed it's sellBid price from "+this.getLastSellBid()+" to "+this.getSellPrice()+"/unit. An addition of "+(bidRatio*(this.avgPrices[SmartGridDriver.getGlobal('t')][(SmartGridDriver.getGlobal('d')-1)]-this.getLastSellBid())));
				}	
				else{
					this.setSellPrice(this.getLastSellBid());//bid the same amount you did last round
					smartPrint.println(2,this.name+" kept its sellBid the same as the last round at "+this.getSellPrice()+"/unit.");
				}
			}
			else{
				this.sellPrice=startSellBid;
			}
		}
		
		public void stepEnd(){
			if(sellPower>0){
				smartPrint.println(0, "Error: Consumer did not get enough power.");
			}
			this.sellBids[SmartGridDriver.getGlobal('t')][(SmartGridDriver.getGlobal('d'))]=this.sellPrice;
			this.avgPrices[SmartGridDriver.getGlobal('t')][(SmartGridDriver.getGlobal('d'))]=this.getAvgPrice();
		}
}
