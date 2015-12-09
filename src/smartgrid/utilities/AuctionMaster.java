package smartgrid.utilities;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import smartgrid.agents.*;
import smartgrid.web.bridge.SmartGridDriver;


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
		this.buyers.addAll(buyers);
	}
	
	public void addBuyer(Agent buyer){
		this.buyers.add(buyer);
	}

	public void exchange(Buyers buyer, Sellers seller){
		
		//System.out.println("STATIC DAY CHECK: "+SmartGridDriver.getDay());
		
		double price=0;//agreed upon exchange price
		if(buyer.getName().equals("MainGrid")|| seller.getName().equals("MainGrid")){
			if(seller.getName().equals("MainGrid")){//the seller is main grid
				price=seller.getSellPrice();//price is main-grid sell price
				if(buyer.getClass()==smartgrid.agents.GridStorage.class){//TODO find a better implementation for this to break cohesion
					if(((GridStorage)buyer).getPowerRating() < 0.0){
						((GridStorage)buyer).setBuyPower(-1*((GridStorage)buyer).getPowerRating());//buy up to min threshold
					}
					else{
						smartPrint.println(3,buyer.getName()+" refused to buy from MainGrid but still wanted to buy "+buyer.getBuyPower()+" more units.");
						((GridStorage)buyer).setBuyPower(0.0);//refuse to buy from main grid
					}
				}
			}
			else{//the buyer is main grid
				price=buyer.getBuyPrice();//price is main grid buy price
				if(seller.getClass()==smartgrid.agents.GridStorage.class && seller.getSellPower()>0){//TODO find a better implementation for this to break cohesion
					if(((GridStorage)seller).getPowerRating() > 0.0){
						((GridStorage)seller).setSellPower(((GridStorage)seller).getPowerRating());//sell down to max threshold
					}
					else{
						smartPrint.println(3,seller.getName()+" refused to sell to MainGrid but still wanted to sell "+seller.getSellPower()+" more units.");
						((GridStorage)seller).setSellPower(0);
					}
				}
			}
		}
		else{//Both the buyer and seller are agents
			price=(buyer.getBuyPrice()+seller.getSellPrice())/2;// if it's not the main grid the agent pays the average of buyer and seller bids
		}
		//Run actual trade with agreed upon price
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
				//System.out.println("Seller("+seller.getName()+") bid at "+seller.getSellPrice()+"\\unit and Buyer("+buyer.getName()+") bit at "+buyer.getBuyPrice()+"\\unit agreeing at "+price+"\\unit.");
			}
		}
	}
}
