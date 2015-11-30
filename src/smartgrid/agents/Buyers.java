package smartgrid.agents;

public interface Buyers {
	public void setBuyPrice(double buy);
	public double getBuyPrice();
	public void buy(double units, double price);
	public double getBuyPower();
	public double getExpense();
	public String getName();
	double getDailyExpense();
	double getHourlyExpense();
	double getLastBuyBid(int t);
	double[] getLastBuyBids();
	public void stepBegin(int t);
	public void stepEnd(int t);
}
