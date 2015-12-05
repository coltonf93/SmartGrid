package smartgrid.utilities;

import java.util.ArrayList;
import smartgrid.agents.*;

public class ConnectionBuilder {
	SmartPrint smartPrint = SmartPrint.getInstance();
	public ConnectionBuilder(double p, ArrayList<Agent>generators, ArrayList<Agent> consumers, ArrayList<Agent> storage, MainGrid mainGrid ){
		//Replace k with k and intitialize k with Agent k = array.get(i)
		if(p>=0&&p<=1){
			int cindex=0;//consumerIndex to prevent duplicates
			int gindex=0;//generator index to prevent duplicates
			int sindex=0;//storage index to prevent duplicates
			int conCount=0;//counts the connections to prevent duplicates
			for(int i=0;i<storage.size();i++){
				Agent s=storage.get(i);
				conCount=0;
				do{//Connects storage to generators
					s.setBuyFrom(generators.get(gindex%generators.size()));
					((generators.get(gindex%generators.size()))).setSellTo(s);
					smartPrint.println(1,s.getName()+" connected to "+generators.get(gindex%generators.size()).getName());
					gindex++;
					conCount++;
				}while((double)conCount/(double)generators.size()<p);
				conCount=0;
				do{//Connects storage to consumers
					(s).setSellTo(consumers.get(cindex%consumers.size()));
					(consumers.get(cindex%consumers.size())).setBuyFrom(s);
					smartPrint.println(1,s.getName()+" connected to "+consumers.get(cindex%consumers.size()).getName());
					cindex++;
					conCount++;
				}while(((double)conCount/(double)consumers.size())<p);
				//Storage buys/sells to main grid 
				s.setSellTo(mainGrid);
				s.setBuyFrom(mainGrid);
				mainGrid.setSellTo(s);
				mainGrid.setBuyFrom(s);
				smartPrint.println(1,s.getName()+" connected to MainGrid");
			}
			
			for(int i=0;i<generators.size();i++){//makes all the generator connections
				Agent g = generators.get(i);
				conCount=0;
				do{//Connects remaining generators to storage
					if(!(g).isConnected(storage.get(sindex%storage.size()))){//Check that the current index is not connected
						(g).setSellTo((storage.get(sindex%storage.size())));
						((storage.get(sindex%storage.size()))).setBuyFrom(g);
						smartPrint.println(1,g.getName()+" connected to "+storage.get(sindex%storage.size()).getName());
					}
					sindex++;
					conCount++;
				}while(((double)conCount/(double)storage.size())<p);
				conCount=0;	
				do{//Connects generators to consumers
					(g).setSellTo((consumers.get(cindex%consumers.size())));
					(consumers.get(cindex%consumers.size())).setBuyFrom(g);
					smartPrint.println(1,g.getName()+" connected to "+consumers.get(cindex%consumers.size()).getName());
					cindex++;
					conCount++;
				}while(((double)conCount/(double)consumers.size())<p);
				g.setSellTo(mainGrid);
				mainGrid.setBuyFrom(g);
				
			}
			
			for(int i=0;i<consumers.size();i++){
				Agent c=consumers.get(i);
				conCount=0;
				do{//Connects the remaining consumers to storage
					if(!c.isConnected(storage.get(sindex%storage.size()))){//Check that the current index is not connected
						c.setBuyFrom((storage.get(sindex%storage.size())));
						((storage.get(sindex%storage.size()))).setSellTo(c);
						smartPrint.println(1,c.getName()+" connected to "+storage.get(sindex%storage.size()).getName());
					}
					sindex++;
					conCount++;
				}while(((double)conCount/(double)storage.size())<p);
				conCount=0;
				do{//Connects the remaining consumers to generators
					if(!c.isConnected(generators.get(gindex%generators.size()))){//Check that the current index is not connected
						c.setBuyFrom(generators.get(gindex%generators.size()));
						(generators.get(gindex%generators.size())).setSellTo(c);
						smartPrint.println(1,c.getName()+" connected to "+generators.get(gindex%generators.size()).getName());
					}
					gindex++;
					conCount++;
				}while(((double)conCount/(double)generators.size())<p);
				c.setBuyFrom(mainGrid);
				mainGrid.setSellTo(c);
			}
		}
		else{
			smartPrint.println(0, "Warning: Connection ratio p must be between [0,1], "+p+" is not an acceptable input.");
		}
		
	}
}
