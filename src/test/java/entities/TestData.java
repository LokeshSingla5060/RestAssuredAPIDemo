package entities;

import org.json.simple.JSONObject;

import io.restassured.response.Response;

public class TestData {

	
	public String TestCaseName;
	public String SuiteCategory;
	public String APILink;	
	public String Description;
	public int expectedtResponseCount;
	public String APIName;
	public int expectedResponseCode;
	public int actualResponseCode;
	public String JsonDataPath;
	public JSONObject headerJson;
	public JSONObject bodyJson;
	public Response response;
	
	public Response getResponse() {
		return response;
	}
	public void setResponse(Response response) {
		this.response = response;
	}
	public JSONObject getBodyJson() {
		return bodyJson;
	}
	public void setBodyJson(JSONObject bodyJson) {
		this.bodyJson = bodyJson;
	}
	public JSONObject getPayloadJson() {
		return payloadJson;
	}
	public void setPayloadJson(JSONObject payloadJson) {
		this.payloadJson = payloadJson;
	}
	public JSONObject payloadJson;
	public String submissionId;
	public String ActionKeywords;

	public String getActionKeywords() {
		return ActionKeywords;
	}
	public void setActionKeywords(String actionKeywords) {
		ActionKeywords = actionKeywords;
	}

	
	public String getSubmissionId() {
		return submissionId;
	}
	public void setSubmissionId(String submissionId) {
		this.submissionId = submissionId;
	}
	public JSONObject getHeaderJson() {
		return headerJson;
	}
	public void setHeaderJson(JSONObject headerJson) {
		this.headerJson = headerJson;
	}
	public String getJsonDataPath() {
		return JsonDataPath;
	}
	public void setJsonDataPath(String jsonDataPath) {
		JsonDataPath = jsonDataPath;
	}

	public int getExpectedResponseCode() {
		return expectedResponseCode;
	}
	public void setExpectedResponseCode(int expectedResponseCode) {
		this.expectedResponseCode = expectedResponseCode;
	}
	public int getActualResponseCode() {
		return actualResponseCode;
	}
	public void setActualResponseCode(int actualResponseCode) {
		this.actualResponseCode = actualResponseCode;
	}
	public int getExpectedtResponseCount() {
		return expectedtResponseCount;
	}
	public void setExpectedtResponseCount(int expectedtResponseCount) {
		this.expectedtResponseCount = expectedtResponseCount;
	}
	
	public String getAPIName() {
		return APIName;
	}
	public void setAPIName(String aPIName) {
		APIName = aPIName;
	}
	
	public String getTestCaseName() {
		return TestCaseName;
	}
	public void setTestCaseName(String testCaseName) {
		TestCaseName = testCaseName;
	}
	public String getSuiteCategory() {
		return SuiteCategory;
	}
	public void setSuiteCategory(String suiteCategory) {
		SuiteCategory = suiteCategory;
	}
	public String getAPILink() {
		return APILink;
	}
	public void setAPILink(String aPILink) {
		APILink = aPILink;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	
}
