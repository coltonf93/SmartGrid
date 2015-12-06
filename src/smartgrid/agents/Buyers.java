package smartgrid.agents;

public interface Buyers {
	public void setBuyPrice(double buy);
	public double getBuyPrice();
	public void buy(double units, double price);
	public double[][] getBuyBidMatrix();
	public double getBuyPower();
	public double getExpense();
	public String getName();
	double getDailyExpense();
	double getHourlyExpense();
	double getLastBuyBid();
	public void stepBegin();
	public void stepEnd();
}
