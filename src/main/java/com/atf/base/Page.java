package com.atf.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.atf.utilities.ExtentManager;
import com.atf.utilities.Utilities;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.atf.utilities.PropertyConstants;
import com.atf.utilities.OR;


public class Page {

	public static WebDriver driver;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static WebDriverWait wait;
	public ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;
	public static String browser;

	public Page() {

		if (driver == null) {

			// Jenkins Browser filter configuration
			if (System.getenv("browser") != null && !System.getenv("browser").isEmpty()) {

				browser = System.getenv("browser");
			} else {

				browser = PropertyConstants.getProperty("CONFIG.BROWSER");

			}

			if (PropertyConstants.getProperty("CONFIG.BROWSER").equals("firefox")) {

				// System.setProperty("webdriver.gecko.driver", "gecko.exe");
				driver = new FirefoxDriver();

			} else if (PropertyConstants.getProperty("CONFIG.BROWSER").equals("chrome")) {

				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")
						+ "\\src\\test\\resources\\com\\atf\\executables\\chromedriver.exe");

				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("profile.default_content_setting_values.notifications", 2);
				prefs.put("credentials_enable_service", false);
				prefs.put("profile.password_manager_enabled", false);
				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("prefs", prefs);
				options.addArguments("--disable-extensions");
				options.addArguments("--disable-infobars");

				driver = new ChromeDriver(options);
			} else if (PropertyConstants.getProperty("CONFIG.BROWSER").equals("ie")) {

				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();

			}
			try {
				driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
				driver.get(PropertyConstants.getProperty("CONFIG.TEST_SITE_URL"));
			}
			catch (TimeoutException e){
				JavascriptExecutor js = (JavascriptExecutor) driver;  
				js.executeScript("window.stop();");
			}
			log.debug("Navigated to : " + PropertyConstants.getProperty("CONFIG.TEST_SITE_URL"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(PropertyConstants.getProperty("CONFIG.IMPLICIT_WAIT")),
					TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, 5);

		}
	}

	public static void quit() {

		driver.quit();

	}

