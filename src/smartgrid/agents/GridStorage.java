package smartgrid.agents;

public class GridStorage extends Agent implements Buyers, Sellers{
	double sellPrice, buyPrice, profit, expense, sellPower, buyPower, decayRate, storedPower, capacity, dailyExpense,dailyProfit,hourlyProfit,hourlyExpense;
	double[] lastSellBids = {.99,.99,.99,.99,.99,.99,.99,.99,.99,.99,.99,.99,.99,.99,.99,.99,.99,.99,.99,.99,.99,.99,.99,.99}; //How much the agent bided to sell the power yesterday at this time recommended slightly under the sell price of main grid
	double[] lastBuyBids = {.02,.02,.02,.02,.02,.02,.02,.02,.02,.02,.02,.02,.02,.02,.02,.02,.02,.02,.02,.02,.02,.02,.02,.02};//How much the agent bid to buy for yesterday at this time recommended slightly above main grid buy price
	
	public GridStorage(String name){
		super(name);
		this.capacity=30.0;
		this.decayRate=.2;
		this.name=name;	
		this.profit=0;
		this.dailyProfit=0;
		this.expense=0;
		this.dailyExpense=0;
		this.storedPower=.5*this.capacity;//starts half full
		this.hourlyProfit=0;
		this.hourlyExpense=0;
		smartPrint.println(1,this.name+" was created and has "+.5*this.capacity+" units of power stored and a max capacity of "+this.capacity);
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
			//TODO next 2 lines sloppy code please refactor
			this.setExchangeCount(this.getExchangeCount()+1);
			this.setPriceSum(this.getPriceSum()+price);
		}
		else{
			smartPrint.println(0,"ERROR: "+this.getName()+" tried to sell "+units+" units, but only has "+sellPower+" available.");
		}
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
			//TODO next 2 lines sloppy code please refactor
			this.setExchangeCount(this.getExchangeCount()+1);
			this.setPriceSum(this.getPriceSum()+price);
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
	//offer to buy from a seller
	public double offer(Sellers seller, double units){
		double price=0;//agreed upon exchange price
		if(!seller.getName().equals("MainGrid")){
			price=(this.buyPrice+seller.getSellPrice())/2;// if it's not the main grid the agent pays the average of buyer and selleer bids
		}
		else if(seller.getName().equals("MainGrid")&& this.storedPower<.2*this.capacity){
			price=seller.getSellPrice();//If it's the main grid, the agent pays the price of the main grid
		}
		else{
			smartPrint.println(3, this.name+" rejected main grid offer.");
		}
		//TODO implement sell to main grid functionality inverse to this
		if(this.buyPower<=units){
			seller.sell(this.buyPower,price);
			this.buy(this.buyPower,price);
		}
		else if(this.buyPower>0){
			this.expense-=price*units;
			this.buyPower-=units;
			seller.sell(units,price);
			this.buy(units,price);
		}
		return price;
	}
	//offer to sell to a buyer
	public double offer(Buyers buyer, double units){
		double price=(this.sellPrice+buyer.getBuyPrice())/2;
		if(units<=this.sellPower){//storage has more power than is needed, satisfy requested need.
			buyer.buy(units,price);	
			this.sell(units,price);	
		}
		else if(this.sellPower>0){//If storage has power available to give but does not have enough to satisfy need, give available
			buyer.buy(sellPower, price);
			this.sell(sellPower,price);
			
		}
		return price;
	}
	
	@Override
	public String getName(){
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
		return this.lastBuyBids[t];
	}

	@Override
	public double[] getLastBuyBids() {
		return this.lastBuyBids;
	}
	
	@Override
	public void stepBegin(int t){
		if(t==0){
			dailyProfit=0;
			dailyExpense=0;
		}
		this.hourlyExpense=0;
		this.hourlyProfit=0;
		if(Math.abs(this.lastPrices[t]-this.lastPrices2[t])>=lastPriceDifference){//Check if the difference between the pricing in the last two rounds at this time is greater than timeDiffence don't change price if it is
			this.setBuyPrice(this.lastBuyBids[t]+bidRatio*(this.lastPrices[t]-this.lastBuyBids[t]));//modify buyBid according to previous bid, price and bid ratio
			smartPrint.println(2,this.name+" changed it's buyBid price from "+this.getLastBuyBid(t)+" to "+this.getBuyPrice()+"/unit.");
			this.setSellPrice(this.lastSellBids[t]+bidRatio*(this.lastPrices[t]-this.lastSellBids[t]));//modify sellBid according to previous bid, price and bid ratio
			smartPrint.println(2,this.name+" changed it's sellBid price from "+this.getLastSellBid(t)+" to "+this.getSellPrice()+"/unit.");
		}
		
		else{
			this.setSellPrice(this.lastSellBids[t]);//sellBid the same amount as yesterday at this time	
			smartPrint.println(2,this.name+" kept its sellBid the same as the last round at "+this.getSellPrice()+"/unit.");
			this.setBuyPrice(this.lastSellBids[t]);//buyBid the same amount as today at this time
			smartPrint.println(2,this.name+" kept its buyBid the same as the last round at "+this.getBuyPrice()+"/unit.");
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
			this.buyPower=.9*this.capacity-this.storedPower;//Will buy up to slightly above the max threshold
			this.sellPower=this.storedPower-(.1*this.capacity);//Will sell up to the slightly below the min threshold
			smartPrint.println(2,this.name+" has "+this.storedPower+" and is buying "+this.buyPower+" units, and is selling "+this.sellPower+" units.");
		}
	}
	
	public void stepEnd(int t){
		//Daily totals print statements should go here.
		//Put print statements for tic totals here
		if(this.buyPower>0){
			smartPrint.println(0, "Warning: "+this.name+" did not buy as much as it allocated.");
			
		}
		if(this.sellPower>0){
			smartPrint.println(0, "Warning: "+this.name+" did not sell as much as it wanted.");
		}
		this.lastBuyBids[t]=this.getBuyPrice();
		this.lastSellBids[t]=this.getSellPrice();
		this.lastPrices2[t]=this.lastPrices[t];
		this.lastPrices[t]=this.getAvgPrice();
		
	}
}
