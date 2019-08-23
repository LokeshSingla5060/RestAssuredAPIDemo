package testCases;

import java.util.LinkedHashMap;

import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import businessUtilities.ValidateResponses;
import entities.TestData;
import io.restassured.response.Response;
import utilities.CSVFileReader;
import utilities.CreateJSONRequest;
import utilities.ReadProperties;
import utilities.ReportUtility;
import utilities.SubmitRequest;

public class FirstTestCase extends ReportUtility {
	private static final String VERIFY_RESPONSE = "VerifyResponse";
	// Define Constants
	private static final String USER_DIR = "user.dir";
	private static final String NO_TEST_CASE_IS_MARKED_AS_Y = "No Test Case is marked as Y";
	private static final String EXCEPTION_DETAILS = "Exception Details";
	private static final String EXCEPTION_IN_READING_TEST_DATA_FOR_PROPENSITY_SCORE_RANGE = "Exception in reading Test Data for Propensity Score Range";
	private static final String API_NAME = "APIName_";
	private static final String SUITE_CATEGORY = "SuiteCategory_";
	private static final String TEST_CASE_NAME = "TestCaseName_";
	private static final String ACTION_KEYWORDS = "ActionKeywords_";
	private static final String POST_REQUEST = "PostRequest";
	private static final String CREATE_BODY = "CreateBody";
	private static final String CREATE_HEADER = "CreateHeader";
	private static final String POSITIVE_SCENARIO = "PositiveScenario";
	private static final String NEGATIVE_SCENARIO = "NegativeScenario";

	// Define Objects
	LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
	int totalTestCaseCount;
	ExtentTest test, scenarioTypeTest;
	TestData testData = new TestData();
	CreateJSONRequest createJSONRequest = new CreateJSONRequest();
	SubmitRequest submitRequest = new SubmitRequest();
	ValidateResponses validateResponse = new ValidateResponses();

	@BeforeClass
	public void setUpConfiguration() {
		try {
			// Read Data from CSV file
			String filePath = System.getProperty(USER_DIR) + ReadProperties.readProperties("TestDataPath");
			CSVFileReader csvFileReader = new CSVFileReader(filePath);
			map = csvFileReader.openCSVReader();
			totalTestCaseCount = CSVFileReader.keyCount.size();
			if (totalTestCaseCount == 0) {
				test = report.createTest("Chat.PostMessageAPI").createNode(NO_TEST_CASE_IS_MARKED_AS_Y);
				report.flush();
				return;
			}
		} catch (Exception e) {
			test = report.createTest(EXCEPTION_IN_READING_TEST_DATA_FOR_PROPENSITY_SCORE_RANGE);
			ExtentTest exception = test.createNode(EXCEPTION_DETAILS);
			exception.log(Status.ERROR, e);
			report.flush();
		}

	}

