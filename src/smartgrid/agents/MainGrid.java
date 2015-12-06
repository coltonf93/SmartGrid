package smartgrid.agents;

import smartgrid.web.bridge.SmartGridDriver;

public class MainGrid extends Agent implements Buyers,Sellers{
	double buyPrice,sellPrice,expense,profit,dailyProfit,dailyExpense,hourlyProfit,hourlyExpense;
	double[][] sellBids = new double[24][SmartGridDriver.getGlobal('D')];//array that can be returned to keep it uniform with others
	double[][] buyBids = new double[24][SmartGridDriver.getGlobal('D')];//array that can be returned to keep it uniform with others
	public MainGrid(double buyPrice,double sellPrice){
		super("MainGrid");
		this.buyPrice=buyPrice;
		this.sellPrice=sellPrice;
		this.dailyProfit=0;
		this.profit=0;
		this.dailyExpense=0;
		this.expense=0;
		smartPrint.println(1,this.name+" was created and has a fixed [buy:sell] price of ["+this.buyPrice+","+this.sellPrice+"] and has unlimited buy and sell power");
	}
	
	@Override
	public double getSellPrice() {
		return this.sellPrice;
	}

	@Override
	public double getSellPower() {
		return  Double.POSITIVE_INFINITY;
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
	public void setSellPrice(double sellPrice) {
		this.sellPrice=sellPrice;
	}

	@Override
	public void sell(double units, double price) {
		smartPrint.println(3,this.name+" sold "+units+" of its unlimited supply at "+price+"/unit.");
		this.profit+=units*price;
		this.dailyProfit+=units*price;
		this.hourlyProfit+=units*price;
		//TODO next 2 lines sloppy code please refactor
		this.setExchangeCount(this.getExchangeCount()+1);
		this.setPriceSum(this.getPriceSum()+price);
	}

	@Override
	public void setBuyPrice(double buyPrice) {
		this.buyPrice=buyPrice;
		
	}

	@Override
	public double getBuyPrice() {
		return this.buyPrice;
	}

	@Override
	public void buy(double units, double price) {
		smartPrint.println(3,this.name+" bought "+units+" out of its unlimited need at "+price+"/unit.");
		this.expense+=units*price;
		this.dailyExpense+=units*price;
		this.hourlyExpense+=units*price;
		//TODO next 2 lines sloppy code please refactor
		this.setExchangeCount(this.getExchangeCount()+1);
		this.setPriceSum(this.getPriceSum()+price);
	}

	@Override
	public double getBuyPower() {
		return  Double.POSITIVE_INFINITY;
	}

	@Override
	public double getExpense() {
		return this.expense;
	}
	
	@Override
	public double getDailyExpense(){
		return this.dailyExpense;
	}

	@Override
	public String getName() {
		return this.name;
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
	public double getHourlyExpense(){
		return this.hourlyExpense;
	}
	
	public double getHourlyNetProfit(){
		return this.hourlyProfit-this.hourlyExpense;
	}
	
	@Override
	public double getLastSellBid() {
		return this.sellPrice;
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
	public double getLastBuyBid() {
		return this.buyPrice;
	}
	
	@Override
	public void stepBegin() {
		if(SmartGridDriver.getGlobal('t')==0){
			this.dailyExpense=0;
			this.dailyProfit=0;
		}
		this.hourlyExpense=0;
		this.hourlyProfit=0;
		smartPrint.println(2,this.name+" never changes its buyBid and kept it set at "+this.getBuyPrice()+"/unit.");
		smartPrint.println(2,this.name+" never changes its sellBid and kept it set at "+this.getSellPrice()+"/unit.");
	}
	
	public void stepEnd() {
		//the below declartaions should be array's with all the same value other than avgPrices which is the ratio between the grids buy and sell.
		this.buyBids[SmartGridDriver.getGlobal('t')][SmartGridDriver.getGlobal('d')]=this.getBuyPrice();
		this.sellBids[SmartGridDriver.getGlobal('t')][SmartGridDriver.getGlobal('d')]=this.getSellPrice();
		this.avgPrices[SmartGridDriver.getGlobal('t')][(SmartGridDriver.getGlobal('d'))]=this.getAvgPrice();

	}
}
