package com.atf.testcases;

import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import com.atf.base.Page;
import com.atf.pages.HomePage;
import com.atf.pages.LoggedInHomePage;
import com.atf.pages.LoginPage;
import com.atf.pages.RegisterPage;
import com.atf.utilities.Utilities;
import com.relevantcodes.extentreports.LogStatus;
import com.atf.utilities.PropertyConstants;

public class TestCases extends Page {
	static String email = Utilities.generateRandomEmail(20);
	static String password = PropertyConstants.getProperty("TEST_DATA.REGISTER_PASSWORD");
	static Properties config = new Properties();
	
	@Test(description="TC_01: Register User", priority=0)
	public void TC_01_Registration_User() throws InterruptedException {
		test.log(LogStatus.INFO, "Opening WebPage");
		HomePage homepage = new HomePage();
		test.log(LogStatus.INFO, "Hover over to My Accounts");
		homepage.hover_myaccounts();
		Thread.sleep(2000L);
		test.log(LogStatus.INFO, "Click SignUp");
		RegisterPage registerPage = homepage.clickSignUp();
		Thread.sleep(5000L);
		test.log(LogStatus.INFO, "Register user with credentials");
		registerPage.registerUser(email, password, password);
		test.log(LogStatus.INFO, "Validate Terms and confitions");
		registerPage.clickTermsAndConditionLink();
		registerPage.switchToTermsAndConditonWindowAndValidateText();
		test.log(LogStatus.INFO, "Validate Privacy policy");
		registerPage.clickPrivacyPolicyLink();
		registerPage.switchToPrivatePolicyWindowAndValidateText();
		test.log(LogStatus.INFO, "Click Register button");
		LoggedInHomePage loggedInHomePage = registerPage.click_register_button();
		test.log(LogStatus.INFO, "Validate User is logged in successfully");
		loggedInHomePage.validateSuccessfulLogin(email);
		Thread.sleep(2000L);
		test.log(LogStatus.INFO, "Logout with current user");
		loggedInHomePage.hover_myaccounts();
		Thread.sleep(2000L);
		LoginPage loginPage = loggedInHomePage.clickLogOut();
		test.log(LogStatus.INFO, "Validate page redirected to login page");
		loginPage.validatePageRedirectedToLoginPage();
		
	}
	
	@Test(description="TC_02: Login with newly created user", priority=1)
	public void TC_02_Login_With_Newly_Created_User() throws InterruptedException {
		test.log(LogStatus.INFO, "Opening WebPage");
		HomePage homepage = new HomePage();
		homepage.openWebpage();
		test.log(LogStatus.INFO, "Click Sign in link");
		LoginPage loginPage = homepage.clickSignIn();
		Thread.sleep(2000L);
		test.log(LogStatus.INFO, "Enter valid credentials and sign in");
		loginPage.enterCredentials(email, password);
		LoggedInHomePage loggedInHomePage = loginPage.SignIn();
		test.log(LogStatus.INFO, "Validate user logged in successfully");
		loggedInHomePage.validateSuccessfulLogin(email);
		Thread.sleep(2000L);
		test.log(LogStatus.INFO, "Logout the user");
		loggedInHomePage.hover_myaccounts();
		Thread.sleep(2000L);
		loginPage = loggedInHomePage.clickLogOut();
		test.log(LogStatus.INFO, "Validate page redirected to login page");
		loginPage.validatePageRedirectedToLoginPage();
		Thread.sleep(5000L);
		
	}
	
	@Test(description="TC_03: Register with same user", priority=2)
	public void TC_03_Registration_With_SameUser() throws InterruptedException {
		test.log(LogStatus.INFO, "Opening WebPage");
		HomePage homepage = new HomePage();
		homepage.openWebpage();
		test.log(LogStatus.INFO, "Hover over my accounts tab");
		homepage.hover_myaccounts();
		Thread.sleep(2000L);
		test.log(LogStatus.INFO, "Click Sign Up");
		RegisterPage registerPage = homepage.clickSignUp();
		Thread.sleep(5000L);
		test.log(LogStatus.INFO, "Enter credentials as already registered user");
		registerPage.registerUser(email, password, password);
		registerPage.click_register_button();
		Thread.sleep(5000L);
		test.log(LogStatus.INFO, "Validate already registered popup");
		registerPage.clickOkPopUpWindow();
		
	}
	
