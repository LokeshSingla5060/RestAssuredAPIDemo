package utilities;

import java.util.LinkedHashMap;

import org.json.simple.JSONObject;

public class CreateJSONRequest {
	
	//build a JSON request in the form of Key Value pair
	@SuppressWarnings("unchecked")
	public JSONObject buildRequest(int loopCount,String parameters,LinkedHashMap<String, String> dataMap) {
		String param[]= parameters.split(";");

		JSONObject jsonObject =new JSONObject();
		for(int p =0;p<param.length;p++) {
//			System.out.println("param="+String.valueOf(param[p])+" value="+dataMap.get(param[p]+"_"+loopCount));		
			jsonObject.put(String.valueOf(param[p]),dataMap.get(param[p]+"_"+loopCount) );					
		}
		return jsonObject;		
		
	}

}

