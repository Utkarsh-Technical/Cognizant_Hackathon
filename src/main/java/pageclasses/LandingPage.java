package pageclasses;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import baseclasses.PageBaseClass;
import utilities.ExcelDataFile;

public class LandingPage extends PageBaseClass {

	private JavascriptExecutor javascript;

	public LandingPage(WebDriver driver, ExtentTest logger) {
		super(driver, logger);
		javascript = (JavascriptExecutor) driver;
	}

	@FindBy(id = "menu-item-dropdown-2696")
	WebElement menuItem_calculator;

	@FindBy(id = "menu-item-2423")
	WebElement calculatorOption_loanCalculator;

	@FindBy(id = "loanamount")
	WebElement loanAmount_textField;

	@FindBy(id = "loaninterest")
	WebElement loanInterest_textField;

	@FindBy(id = "loanterm")
	WebElement loanTenure_textField;

	@FindBy(xpath = "//li[@id='car-loan']//a")
	WebElement carLoan_tab;

	@FindBy(xpath = "//*[@id='emiamount']/p")
	WebElement emiAmount_value;

	@FindBy(xpath = "//*[@id='emitotalinterest']/p")
	WebElement totalInterest_value;

	@FindBy(xpath = "//*[@id='emitotalamount']/p")
	WebElement totalPayment_value;

	@FindBy(xpath = "//*[@id='emipaymenttable']/table/tbody/tr[1]")
	WebElement emiPaymentTable_headerRow;

	@FindBy(xpath = "//*[@id='emipaymenttable']/table/tbody/tr[contains(@class, 'yearlypaymentdetails')]")
	List<WebElement> emiPaymentTable_dataRows;

	public void setLoanAmount(String amount) {
		try {
			loanAmount_textField.clear();
			loanAmount_textField.sendKeys(amount);
			logger.log(Status.PASS, "Entered Loan Amount : " + amount);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public void setLoanIntrest(String interestRate) {
		try {
			javascript.executeScript("arguments[0].value='" + interestRate + "'", loanInterest_textField);
			logger.log(Status.PASS, "Entered Loan Interest Rate : " + interestRate);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public void setLoanTenure(String tenure) {
		try {
			javascript.executeScript("arguments[0].value='" + tenure + "'", loanTenure_textField);
			loanTenure_textField.click();
			logger.log(Status.PASS, "Entered Loan Tenure : " + tenure);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public void verifyEmiAmount(String expectedEmiAmount) {
		try {
			String emiAmount = emiAmount_value.getText();
			Assert.assertEquals(emiAmount, expectedEmiAmount);
			logger.log(Status.PASS,
					"Actual EMI Amount : " + emiAmount + " - equals to Expected EMI Amount : " + expectedEmiAmount);
			System.out.println("EMI Amount : " + emiAmount);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public void verifyTotalInterest(String expectedTotalIntrest) {
		try {
			String totalIntrest = totalInterest_value.getText();
			Assert.assertEquals(totalIntrest, expectedTotalIntrest);
			logger.log(Status.PASS, "Actual Total Intrest : " + totalIntrest + " - equals to Expected Total Intrest : "
					+ expectedTotalIntrest);
			System.out.println("Total Intrest : " + totalIntrest);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public void verifyTotaPayment(String expectedTotalPayment) {
		try {
			String totalPayment = totalPayment_value.getText();
			Assert.assertEquals(totalPayment, expectedTotalPayment);
			logger.log(Status.PASS, "Actual Total Payment : " + totalPayment + " - equals to Expected Total Payment : "
					+ expectedTotalPayment);
			System.out.println("Total Payment : " + totalPayment);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public void clickCarLoan() {
		try {
			carLoan_tab.click();
			logger.log(Status.PASS, "Clicked Car Loan tab");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public void extractDataFromTable(String sheetName) {
		try {
			logger.log(Status.INFO, "Storing data into Excel File in Sheet " + sheetName);
			ExcelDataFile excelfile = new ExcelDataFile(
					System.getProperty("user.dir") + "\\testdata\\TestOutputData.xlsx");

			if (excelfile.isSheetExist(sheetName)) {
				excelfile.removeSheet(sheetName);
			}
			excelfile.addSheet(sheetName);

			List<WebElement> columns = emiPaymentTable_headerRow.findElements(By.xpath("//th"));

			int colNum = 1;
			for (int i = 0; i < columns.size(); i++) {
				if (!(columns.get(i).getText().isBlank() || columns.get(i).getText().isEmpty())) {
					System.out.println(columns.get(i).getText());
					excelfile.setCellData(sheetName, colNum, 1, columns.get(i).getText());
					colNum++;
				}
			}

			for (int i = 0; i < emiPaymentTable_dataRows.size(); i++) {
				columns = emiPaymentTable_dataRows.get(i).findElements(By.tagName("td"));
				for (int j = 0; j < columns.size(); j++) {
					System.out.println(columns.get(j).getText());
					excelfile.setCellData(sheetName, j + 1, i + 2, columns.get(j).getText());
				}
			}
			logger.log(Status.PASS, "Data Stored Successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public LoanCalculator navigateToLoanCalc() {
		try {
			menuItem_calculator.click();
			logger.log(Status.PASS, "Clicked Calculator Menu Option");

			calculatorOption_loanCalculator.click();
			logger.log(Status.PASS, "Clicked Loan Calculator Option");

			LoanCalculator loanCalculator = new LoanCalculator(driver, logger);
			PageFactory.initElements(driver, loanCalculator);
			logger.log(Status.PASS, "Navigated to Loan Calculator Page");
			return loanCalculator;
		} catch (Exception e) {
			reportFail(e.getMessage());
			return null;
		}
	}

}
