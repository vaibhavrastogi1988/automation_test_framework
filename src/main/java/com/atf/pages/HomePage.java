package com.atf.pages;

import java.util.List;
import org.openqa.selenium.WebElement;
import com.atf.base.Page;

public class HomePage extends Page {
	
	public void hover_myaccounts(){
		hoverMoveToElement("HOMEPAGE.MY_ACCOUNT_XPATH");
	}
	
	public RegisterPage clickSignUp(){
		actionClick("HOMEPAGE.SIGNUP_XPATH");
		return new RegisterPage();
	}
	
	
	public void searchAndClick(String text){
		type("HOMEPAGE.SEARCH_XPATH", text);
		click("HOMEPAGE.SEARCH_BUTTON_XPATH");
	}
	
	
	public int valiadateSearchCriteriaResult(String locator, String customlocator) {
		List<WebElement> searchCount = returnElementsPresent(locator, customlocator);
		return searchCount.size();
	}
	
	public void openWebpage() {
		open();
	}
	
	public LoginPage clickSignIn() {
		click("HOMEPAGE.SIGNIN_XPATH");
		return new LoginPage();
	}
}