	@Test(description="TC_04: Validate register password strength", priority=3)
	public void TC_04_Register_Password_Strength() throws InterruptedException {
		test.log(LogStatus.INFO, "Opening WebPage");
		HomePage homepage = new HomePage();
		homepage.openWebpage();
		test.log(LogStatus.INFO, "Hover over to My Accounts");
		homepage.hover_myaccounts();
		Thread.sleep(2000L);
		test.log(LogStatus.INFO, "Click Sign Up");
		RegisterPage registerPage = homepage.clickSignUp();
		Thread.sleep(5000L);
		test.log(LogStatus.INFO, "Validate Zu kurz password strength");
		registerPage.registerUser(email, PropertyConstants.getProperty("TEST_DATA.SHORT_PASSWORD"), PropertyConstants.getProperty("TEST_DATA.SHORT_PASSWORD"));
		Thread.sleep(5000L);
		String passwordStrength = registerPage.fetchPasswordStrengthText("REGISTER_PAGE.PASSWORD_STRENGTH_XPATH");
		Assert.assertTrue((passwordStrength.equalsIgnoreCase("Zu kurz")), "Mis-match in password strength text. \nExpected: " +passwordStrength
																			+ "\n Actual: Zu kurz");
		
		Thread.sleep(5000L);
		test.log(LogStatus.INFO, "Validate Zu schwach password strength");
		registerPage.registerUser(email, PropertyConstants.getProperty("TEST_DATA.TOO_WEAK"), PropertyConstants.getProperty("TEST_DATA.TOO_WEAK"));
		Thread.sleep(5000L);
		passwordStrength = registerPage.fetchPasswordStrengthText("REGISTER_PAGE.PASSWORD_STRENGTH_XPATH");
		Assert.assertTrue((passwordStrength.equalsIgnoreCase("Zu schwach")), "Mis-match in password strength text. \nExpected: " +passwordStrength
																			+ "\n Actual: Zu schwach");
		
		Thread.sleep(5000L);
		test.log(LogStatus.INFO, "Validate Ausreichend password strength");
		registerPage.registerUser(email, PropertyConstants.getProperty("TEST_DATA.SUFFICIENT"), PropertyConstants.getProperty("TEST_DATA.SUFFICIENT"));
		Thread.sleep(5000L);
		passwordStrength = registerPage.fetchPasswordStrengthText("REGISTER_PAGE.PASSWORD_STRENGTH_XPATH");
		Assert.assertTrue((passwordStrength.equalsIgnoreCase("Ausreichend")), "Mis-match in password strength text. \nExpected: " +passwordStrength
																			+ "\n Actual: Ausreichend");
		
		Thread.sleep(5000L);
		test.log(LogStatus.INFO, "Validate Stark password strength");
		registerPage.registerUser(email, PropertyConstants.getProperty("TEST_DATA.STRONGLY"), PropertyConstants.getProperty("TEST_DATA.STRONGLY"));
		Thread.sleep(5000L);
		passwordStrength = registerPage.fetchPasswordStrengthText("REGISTER_PAGE.PASSWORD_STRENGTH_XPATH");
		Assert.assertTrue((passwordStrength.equalsIgnoreCase("Stark")), "Mis-match in password strength text. \nExpected: " +passwordStrength
																			+ "\n Actual: Stark");
		
		Thread.sleep(5000L);
		test.log(LogStatus.INFO, "Validate Sehr stark password strength");
		registerPage.registerUser(email, PropertyConstants.getProperty("TEST_DATA.VERY_STRONG"), PropertyConstants.getProperty("TEST_DATA.VERY_STRONG"));
		Thread.sleep(5000L);
		passwordStrength = registerPage.fetchPasswordStrengthText("REGISTER_PAGE.PASSWORD_STRENGTH_XPATH");
		Assert.assertTrue((passwordStrength.equalsIgnoreCase("Sehr stark")), "Mis-match in password strength text. \nExpected: " +passwordStrength
																			+ "\n Actual: Sehr stark");
		
	}
	
	
	@Test(description="TC_05: Negative case: Verify registration with no email and no password ", priority=4)
	public void TC_05_Registration_Without_Information() throws InterruptedException {
		test.log(LogStatus.INFO, "Opening WebPage");
		HomePage homepage = new HomePage();
		homepage.openWebpage();
		test.log(LogStatus.INFO, "Hover over to My Accounts");
		homepage.hover_myaccounts();
		Thread.sleep(2000L);
		test.log(LogStatus.INFO, "Click Sign Up");
		RegisterPage registerPage = homepage.clickSignUp();
		Thread.sleep(5000L);
		email = Utilities.generateRandomEmail(25);
		test.log(LogStatus.INFO, "Register User with empty email and empty password");
		registerPage.registerUser("", "", "");
		registerPage.click_register_button();
		test.log(LogStatus.INFO, "Validate error message");
		Assert.assertTrue(registerPage.validateEmailError(), "Email validation failed");
		Assert.assertTrue(registerPage.validatePasswordError(), "Password validation failed");
		Assert.assertTrue(registerPage.validateRetypePasswordError(), "Re-Type password validation failed");
	}
	
