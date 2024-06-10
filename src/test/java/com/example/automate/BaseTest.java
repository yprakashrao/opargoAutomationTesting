package com.example.automate;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
	
	private static final Logger logger = LoggerFactory.getLogger(NewPatientLogin.class);
	public WebDriver driver;
	
	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "C:\\Windows\\System32\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addExtensions(new File(
				"C:\\workspace\\BE\\opargoAutomationTesting\\mpbjkejclgfgadiemmefgebjfooflfhl-2.0.1-Crx4Chrome.com.crx"));
		options.addArguments("--start-maximized");
		System.out.println();
		driver = new ChromeDriver(options);
	}
	@AfterMethod
	public void tearDown() throws InterruptedException {
		Thread.sleep(2000);
		if (driver != null) {
			// driver.quit();
		}
		logger.info("Closed the browser." + "" + "");
	}
}
