package smartgrid.web.bridge;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.corba.se.impl.orbutil.ObjectWriter;

import smartgrid.agents.*;
import smartgrid.utilities.SmartPrint; 

public class WebSync {
	ArrayList<Agent> agents = new ArrayList<Agent>();
	SmartPrint smartPrint = SmartPrint.getInstance();
	DecimalFormat df = new DecimalFormat("#####.##");
	
	public WebSync(ArrayList<Agent> agents){
	 this.agents=agents;
	}
	
	public void saveTest(String testName, String description, int cCount, int wCount, int sCount, int stCount, double connectivity, double[] cCons, double[] sGen, double[] wGen) throws IOException{
		smartPrint.println(8,"Saving Test to json file.");
		//Hackish workaround to solve the cyclic serilization issue
		ArrayList<String[]>links = new ArrayList<String[]>();
		for(int i=0;i<agents.size();i++){
			ArrayList<Agent>sellers=agents.get(i).getBuysFrom();
			for(int j=0;j<sellers.size();j++){
				String[] temp=new String[2];
				temp[0]=agents.get(i).getName();
				temp[1]=sellers.get(j).getName();
				links.add(temp);
			}
		}
		
		String fileName="";
		Gson gson = new GsonBuilder().setExclusionStrategies(new SerializationExclusion()).create();
		try {
			//TODO prevent files from being overwritten because config file will still show them
			testName=testName.replace(" ", "_");
			File f = new File("C:/tests/"+testName+".json");
			if(f.exists() && !f.isDirectory()) { 
			    int i=-1;
			    while(f.exists() && !f.isDirectory())
			    {
			    	i++;
			    	f=new File("C:/tests/"+testName+"("+i+").json");	
			    }
			    testName=testName+"("+i+")";
			}
			Test test = new Test(testName, description, agents, SmartGridDriver.getGlobal('D'), cCount, wCount, sCount, stCount, connectivity, links, cCons, sGen, wGen );
			String json =gson.toJson(test);// gson.toJson(agents);
			FileWriter writer = new FileWriter("C:/tests/"+testName+".json");
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			smartPrint.println(0,"Error Saving the Json file.");
			e.printStackTrace();
		}
		//Updates the test.config file so tests can be loaded dynamically
		String testConfigString=testName+","+description+","+cCount+","+sCount+","+wCount+","+stCount+","+SmartGridDriver.getGlobal('D');
		File testConfig = new File("C:/tests/tests.config");
	    BufferedWriter configwriter = new BufferedWriter(new FileWriter(testConfig,true));
	    configwriter.write(testConfigString);
	    configwriter.newLine();
	    configwriter.close();
		smartPrint.println(8,"Done Saving");
		
	}
}

class SerializationExclusion implements ExclusionStrategy {

   @Override
	public boolean shouldSkipField(FieldAttributes fa) {                
        String className = fa.getDeclaringClass().getName();
        String fieldName = fa.getName();
        return 
            ((className.equals("smartgrid.agents.MainGrid")
                && fieldName.equals("buysFrom")) || (className.equals("smartgrid.agents.MainGrid")
                && fieldName.equals("sellsTo")));
    }

    @Override
    public boolean shouldSkipClass(Class<?> type) {
        // never skips any class
        return false;
    }
}