	@Test(description="TC_06: Negative case: Verify registration with incorrect email and correct password ", priority=5)
	public void TC_06_Registration_With_Incorrect_Email_Correct_Password() throws InterruptedException {
		test.log(LogStatus.INFO, "Opening WebPage");
		HomePage homepage = new HomePage();
		homepage.openWebpage();
		test.log(LogStatus.INFO, "Hover over to My Accounts");
		homepage.hover_myaccounts();
		Thread.sleep(2000L);
		test.log(LogStatus.INFO, "Click Sign Up");
		RegisterPage registerPage = homepage.clickSignUp();
		Thread.sleep(5000L);
		test.log(LogStatus.INFO, "Register with incorrect email id");
		registerPage.registerUser(PropertyConstants.getProperty("TEST_DATA.INCORRECT_EMAIL"), password, password);
		registerPage.click_register_button();
		test.log(LogStatus.INFO, "Validate error message");
		Assert.assertTrue(registerPage.validateIncorrectEmailError(), "Incorrect Email validation failed");
	}
	
	
	
	
	@Test(description="TC_07: Negative case: Verify registration with correct email and empty password ", priority=6)
	public void TC_07_Registration_With_Empty_Password() throws InterruptedException {
		test.log(LogStatus.INFO, "Opening WebPage");
		HomePage homepage = new HomePage();
		homepage.openWebpage();
		test.log(LogStatus.INFO, "Hover over to My Accounts");
		homepage.hover_myaccounts();
		Thread.sleep(2000L);
		test.log(LogStatus.INFO, "Click Sign Up");
		RegisterPage registerPage = homepage.clickSignUp();
		Thread.sleep(5000L);
		email = Utilities.generateRandomEmail(20);
		test.log(LogStatus.INFO, "Register user with valid email and empty password");
		registerPage.registerUser(email, "", "");
		registerPage.click_register_button();
		test.log(LogStatus.INFO, "Validate Error message");
		Assert.assertFalse(registerPage.validateEmailError(), "Email validation failed");
		Assert.assertTrue(registerPage.validatePasswordError(), "Password validation failed");
		Assert.assertTrue(registerPage.validateRetypePasswordError(), "Re-Type password validation failed");
	}
	
	
	
	
	@Test(description="TC_08: Negative case: Verify registration with correct email and incorrect password and retype password", priority=7)
	public void TC_08_Registration_Correct_Email_Incorrect_Password() throws InterruptedException {
		test.log(LogStatus.INFO, "Opening WebPage");
		HomePage homepage = new HomePage();
		homepage.openWebpage();
		test.log(LogStatus.INFO, "Hover over to My Accounts");
		homepage.hover_myaccounts();
		Thread.sleep(2000L);
		test.log(LogStatus.INFO, "Click Sign Up");
		RegisterPage registerPage = homepage.clickSignUp();
		Thread.sleep(5000L);
		String temp_email = Utilities.generateRandomEmail(20);
		test.log(LogStatus.INFO, "Register user with correct email and incorrect password and retype password");
		registerPage.registerUser(temp_email, PropertyConstants.getProperty("TEST_DATA.TEMP_PASSWORD_1"), 
										PropertyConstants.getProperty("TEST_DATA.TEMP_PASSWORD_2"));
		registerPage.click_register_button();
		test.log(LogStatus.INFO, "Validate Error message");
		Assert.assertTrue(registerPage.validateElementPresent("REGISTER_PAGE.PASSWORD_NOT_MATCH_ERROR_XPATH", ""), 
																"Incorrect password length validation failed for retype password textbox");
		Assert.assertFalse(registerPage.validateElementPresent("LOGGED_IN_HOMEPAGE.LOGIN_SUCCESS_XPATH", "[contains(text(),'"+ email +"')]"), 
				"Incorrect password length validation failed for retype password textbox");
		
		test.log(LogStatus.INFO, "Register user with correct email and incorrect password and retype password");
		registerPage.registerUser(temp_email, PropertyConstants.getProperty("TEST_DATA.TEMP_PASSWORD_2"), 
				PropertyConstants.getProperty("TEST_DATA.TEMP_PASSWORD_1"));
		registerPage.click_register_button();
		
		test.log(LogStatus.INFO, "Validate Error message");
		Assert.assertTrue(registerPage.validateElementPresent("REGISTER_PAGE.PASSWORD_NOT_MATCH_ERROR_XPATH", ""), 
												"Incorrect password length validation failed for retype password textbox");
		Assert.assertFalse(registerPage.validateElementPresent("LOGGED_IN_HOMEPAGE.LOGIN_SUCCESS_XPATH", "[contains(text(),'"+ email +"')]"), 
				"Incorrect password length validation failed for retype password textbox");
	}
	
	
	
	
	@Test(description="TC_09: Test case to test Search Criteria", priority=8)
	public void TC_09_Search_Criteria() throws InterruptedException {
		test.log(LogStatus.INFO, "Opening WebPage");
		HomePage homepage = new HomePage();
		homepage.openWebpage();
		test.log(LogStatus.INFO, "Input Search text in the Search textbox and click search button");
		homepage.searchAndClick(PropertyConstants.getProperty("TEST_DATA.SEARCH_CRITERIA"));
		test.log(LogStatus.INFO, "Fetch search record count");
		int searchResultCount = homepage.valiadateSearchCriteriaResult("HOMEPAGE.SEARCH_RESULT_XPATH", "");
		test.log(LogStatus.INFO, "Validate search record are more than one");
		Assert.assertTrue(searchResultCount>=1, "Search result count is zero");
	}
	
