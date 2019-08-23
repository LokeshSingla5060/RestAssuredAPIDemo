package utilities;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import entities.TestData;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class SubmitRequest {

	/**
	 * 
	 * @param testData
	 * @param testSteps
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Response submitPostRequest(TestData testData, ExtentTest testSteps) {
		Response response = null;
		try {
			testSteps.log(Status.INFO, testData.getAPILink());
			testSteps.log(Status.INFO, "<b>Request Header is :</b>");
			testSteps.log(Status.INFO,
					MarkupHelper.createCodeBlock(testData.getHeaderJson().toString(), CodeLanguage.JSON));
			testSteps.log(Status.INFO, "<b>Request Body is :</b>");
			testSteps.log(Status.INFO,
					MarkupHelper.createCodeBlock(testData.getBodyJson().toJSONString(), CodeLanguage.JSON));
			response = RestAssured.given().headers(testData.getHeaderJson()).body(testData.getBodyJson()).when()
					.post(testData.getAPILink());
			testSteps.log(Status.INFO, "<b>Response Body is :</b>");
			testSteps.log(Status.INFO, MarkupHelper.createCodeBlock(response.getBody().asString(), CodeLanguage.JSON));
		} catch (Exception e) {
			testSteps.log(Status.FAIL, e);
		}
		return response;
	}

}

