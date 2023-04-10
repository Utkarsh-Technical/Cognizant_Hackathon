package baseclasses;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import pageclasses.LandingPage;
import utilities.DateUtils;

public class PageBaseClass extends BaseTestClass {

	public WebDriver driver;
	public ExtentTest logger;

	public PageBaseClass(WebDriver driver, ExtentTest logger) {
		this.driver = driver;
		this.logger = logger;
	}

	/**
	 * Opens the website using the url
	 * 
	 * @param url url of the website to be opened
	 * 
	 * @return Landing page object
	 */
	public LandingPage openApplication(String url) {
		logger.log(Status.INFO, "Opening the Website");
		driver.get(url);
		logger.log(Status.INFO, "Successfully Opened the " + url);
		LandingPage landingPage = new LandingPage(driver, logger);
		PageFactory.initElements(driver, landingPage);
		return landingPage;
	}
	
	public void getTitle(String expectedTitle) {
		try {
			Assert.assertEquals(driver.getTitle(), expectedTitle);
			reportPass("Actual Title : " + driver.getTitle() + " - equals to Expected Title : " + expectedTitle);
		} catch(Exception e) {
			reportFail(e.getMessage());
		}
	}
	
	public void scrollPage(int pixels) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0," + pixels + ")");
	}
	
	public void scrollPageToElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView()", element);
	}

	public void reportFail(String reportString) {
		logger.log(Status.FAIL, reportString);
		takeScreenshot("Fail");
		Assert.fail(reportString);
	}

	public void reportPass(String reportString) {
		takeScreenshot("Pass");
		logger.log(Status.PASS, reportString);
	}

	public void takeScreenshot(String status) {
		TakesScreenshot takescreenshot = (TakesScreenshot) driver;
		File sourceFile = takescreenshot.getScreenshotAs(OutputType.FILE);

		File destFile = new File(
				System.getProperty("user.dir") + "\\screenshots\\" + status + "-" + DateUtils.getTimeStamp() + ".png");
		try {
			FileUtils.copyFile(sourceFile, destFile);
			logger.addScreenCaptureFromPath(
					System.getProperty("user.dir") + "\\screenshots\\" + status + "-" + DateUtils.getTimeStamp() + ".png");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
