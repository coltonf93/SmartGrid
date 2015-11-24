package smartgrid.agents;

public interface Sellers{
	public double getSellPrice();
	public double getSellPower();
	public double getProfit();
	double getDailyProfit();
	double getHourlyProfit();
	public void setSellPrice(double sellPrice);
	public double offer(Buyers buyer, double units);
	public void sell(double units, double price);
	public String getName();
	public double getLastSellBid(int t);
	public double[] getLastSellBids();
	public void stepBegin(int t);
	public void stepEnd(int t);
}