	// Common Keywords
	public static void click(String locator) {
		try {
			if (locator.endsWith("_CSS")) {
				driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
			} else if (locator.endsWith("_XPATH")) {
				driver.findElement(By.xpath(OR.getProperty(locator))).click();
			} else if (locator.endsWith("_ID")) {
				driver.findElement(By.id(OR.getProperty(locator))).click();
			}
			else {
				Assert.fail("Locator type missing.");
			}
			log.debug("Clicking on an Element : " + locator);
			test.log(LogStatus.INFO, "Clicking on : " + locator);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void type(String locator, String value) {
		try {
			if (locator.endsWith("_CSS")) {
				driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(Keys.chord(Keys.CONTROL, "a"), value);
			} else if (locator.endsWith("_XPATH")) {
				driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(Keys.chord(Keys.CONTROL, "a"), value);
			} else if (locator.endsWith("_ID")) {
				driver.findElement(By.id(OR.getProperty(locator))).sendKeys(Keys.chord(Keys.CONTROL, "a"), value);
			}else {
				Assert.fail("Locator type missing.");
			}
	
			log.debug("Typing in an Element : " + locator + " entered value as : " + value);
	
			test.log(LogStatus.INFO, "Typing in : " + locator + " entered value as " + value);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	static WebElement dropdown;

	public void select(String locator, String value) {
		try {
			if (locator.endsWith("_CSS")) {
				dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
			} else if (locator.endsWith("_XPATH")) {
				dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
			} else if (locator.endsWith("_ID")) {
				dropdown = driver.findElement(By.id(OR.getProperty(locator)));
			}else {
				Assert.fail("Locator type missing.");
			}
	
			Select select = new Select(dropdown);
			select.selectByVisibleText(value);
	
			log.debug("Selecting from an element : " + locator + " value as : " + value);
			test.log(LogStatus.INFO, "Selecting from dropdown : " + locator + " value as " + value);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isElementPresent(String locator, String custom) {
		log.debug("Validate element is present : " + locator + custom);
		test.log(LogStatus.INFO, "Validate element is present : " + locator + custom);
		try {
			if (locator.endsWith("_CSS")) {
				driver.findElement(By.cssSelector(OR.getProperty(locator) + custom));
			} else if (locator.endsWith("_XPATH")) {
				driver.findElement(By.xpath(OR.getProperty(locator) + custom));
			} else if (locator.endsWith("_ID")) {
				driver.findElement(By.id(OR.getProperty(locator) + custom));
			}else {
				Assert.fail("Locator type missing.");
			}
			return true;
			

		} catch (NoSuchElementException e) {
			return false;

		}

	}
	
	public List<WebElement> returnElementsPresent(String locator, String custom) {
		log.debug("Validate elements is present : " + locator + custom);
		test.log(LogStatus.INFO, "Validate elements is present : " + locator + custom);
		try {
			if (locator.endsWith("_CSS")) {
				return driver.findElements(By.cssSelector(OR.getProperty(locator) + custom));
			} else if (locator.endsWith("_XPATH")) {
				return driver.findElements(By.xpath(OR.getProperty(locator) + custom));
			} else if (locator.endsWith("_ID")) {
				return driver.findElements(By.id(OR.getProperty(locator) + custom));
			} else {
				Assert.fail("Locator type missing.");
			}
			

		} catch (Exception e) {
			log.debug("Unable to locate elements : " + locator + custom);
			test.log(LogStatus.FAIL, "Unable to locate elements : " + locator + custom);
			e.printStackTrace();

		}
		return null;

	}

	public static void verifyEquals(String expected, String actual) throws IOException {

		try {

			Assert.assertEquals(actual, expected);

		} catch (Throwable t) {

			Utilities.captureScreenshot();
			// ReportNG
			Reporter.log("<br>" + "Verification failure : " + t.getMessage() + "<br>");
			Reporter.log("<a target=\"_blank\" href=" + Utilities.screenshotName + "><img src="
					+ Utilities.screenshotName + " height=200 width=200></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			// Extent Reports
			test.log(LogStatus.FAIL, " Verification failed with exception : " + t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(Utilities.screenshotName));

		}

	}
	
	public static void hoverMoveToElement(String locator) {
		try {
			log.debug("Hover over to the element : " + locator);
			test.log(LogStatus.INFO, "Hover over to the element : " + locator);
			Actions action = new Actions(driver);
			action.moveToElement(getElementByLocator(locator)).perform();
		}
		catch(Exception e) {
			log.debug("Unable to hover to element : " + locator);
			test.log(LogStatus.FAIL, "Unable to hover to element : " + locator);
			e.printStackTrace();
		}
		
	}
	
	public static void actionClick(String locator) {
		try {
			log.debug("Click Element using Action Click : " + locator);
			test.log(LogStatus.INFO, "Click Element using Action Click : " + locator);
			Actions action = new Actions(driver);
			action.moveToElement(getElementByLocator(locator)).click().perform();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static WebElement getElementByLocator(String locator) {
		try {
			log.debug("Fetching locator : " + locator);
			test.log(LogStatus.INFO, "Fetching locator : " + locator);
			if (locator.endsWith("_CSS")) {
				return driver.findElement(By.cssSelector(OR.getProperty(locator)));
			} else if (locator.endsWith("_XPATH")) {
				return driver.findElement(By.xpath(OR.getProperty(locator)));
			} else if (locator.endsWith("_ID")) {
				return driver.findElement(By.id(OR.getProperty(locator)));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void open() {
		log.debug("Navigating to webpage : " + PropertyConstants.getProperty("CONFIG.TEST_SITE_URL"));
		test.log(LogStatus.INFO, "Navigating to webpage : " + PropertyConstants.getProperty("CONFIG.TEST_SITE_URL"));
		try {
				driver.get(PropertyConstants.getProperty("CONFIG.TEST_SITE_URL"));
		}
		catch (TimeoutException e){
			JavascriptExecutor js = (JavascriptExecutor) driver;  
			js.executeScript("window.stop();");
		}
		catch(Exception e) {
			log.debug("Unable to open webpage : " + PropertyConstants.getProperty("CONFIG.TEST_SITE_URL"));
			test.log(LogStatus.FAIL, "Unable to open webpage : " + PropertyConstants.getProperty("CONFIG.TEST_SITE_URL"));
			e.printStackTrace();
		}
	}
	
	public static String switchToPopupWindow() throws InterruptedException {
		try {
			log.debug("Switching to pop up window");
			test.log(LogStatus.INFO, "Switching to pop up window");
			String parentWinHandle = driver.getWindowHandle();
			log.debug("Parent window handle: " + parentWinHandle);
//	        click("REGISTER_PAGE.TEXT_TERMSANDCONDITION_XPATH");
	        // Get the window handles of all open windows
	        Set<String> winHandles = driver.getWindowHandles();
	        // Loop through all handles
	        for(String handle: winHandles){
	            if(!handle.equals(parentWinHandle)){
	            driver.switchTo().window(handle);
	            Thread.sleep(1000);
	            }
	        }
	        return parentWinHandle;
		}
		catch(Exception e) {
			log.debug("Unable to switch to pop up window ");
			test.log(LogStatus.FAIL, "Unable to switch to pop up window ");
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static void closeBrowser() {
		try {
			log.debug("Close the browser");
			test.log(LogStatus.INFO, "Close the browser");
			driver.close();
		}
		catch(Exception e) {
			log.debug("Unable to close window ");
			test.log(LogStatus.FAIL, "Unable to close window ");
			e.printStackTrace();
		}
	}
	
	
	public static void switchToParentWindow(String parentWinHandle) {
		try {
			log.debug("Switch to parent window: "+ parentWinHandle);
			test.log(LogStatus.INFO, "Switch to parent window: "+ parentWinHandle);
			driver.switchTo().window(parentWinHandle);
		}
		catch(Exception e) {
			log.debug("Unable to switch to parent window ");
			test.log(LogStatus.FAIL, "Unable to switch to parent window ");
			e.printStackTrace();
		}
	}
	
	public static String fetchText(String locator) {
		try {
			log.debug("Fetch text for locator: "+ locator);
			test.log(LogStatus.INFO, "Fetch text for locator: "+ locator);
			return getElementByLocator(locator).getText();
		}
		catch(Exception e) {
			log.debug("Unable to fetch text for locator:  "+ locator);
			test.log(LogStatus.FAIL, "Unable to fetch text for locator:  "+ locator);
			e.printStackTrace();
		}
		return null;
	}
}
