package smartgrid.utilities;

import java.util.ArrayList;

public class SmartPrint {
	private static volatile SmartPrint instance;//singleton method
	//types[errors,creation,step info,exchanges,formatText,dailyOverviews,globalOverviews,decisions]
	Boolean[] types={true,false,false,false,false,false,false,false};
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
