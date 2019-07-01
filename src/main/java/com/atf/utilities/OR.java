package com.atf.utilities;

import java.io.BufferedReader;
import java.io.FileReader;

import org.testng.Assert;

import com.jayway.restassured.path.json.JsonPath;



public final class OR {
		
	// #################AppSettings#################
	static String jsondata;
	
	public static String getProperty(String key) {

			// load a properties file
			String test="";
			if(jsondata==null || jsondata=="")
			{
				System.out.println("Initializing");
				jsondata = getJSONModel(System.getProperty("user.dir")
						+ "\\src\\test\\resources\\com\\atf\\properties\\OR.json");
			}
			try{
				test = JsonPath.with(jsondata).get(key);
			}
			catch(Exception e){
				Assert.fail(key+" doesn't exist in appSettings.json");
			}

		return test;
	}	
	
	private static String getJSONModel(String filePath) {
		try {

			BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
			StringBuilder srtingBilder = new StringBuilder();
			String line = bufferedReader.readLine();
			while (line != null) {
				srtingBilder.append(line);
				srtingBilder.append("\n");
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
			return srtingBilder.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
}