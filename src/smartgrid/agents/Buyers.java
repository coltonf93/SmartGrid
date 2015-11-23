package smartgrid.agents;

public interface Buyers {
	public void setBuyPrice(double buy);
	public double getBuyPrice();
	public double offer(Sellers seller, double units);
	public void buy(double units, double price);
	public double getBuyPower();
	public double getExpense();
	public String getName();
	double getDailyExpense();
	double getLastBuyBid(int t);
	double[] getLastBuyBids();
	public void stepBegin(int t);
	public void stepEnd(int t);
}
