package smartgrid.utilities;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import smartgrid.agents.*;


public class AuctionMaster {
	private static ArrayList<Agent> sellers = new ArrayList<Agent>();
	private static ArrayList<Agent> buyers = new ArrayList<Agent>();
	private static Comparator<Agent> lowSeller;
	private static Comparator<Agent> highBuyer;
	SmartPrint smartPrint=SmartPrint.getInstance();
	Sellers seller;
	Buyers buyer;
	static{	
		lowSeller = new Comparator<Agent>(){
			@Override
			public int compare(Agent s1, Agent s2){
				return Double.compare(((Sellers)s1).getSellPrice(), ((Sellers)s2).getSellPrice());
			}
		};
		highBuyer = new Comparator<Agent>(){
			@Override
			public int compare(Agent b1, Agent b2){
				return -1*Double.compare(((Buyers)b1).getBuyPrice(), ((Buyers)b2).getBuyPrice());
			}
		};
	}
	
	public AuctionMaster(){
		//blank for now
	}
	
	public void addBuyers(Collection<? extends Agent> buyers){
		AuctionMaster.buyers.addAll(buyers);
	}
	
	public void addBuyer(Agent buyer){
		AuctionMaster.buyers.add(buyer);
	}

	public void exchange(Buyers buyer, Sellers seller){	
		double price=0;//agreed upon exchange price
		if(seller.getName().equals("MainGrid")){
			price=seller.getSellPrice();
		}
		else if(buyer.getName().equals("MainGrid")){
			price=buyer.getBuyPrice();
		}
		else{
			price=(buyer.getBuyPrice()+seller.getSellPrice())/2;// if it's not the main grid the agent pays the average of buyer and seller bids
		}
			
		if(buyer.getBuyPower()>0&&seller.getSellPower()>0){
			if(buyer.getBuyPower()>=seller.getSellPower()){
				buyer.buy(seller.getSellPower(), price);
				seller.sell(seller.getSellPower(), price);
			}
			else if(buyer.getBuyPower()<=seller.getSellPower()){
				seller.sell(buyer.getBuyPower(), price);
				buyer.buy(buyer.getBuyPower(), price);
			}
		}
	}
	
	public void processExchanges(){
		Collections.shuffle(buyers);//randomizes buyer order to prevent ordering preference
		Collections.sort(buyers,highBuyer);//Sorts all of the buyers by, buy price, highest first
		for(int i=0;i<buyers.size();i++){
			buyer=((Buyers)buyers.get(i));
			sellers=buyers.get(i).getBuysFrom();//Determines who can sell to the current buyer			
			Collections.shuffle(sellers);//Randomizes  seller order to prevent ordering preference
			Collections.sort(sellers,lowSeller);//matches the lowest price sellers to the highest price buyer
			smartPrint.println(3,"\n"+buyer.getName()+" needs "+buyer.getBuyPower()+" units for $"+buyer.getBuyPrice()+"/unit: \n-------------------------------------------------------");
			for(int j=0;j<sellers.size()&&buyer.getBuyPower()>0;j++){//All connected sellers offer their prices and availability, cheapest first 
				seller=(Sellers)sellers.get(j);//TODO Sellers is a higher level than storage so storage has issues I think
				this.exchange(buyer, seller);
			}
		}
	}
}
