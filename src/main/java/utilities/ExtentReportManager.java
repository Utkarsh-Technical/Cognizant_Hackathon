package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
	
	public static ExtentReports report;
	
	public static ExtentReports getReportInstance() {
		if(report == null) {
			String reportName = DateUtils.getTimeStamp() + ".html";
			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "\\test-report\\" + reportName);
			report = new ExtentReports();
			report.attachReporter(sparkReporter);
			
			sparkReporter.config().setDocumentTitle("Automation Report");
			sparkReporter.config().setReportName("Report");
			sparkReporter.config().setTheme(Theme.STANDARD);
			sparkReporter.config().setTimeStampFormat("MMM dd, yyy HH:mm:ss");
		}
		return report;
	}

}
