package smartgrid.web.bridge;

public class Configuration {
	 String testName;
	 String description;
	 int daysS;
	 double connectivity;
	 double mainBuy;
	 double mainSell;
	 int storageCount;
	 double stCap;
	 double stDecay;
	 double stCapVar;
	 int consumerCount;
	 double[] cConsumption;
	 double cConsVar;
	 int solarCount;
	 double[] sGeneration;
	 double sGenVar;
	 int windCount;
	 double[] wGeneration;
	 double wGenVar;
	 
	 public Configuration(String testName, String description, int daysS, double connectivity, double mainBuy, double mainSell, int storageCount, double stCap, double stDecay, double stCapVar, int consumerCount,double[] cConsumption, double cConsVar, int solarCount, double[] sGeneration, double sGenVar, int windCount, double[] wGeneration, double wGenVar){
		 this.testName=testName;
		 this.description=description;
		 this.daysS=daysS;
		 this.connectivity=connectivity;
		 this.mainBuy= mainBuy;
		 this.mainSell=mainSell;
		 this.storageCount=storageCount;
		 this.stCap=stCap;
		 this.stDecay=stDecay;
		 this.stCapVar=stCapVar;
		 this.consumerCount=consumerCount;
		 this.cConsumption=cConsumption;
		 this.cConsVar= cConsVar;
		 this.solarCount=solarCount;
		 this.sGeneration=sGeneration;
		 this.sGenVar=sGenVar;
		 this.windCount=windCount;
		 this.wGeneration=wGeneration;
		 this.wGenVar=wGenVar;
	 }

	public String getTestName() {
		return testName;
	}

	public String getDescription() {
		return description;
	}

	public int getDaysS() {
		return daysS;
	}

	public double getConnectivity() {
		return connectivity;
	}

	public double getMainBuy() {
		return mainBuy;
	}

	public double getMainSell() {
		return mainSell;
	}

	public int getStorageCount() {
		return storageCount;
	}

	public double getStCap() {
		return stCap;
	}

	public double getStDecay() {
		return stDecay;
	}

	public double getStCapVar() {
		return stCapVar;
	}

	public int getConsumerCount() {
		return consumerCount;
	}

	public double[] getcConsumption() {
		return cConsumption;
	}

	public double getcConsVar() {
		return cConsVar;
	}

	public int getSolarCount() {
		return solarCount;
	}

	public double[] getsGeneration() {
		return sGeneration;
	}

	public double getsGenVar() {
		return sGenVar;
	}

	public int getWindCount() {
		return windCount;
	}

	public double[] getwGeneration() {
		return wGeneration;
	}

	public double getwGenVar() {
		return wGenVar;
	}
	
	public void setTestName(String testName){
		this.testName=testName;
	}
}