	@Test(description="TC_10: Negative case: Verify registration with correct email and short and long password ", priority=9)
	public void TC_10_Registration_Correct_Email_Short_Long_Password() throws InterruptedException {
		test.log(LogStatus.INFO, "Opening WebPage");
		HomePage homepage = new HomePage();
		homepage.openWebpage();
		test.log(LogStatus.INFO, "Hover over to My Accounts");
		homepage.hover_myaccounts();
		Thread.sleep(2000L);
		test.log(LogStatus.INFO, "Click Sign Up");
		RegisterPage registerPage = homepage.clickSignUp();
		Thread.sleep(5000L);
		String temp_email = Utilities.generateRandomEmail(20);
		test.log(LogStatus.INFO, "Register user with short password");
		registerPage.registerUser(temp_email, PropertyConstants.getProperty("TEST_DATA.SHORT_PASSWORD"), 
										PropertyConstants.getProperty("TEST_DATA.SHORT_PASSWORD"));
		registerPage.click_register_button();
		test.log(LogStatus.INFO, "Validate Error Message");
		Assert.assertTrue(registerPage.validateElementPresent("REGISTER_PAGE.INCORRECT_PASSWORD_LENGTH_ERROR_XPATH", ""), 
																"Incorrect password length validation failed for password textbox");
		Assert.assertTrue(registerPage.validateElementPresent("REGISTER_PAGE.INCORRECT_RETYPE_PASSWORD_LENGTH_ERROR_XPATH", ""), 
																"Incorrect password length validation failed for retype password textbox");
		Assert.assertFalse(registerPage.validateElementPresent("LOGGED_IN_HOMEPAGE.LOGIN_SUCCESS_XPATH", "[contains(text(),'"+ email +"')]"), 
				"Incorrect password length validation failed for retype password textbox");
	}
	@AfterSuite
	public void tearDown(){
		
		Page.quit();
		
	}

}
