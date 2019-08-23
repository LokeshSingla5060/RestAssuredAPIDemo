package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ReportUtility {

	public static ExtentReports report = reportsConfig();

	/**
	 * 
	 * @return
	 */
	public static ExtentHtmlReporter htmlConfiguration() {
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(
				System.getProperty("user.dir") + "\\reports\\AutomationResult.html");
		htmlReporter.config().setDocumentTitle("Automation Report"); // Tile of report
		htmlReporter.config().setReportName("Functional Testing"); // Name of the report
		htmlReporter.config().setTheme(Theme.DARK);

		String js = "document.getElementById(\"test-view-charts\").setAttribute(\"class\",\"subview-full hide\");";
		htmlReporter.config().setJS(js);
		System.out.println(htmlReporter.config().getJS());
		return htmlReporter;
	}

	/**
	 * 
	 * @return
	 */
	public static ExtentReports reportsConfig() {
		report = new ExtentReports();
		report.attachReporter(htmlConfiguration());
		return report;
	}

}
