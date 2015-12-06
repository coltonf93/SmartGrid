package smartgrid.agents;

public interface Sellers{
	public double getSellPrice();
	public double getSellPower();
	public double getProfit();
	public double[][] getSellBidMatrix();
	double getDailyProfit();
	double getHourlyProfit();
	public void setSellPrice(double sellPrice);
	public void sell(double units, double price);
	public String getName();
	public double getLastSellBid();
	public void stepBegin();
	public void stepEnd();
}
