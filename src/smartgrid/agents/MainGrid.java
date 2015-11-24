package smartgrid.agents;

public class MainGrid extends Agent implements Buyers,Sellers{
	double buyPrice,sellPrice,expense,profit,dailyProfit,dailyExpense,hourlyProfit,hourlyExpense;
	String name;
	double[] lastSellBids = new double[24];//array that can be returned to keep it uniform with others
	double[] lastBuyBids = new double[24];//array that can be returned to keep it uniform with others
	public MainGrid(String name,double buyPrice,double sellPrice){
		super(name);
		this.name=name;
		this.buyPrice=buyPrice;
		this.sellPrice=sellPrice;
		this.dailyProfit=0;
		this.profit=0;
		this.dailyExpense=0;
		this.expense=0;
		this.init();
		smartPrint.println(1,this.name+" was created and has a fixed [buy:sell] price of ["+this.buyPrice+","+this.sellPrice+"] and has unlimited buy and sell power");
	}
	
	//TODO think of a cleaner way to do this since array's can't be specified in construcctor
	public void init(){
		for(int i=0;i<lastSellBids.length;i++){
			this.lastSellBids[i]=this.sellPrice;
		}
		for(int i=0;i<lastBuyBids.length;i++){
			this.lastBuyBids[i]=this.buyPrice;
		}
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
	public double offer(Buyers buyer, double units) {//always has enough to sell
		double price=(this.sellPrice+buyer.getBuyPrice())/2;
		buyer.buy(units,price);	
		this.sell(units,price);	
		return price;
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
	public double offer(Sellers seller, double units) {//Main grid has unlimited buying power
		double price=(this.buyPrice+seller.getSellPrice())/2;
		this.buy(units,price);
		seller.sell(units,price);
		return price;
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
	public double getLastSellBid(int t) {
		return this.lastSellBids[t];
	}

	@Override
	public double[] getLastSellBids() {
		return this.lastSellBids;
	}

	@Override
	public double getLastBuyBid(int t) {
		return this.lastSellBids[t];
	}

	@Override
	public double[] getLastBuyBids() {
		return this.lastBuyBids;
	}
	
	@Override
	public void stepBegin(int t) {
		if(t==0){
			this.dailyExpense=0;
			this.dailyProfit=0;
		}
		this.hourlyExpense=0;
		this.hourlyProfit=0;
		smartPrint.println(2,this.name+" never changes its buyBid and kept it set at "+this.getBuyPrice()+"/unit.");
		smartPrint.println(2,this.name+" never changes its sellBid and kept it set at "+this.getSellPrice()+"/unit.");
	}
	
	public void stepEnd(int t) {
		//TODO move print statements for daily profit here

	}
}
