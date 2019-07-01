package com.atf.utilities;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.atf.base.Page;

public class Utilities extends Page {

	public static String screenshotPath;
	public static String screenshotName;

	public static void captureScreenshot() throws IOException {

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		Date d = new Date();
		screenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";

		FileUtils.copyFile(scrFile,
				new File(System.getProperty("user.dir") + "\\target\\surefire-reports\\html\\" + screenshotName));

	}

	
	public static String generateRandomEmail(int length) {
	    log.info("Generating a Random email String");
	    String allowedChars = "abcdefghijklmnopqrstuvwxyz" + "1234567890";
	    String email = "";
	    String temp = RandomStringUtils.random(length, allowedChars);
	    email = temp.substring(0, temp.length() - 9) + "@testdata.com";
	    return email;
	}
}
