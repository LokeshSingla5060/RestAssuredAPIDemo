package businessUtilities;

import java.util.LinkedHashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import entities.TestData;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;

public class ValidateResponses {
		
	public void validateStatusCode(TestData testData,ExtentTest testSteps) {
		
		if(testData.getExpectedResponseCode()==testData.getActualResponseCode()) {
//			testSteps.log(Status.PASS, "Response Code is :"+testData.getActualResponseCode());
			testSteps.log(Status.PASS, "<font color=\"blue\"><b>Expected Response Code</b></font>||<font color=\"green\"><b>Actual Response Code</b></font>");
			testSteps.log(Status.PASS, "<font color=\"blue\">"+testData.getExpectedResponseCode()+"</font>||<font color=\"green\">"+testData.getActualResponseCode()+"</font>");	

		}else {
			testSteps.log(Status.INFO, "<font color=\"blue\"><b>Expected Response Code</b></font>||<font color=\"green\"><b>Actual Response Code</b></font>");
			testSteps.log(Status.FAIL, "<font color=\"blue\">"+testData.getExpectedResponseCode()+"</font>||<font color=\"red\">"+testData.getActualResponseCode()+"</font>");	

		}
	}

	public void responseBody(int loopCount,String parameters,LinkedHashMap<String, String> dataMap,TestData testData) throws ParseException {
		Response response =testData.getResponse();
		
		String param[]= parameters.split(";");
		
		
		for(int p =0;p<param.length;p++) {
//			loopCount=loopCount+1;
			System.out.println(dataMap.get(param[p].split(":")[1]+"_"+loopCount));
			if(	response.jsonPath().getMap(param[p].split(":")[0]).get("text").equals(dataMap.get(param[p].split(":")[1]+"_"+loopCount))) {
			}
		}
			
			
	}
	
	
}
