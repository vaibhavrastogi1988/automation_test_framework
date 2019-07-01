package com.atf.pages;

import com.atf.base.Page;
import org.apache.log4j.Logger;


public class LoggedInHomePage extends Page {
	public static Logger log = Logger.getLogger("devpinoyLogger");
	
	public void validateSuccessfulLogin(String email) {
		try {
			Thread.sleep(2000L);
			click("LOGGED_IN_HOMEPAGE.CLOSE_REGISTER_POP_UP_XPATH");
			Thread.sleep(2000L);
			isElementPresent("LOGGED_IN_HOMEPAGE.LOGIN_SUCCESS_XPATH", "[contains(text(),'"+ email +"')]");
		}
		
		catch(Exception e) {
			log.debug("Failed to validate success login message");
			e.printStackTrace();
		}
	}
	
	public void hover_myaccounts(){
		hoverMoveToElement("HOMEPAGE.MY_ACCOUNT_XPATH");
	}
	
	public LoginPage clickLogOut(){
		actionClick("LOGGED_IN_HOMEPAGE.LOGOUT_XPATH");
		return new LoginPage();
	}
	
	
}
