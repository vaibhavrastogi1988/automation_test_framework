package com.atf.pages;

import org.testng.Assert;

import com.atf.base.Page;

public class RegisterPage extends Page {
	
	public void enter_email(String username){
		type("REGISTER_PAGE.EMAIL_ID", username);
	}
	
	public void enter_password(String password){
		type("REGISTER_PAGE.PASSWORD_ID", password);
	}
	
	public void enter_retype_password(String password){
		type("REGISTER_PAGE.RETYPE_password_ID", password);
	}
	
	public LoggedInHomePage click_register_button(){
		click("REGISTER_PAGE.REGISTER_XPATH");
		return new LoggedInHomePage();
		
	}
	
	public void registerUser(String username, String password, String retypePassword) {
		enter_email(username);
		enter_password(password);
		enter_retype_password(retypePassword);
	}
	
	public void clickTermsAndConditionLink() {
		click("REGISTER_PAGE.LINK_TERMS_AND_CONDITION_XPATH");
	}
	
	public void switchToTermsAndConditonWindowAndValidateText() throws InterruptedException {
		String parentWindow = switchToPopupWindow();
		Assert.assertTrue(isElementPresent("REGISTER_PAGE.TEXT_TERMSANDCONDITION_XPATH", ""),"Failed to fetch text on Terms and conditions page");
		Thread.sleep(1000L);
		closeBrowser();
		Thread.sleep(2000L);
		switchToParentWindow(parentWindow);
	}
	
	public void switchToPrivatePolicyWindowAndValidateText() throws InterruptedException {
		String parentWindow = switchToPopupWindow();
		Assert.assertTrue(isElementPresent("REGISTER_PAGE.TEXT_PRIVATE_POLICY_XPATH", ""),"Failed to fetch text on Privacy policy page");
		Thread.sleep(1000L);
		closeBrowser();
		Thread.sleep(2000L);
		switchToParentWindow(parentWindow);
	}
	
	
	public void clickPrivacyPolicyLink() {
		click("REGISTER_PAGE.LINK_PRIVACY_POLICY_XPATH");
	}
	
	public boolean validateEmailError() {
		try {
			Thread.sleep(2000L);
			return isElementPresent("REGISTER_PAGE.EMAIL_ERROR_XPATH", "");
		}
		
		catch(Exception e) {
			log.debug("Failed to validate error message for email textbox");
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean validatePasswordError() {
		try {
			Thread.sleep(2000L);
			return isElementPresent("REGISTER_PAGE.PASSWORD_ERROR_XPATH", "");
		}
		
		catch(Exception e) {
			log.debug("Failed to validate error message for password textbox");
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean validateRetypePasswordError() {
		try {
			Thread.sleep(2000L);
			return isElementPresent("REGISTER_PAGE.PASSWORD_RETYPE_ERROR_XPATH", "");
		}
		
		catch(Exception e) {
			log.debug("Failed to validate error message for password re-type textbox");
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean validateIncorrectEmailError() {
		try {
			Thread.sleep(2000L);
			return isElementPresent("REGISTER_PAGE.INCORRECT_EMAIL_ERROR_XPATH", "");
		}
		
		catch(Exception e) {
			log.debug("Failed to validate error message for incorrect email textbox");
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean validateElementPresent(String locator, String custom) {
		try {
			Thread.sleep(2000L);
			return isElementPresent(locator, custom);
		}
		
		catch(Exception e) {
			log.debug("Failed to validate error message for incorrect password length");
			e.printStackTrace();
		}
		return false;
	}
	
	public void clickOkPopUpWindow() {
		click("REGISTER_PAGE.BUTTON_USER_EXISTS_OK_XPATH");
	}
	
	public String fetchPasswordStrengthText(String locator) {
		return fetchText(locator);
	}
}
