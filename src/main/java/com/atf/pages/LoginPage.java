package com.atf.pages;

import org.testng.Assert;

import com.atf.base.Page;

public class LoginPage  extends Page{

	public void validatePageRedirectedToLoginPage(){
		Boolean status = isElementPresent("LOGIN_PAGE.EMAIL_ID", "");
		Assert.assertTrue(status, "Page not redirected to Login Page after Logout operation");
		
	}
	
	public void enterEmail(String email){
		type("LOGIN_PAGE.EMAIL_ID", email);
		
	}
	
	public void enterPassword(String password) {
		type("LOGIN_PAGE.PASSWORD_ID", password);
		
	}
	
	public LoggedInHomePage SignIn() {
		click("LOGIN_PAGE.LOGIN_XPATH");
		return new LoggedInHomePage();
	}
	
	public void enterCredentials(String email, String password) {
		enterEmail(email);
		enterPassword(password);
	}
	
}
