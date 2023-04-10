package testclasses;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import baseclasses.BaseTestClass;
import baseclasses.PageBaseClass;
import pageclasses.LandingPage;
import pageclasses.LoanCalculator;
import utilities.Constants;

public class TestCases extends BaseTestClass {
	
	
	LandingPage landingPage;
	LoanCalculator loanCalculator;
	
	
	@Test(priority = 1)
	public void test_homeLoan() {
		logger = report.createTest("Home Loan Calculator Report");
		
		invokeBrowser("chrome");
		PageBaseClass pageBaseClass = new PageBaseClass(driver, logger);
		PageFactory.initElements(driver, pageBaseClass);
		landingPage = pageBaseClass.openApplication("https://emicalculator.net/");
		landingPage.getTitle("EMI Calculator for Home Loan, Car Loan & Personal Loan in India");
		pageBaseClass.scrollPage(200);
		landingPage.setLoanAmount(Constants.loanAmount);
		landingPage.setLoanIntrest(Constants.intrestRate);
		landingPage.setLoanTenure(Constants.loanTenure);
		pageBaseClass.scrollPage(400);
		waitLoad(2);
		landingPage.verifyEmiAmount("₹1,31,525");
		landingPage.verifyTotalInterest("₹78,303");
		landingPage.verifyTotaPayment("₹15,78,303");
		pageBaseClass.scrollPage(1200);
		landingPage.extractDataFromTable("home_loan");
		System.out.println("Home Loan Test Case Passed");
		pageBaseClass.reportPass("Home Loan Test Passed Successfully");
	}
	
	@Test(priority = 2)
	public void test_carLoan() {
		logger = report.createTest("Car Loan Calculator Report");
		
		invokeBrowser("chrome");
		PageBaseClass pageBaseClass = new PageBaseClass(driver, logger);
		PageFactory.initElements(driver, pageBaseClass);
		landingPage = pageBaseClass.openApplication("https://emicalculator.net/");
		landingPage.getTitle("EMI Calculator for Home Loan, Car Loan & Personal Loan in India");
		pageBaseClass.scrollPage(200);
		landingPage.clickCarLoan();
		landingPage.setLoanAmount(Constants.loanAmount);
		landingPage.setLoanIntrest(Constants.intrestRate);
		landingPage.setLoanTenure(Constants.loanTenure);
		pageBaseClass.scrollPage(400);
		waitLoad(2);
		landingPage.verifyEmiAmount("₹1,31,525");
		landingPage.verifyTotalInterest("₹78,303");
		landingPage.verifyTotaPayment("₹15,78,303");
		pageBaseClass.scrollPage(1200);
		landingPage.extractDataFromTable("car_loan");
		System.out.println("Car Loan Test Case Passed");
		pageBaseClass.reportPass("Car Loan Test Passed Successfully");
	}
	
	@Test(priority = 3)
	public void test_EmiCalculator() {
		logger = report.createTest("EMI Calculator Report");
		
		invokeBrowser("chrome");
		PageBaseClass pageBaseClass = new PageBaseClass(driver, logger);
		PageFactory.initElements(driver, pageBaseClass);
		landingPage = pageBaseClass.openApplication("https://emicalculator.net/");
		landingPage.getTitle("EMI Calculator for Home Loan, Car Loan & Personal Loan in India");
		loanCalculator = landingPage.navigateToLoanCalc();
		waitForPageLoad();
		loanCalculator.handleAd();
		landingPage.getTitle("Loan Calculator — Calculate EMI, Affordability, Tenure & Interest Rate");
		loanCalculator.setLoanAmount(Constants.loanAmount);
		loanCalculator.setLoanIntrest(Constants.intrestRate);
		loanCalculator.setLoanTenure(Constants.loanTenure);
		loanCalculator.setFees(Constants.fees);
		loanCalculator.clickLoanAmountField();
		loanCalculator.verifyLoanEmi("1,31,525.27");
		loanCalculator.verifyLoanApr("10.76");
		pageBaseClass.scrollPage(600);
		loanCalculator.verifyTotalInterest("78,303");
		loanCalculator.verifyTotaPayment("15,88,303");
		pageBaseClass.scrollPage(1000);
		loanCalculator.extractDataFromTable("emi_calculator");
		System.out.println("EMI Calculator Test Case Passed");
		pageBaseClass.reportPass("EMI Calculator Test Passed Successfully");
	}

}