	@Test
	public void chatPostMessageTestCases() throws ParseException {
		int count = 0, positiveScenarioCount = 0, negativeScenarioCount = 0;

		for (int i = 1; i <= totalTestCaseCount; i++) {
			// set Test Data
			testData.setTestCaseName(map.get(TEST_CASE_NAME + i));
			testData.setSuiteCategory(map.get(SUITE_CATEGORY + i));
			testData.setAPIName(map.get(API_NAME + i));
			testData.setActionKeywords(map.get(ACTION_KEYWORDS + i));

			String scenarioType = testData.getAPIName();
			testData.setAPIName(testData.getAPIName().split("_")[0]);

			if (testData.getAPIName().equalsIgnoreCase(map.get(API_NAME + i).split("_")[0]) && count == 0) {
				count = count + 1;
				test = report.createTest(testData.getAPIName());
//				test.assignCategory(testData.SuiteCategory);
			}
			if (scenarioType.split("_")[1].equalsIgnoreCase(POSITIVE_SCENARIO) && positiveScenarioCount == 0) {
				positiveScenarioCount = positiveScenarioCount + 1;

				scenarioTypeTest = test.createNode(scenarioType.split("_")[1]);
			} else if (scenarioType.split("_")[1].equalsIgnoreCase(NEGATIVE_SCENARIO) && negativeScenarioCount == 0) {
				negativeScenarioCount = negativeScenarioCount + 1;
				scenarioTypeTest = test.createNode(scenarioType.split("_")[1]);
			}
			int c = i + 1;
			if (c == totalTestCaseCount) {
				if (!map.get(API_NAME + i).split("_")[0].equalsIgnoreCase(map.get(API_NAME + (c)).split("_")[0])) {
					count = 0;
					positiveScenarioCount = 0;
					negativeScenarioCount = 0;
				}
			}

			// log test case name in report
			ExtentTest testCaseNameNode = scenarioTypeTest.createNode(testData.getTestCaseName());
			ExtentTest testSteps = null;
			testCaseNameNode.assignCategory(scenarioType);

			if (!testData.getActionKeywords().isEmpty()) {
				String actionKeys = String.valueOf(testData.getActionKeywords());
				String[] actionKeywords = actionKeys.toString().split("#");
//						System.out.println("actionKeys=" + actionKeys);
				for (int keywordC = 0; keywordC < actionKeywords.length; keywordC++) {

					// Create Request Header and store in a variable
					if (actionKeywords[keywordC].contains(CREATE_HEADER)) {
						String parameters = actionKeywords[keywordC].replace("(", "").replace(")", "")
								.replace(CREATE_HEADER, "");
						System.out.println(createJSONRequest.buildRequest(i, parameters, map).keySet());
						testData.setHeaderJson(createJSONRequest.buildRequest(i, parameters, map));
						System.out.println(testData.getHeaderJson().get("Authorization"));
//					testCaseNameNode.createNode("Request Header").log(Status.INFO,
//							MarkupHelper.createCodeBlock(testData.getHeaderJson().toString(), CodeLanguage.JSON));
					} else if (actionKeywords[keywordC].contains(CREATE_BODY)) {
						// Create Request Body and store in a variable
						String parameters = actionKeywords[keywordC].replace("(", "").replace(")", "")
								.replace(CREATE_BODY, "");
						testData.setBodyJson(createJSONRequest.buildRequest(i, parameters, map));
//					testCaseNameNode.createNode("Request Body").log(Status.INFO,
//							MarkupHelper.createCodeBlock(testData.getBodyJson().toJSONString(), CodeLanguage.JSON));
					} else if (actionKeywords[keywordC].contains(POST_REQUEST)) {
						String parameters = actionKeywords[keywordC].replace("(", "").replace(")", "")
								.replace(POST_REQUEST, "");
						// set APILink and expected response code
						testData.setAPILink(parameters.split(";")[0]);
						testData.setExpectedResponseCode(Integer.valueOf(parameters.split(";")[1]));
						testSteps = testCaseNameNode.createNode("Submit Post Request for " + testData.getAPILink());
						testData.setResponse(submitRequest.submitPostRequest(testData, testSteps));

						if (testSteps.getStatus().toString().equalsIgnoreCase("Fail")) {
							report.flush();
							return;
						} else {
							testData.setActualResponseCode(testData.getResponse().getStatusCode());
						}
						validateResponse.validateStatusCode(testData, testSteps);
						if (testSteps.getStatus().toString().equalsIgnoreCase("Fail")) {
							report.flush();
							break;
						}
					} else if (actionKeywords[keywordC].contains(VERIFY_RESPONSE)) {
						// Create Request Body and store in a variable
						String parameters = actionKeywords[keywordC].replace("(", "").replace(")", "")
								.replace(VERIFY_RESPONSE, "");
//						validateResponse.responseBody(i, parameters, map,testData);

						Response response = testData.getResponse();

						String param[] = parameters.split(";");

						for (int p = 0; p < param.length; p++) {
							String responseKey = param[p].split(":")[1];
							if(response.getBody().asString().contains(param[p].split(":")[0])) {
							String actualResponseValue = (String) response.jsonPath().getMap(param[p].split(":")[0])
									.get(responseKey);							
							if (actualResponseValue.equals(map.get(responseKey + "_" + i))) {
								testSteps.log(Status.PASS, "<font color=\"blue\">" + responseKey
										+ "</font>||<font color=\"green\">" + actualResponseValue + "</font>");
							}else {
								testSteps.log(Status.FAIL, "Unable to validate response code : "+actualResponseValue);
								
							}
							}else {
								testSteps.log(Status.FAIL, "Unable to validate response map : "+param[p].split(":")[0]);
								report.flush();
								return;
							}
						}

					}

				}
			}
			report.flush();

		}

	}

}
