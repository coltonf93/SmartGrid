package smartgrid.utilities;

public class SmartPrint {
	private transient static volatile SmartPrint instance;//singleton method
	/*List of SmartPrint categories and their numbers
	 * ------------------------------------------------
	 * 0) Error messages and Warning's Enabled by default
	 * 1) Agent Initialization and Connection
	 * 2) Begin Turn (Buy and Sell Power, and pricing determined.
	 * 3) Exchanges (Who sold to who and for how much)
	 * 4) Hourly Totals (Hourly Metrics)
	 * 5) Daily Totals (Daily Metrics)
	 * 6) Global Totals (Final Metrics on program completion)
	 * 7) Hour and daily text dividers 
	 * 8) Web and connectivity logs
	 */
	Boolean[] types={true,false,false,false,false,false,false,false,false};
	private SmartPrint(){}
	
	public static SmartPrint getInstance(){//singleton implementation
		if(instance == null){
			synchronized (SmartPrint.class){
				if(instance==null){
					instance = new SmartPrint();
				}
			}
		}
		return instance;
	}
	
	public void print(int type, String printStatement){
		if(this.getTypeEnabled(type)){
			System.out.print(printStatement);
		}
	}
	
	public void println(int type, String printStatement){
		if(this.getTypeEnabled(type)){
			System.out.println(printStatement);
		}
	}
	
	public Boolean getTypeEnabled(int type){
		if(types[type]){
			return true;
		}
		return false;
	}
	
	public void enableType(int t){
		if(t<this.types.length && t>=0){
			types[t]=true;
		}
		else{
			System.out.println("Smart print type "+t+" does not exist.");
		}
	}
	
	public void disableType(int t){
		if(t<this.types.length && t>=0){
			this.types[t]=false;
		}
		else{
			System.out.println("Smart print type "+t+" does not exist.");
		}
	}
	
	public void enableTypes(int[] typese){
		for(int i=0;i<typese.length;i++){
			this.enableType(typese[i]);
		}
	}
	
	public void disableTypes(int[] typese){
		for(int i=0;i<typese.length;i++){
			this.disableType(typese[i]);
		}
	}
	
}
