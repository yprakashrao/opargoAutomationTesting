package com.example.loadtest;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.sikuli.script.Screen;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.openqa.selenium.Point;

@Listeners(PerformanceListener.class)
public class ExistingPatient {
    
	private static final Logger logger = LoggerFactory.getLogger(ExistingPatient.class);
    public WebDriver driver;
    
    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Windows\\System32\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        logger.info("ChromeDriver initialized successfully The Existing Patient Application Flow.");
    }
    
    @Test
    public void loginPage() throws InterruptedException, IOException, FindFailed, AWTException {
    	
        long startTime = System.currentTimeMillis();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(300));

        driver.get("http://127.0.0.1:90/#/login");
        ExcelUtils excel = new ExcelUtils("C:\\workspace\\BE\\opargoAutomationTesting\\src\\resources\\test-data\\input-data.xlsx");
        Map<String, String> data = excel.getRowData("Sheet1", 2);
        logger.info("Page Title is {}", driver.getTitle());
        Assert.assertEquals("Opargo", driver.getTitle());

        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        usernameField.sendKeys("sireesha");
        logger.debug("Username entered.");

        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        passwordField.sendKeys("DocUser$444");
        logger.debug("Password entered.");
        
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".mb5.primary-btn")));
        loginButton.click();
        logger.info("User logged in successfully.");

        WebElement lastNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("patientLastName")));
        lastNameField.clear();
        lastNameField.sendKeys("kumar");
        logger.debug("Patient last name entered.");

        Thread.sleep(10000);
        WebElement patientLookupButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[type='submit'][value='Patient Lookup'].primary-btn")));
        patientLookupButton.click();
        logger.info("Clicked patient lookup button.");        
        
        WebElement selectButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[contains(., 'Kkk Kumar')]//button[text()='Select']")));
        selectButton.click();
        logger.info("Clicked select button for patient Kkk Kumar.");

//        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        js.executeScript("scroll(0, 350)");
//        WebElement dropdownElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='preferredProvider']")));
         wait.until(ExpectedConditions.presenceOfElementLocated(By.name("preferredProvider")));
         wait.until(ExpectedConditions.elementToBeClickable(By.name("preferredProvider")));
        WebElement dropdownElement = driver.findElement(By.name("preferredProvider"));
        Thread.sleep(1000);
        Select dropdown = new Select(dropdownElement);
        Thread.sleep(1000);
//        dropdown.selectByVisibleText("Cardiologist, Thomas");
        dropdown.selectByValue("2915");
        logger.debug("Preferred provider selected.");

        WebElement referral_source = driver.findElement(By.name("referralSource"));
        Select referralSource_dropdown = new Select(referral_source);
        referralSource_dropdown.selectByVisibleText("Billboard");
        logger.debug("Referral source selected.");

        WebElement officevisit_dropdownElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("select[name='officevisit']")));
        Select officevisit_dropdown = new Select(officevisit_dropdownElement);
        Thread.sleep(1000);
        officevisit_dropdown.selectByIndex(4);
        Thread.sleep(1000);
        logger.debug("Office visit time selected.");

        WebElement thirddatePickerToggle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//button[@aria-label='Open calendar'])")));
        js.executeScript("arguments[0].click();", thirddatePickerToggle);
        logger.debug("Opened date picker.");

        WebElement thirddateButton1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='May 1, 2024']")));
        js.executeScript("arguments[0].click();", thirddateButton1);
        logger.debug("Selected date May 1, 2024.");

        WebElement patientbody_part = driver.findElement(By.name("patientBodyPart"));
        Select patientBodypartdropdown = new Select(patientbody_part);
        patientBodypartdropdown.selectByVisibleText("Knee");
        logger.debug("Patient body part selected.");

        WebElement patientLocationOpt_dropdownElement = driver.findElement(By.name("patientLocationOpt"));
        Select patientLocationOpt_dropdown = new Select(patientLocationOpt_dropdownElement);
        patientLocationOpt_dropdown.selectByVisibleText("Left");
        logger.debug("Patient location option selected.");

        WebElement textareaElement = driver.findElement(By.name("notes"));
        textareaElement.sendKeys(data.get("notes"));
        logger.debug("Notes entered.");

        Thread.sleep(3000); 
        
        WebElement findFirstAvailableButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("input[type='button'][value='Find First Available'].primary-btn"))
            );
        System.out.println(findFirstAvailableButton);
        Thread.sleep(2000);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", findFirstAvailableButton);
        Thread.sleep(2000);
        findFirstAvailableButton.click();
        logger.debug("First click of findFirstAvailableButton completed");
        Thread.sleep(8000);
        findFirstAvailableButton.click();
        logger.debug("Second click of findFirstAvailableButton completed");
        

        Thread.sleep(60000); 
        logger.info("Clicked 'Find First Available' button.");
       
        try {
        String xpathExpression = "//div[contains(@class, 'row')]";
        List<WebElement> elementsWithText = driver.findElements(
                By.xpath(xpathExpression)
            );
        
        js.executeScript("window.scrollTo(0, 0);");
        System.out.println("Found the elements to be clicked and scrolled down"+elementsWithText.size());
        for (WebElement elementWithText : elementsWithText) {
            try {
                // Scroll the specific element into view
                js.executeScript("arguments[0].scrollIntoView(true);", elementWithText);
                Thread.sleep(1000);  // Optional wait to ensure smooth scroll

                // Find the "Schedule" button within the current element
                WebElement scheduleButton = elementWithText.findElement(By.cssSelector("div.col-md-2.text-center input.primary-btn-sm.primary-btn.dmWarning"));
                js.executeScript("window.scrollTo(0, 0);");
                System.out.println("Waiting for Schedule button to be clicked...");
                System.out.println(elementWithText);
                System.out.println(scheduleButton);

                // Click the "Schedule" button
                scheduleButton.click();
                System.out.println("Schedule button clicked");
                
                // Break the loop if you only want to click the first button found
                break;
            } catch (Exception e) {
                System.out.println("Failed to find or click the Schedule button in one of the elements. Continuing to the next element...");
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
    	logger.debug("clicked on the scheduled button completed");
    }
        
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        logger.info("Execution time: {} milliseconds", executionTime);
    }
    
    
    @AfterMethod
    public void tearDown() throws InterruptedException {
        Thread.sleep(2000);
        if (driver != null) {
        	//driver.quit();
            logger.info("Browser closed and driver quit.");
        }
    }
    
}
