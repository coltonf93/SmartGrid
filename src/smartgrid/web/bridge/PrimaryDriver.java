package smartgrid.web.bridge;

import java.util.Map;

public class PrimaryDriver {
	public PrimaryDriver(Map<String, String[]> parameters){
		for(String parameter : parameters.keySet()) {
		    if(parameter.toLowerCase().startsWith("sCount")) {
		        //your code here
		    }
		}
	}

}
