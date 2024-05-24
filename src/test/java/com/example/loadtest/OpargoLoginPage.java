package com.example.loadtest;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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


@Listeners(PerformanceListener.class)
public class OpargoLoginPage{
	
	private static final Logger logger = LoggerFactory.getLogger(OpargoLoginPage.class);

	public WebDriver driver;
	
	  @BeforeMethod
	    public void setUp() {
	        System.setProperty("webdriver.chrome.driver", "C:\\Windows\\System32\\chromedriver.exe");
	        ChromeOptions options = new ChromeOptions();
//	        options.addExtensions(new File("C:\\workspace\\BE\\loadtesting\\load-test-project\\mpbjkejclgfgadiemmefgebjfooflfhl-2.0.1-Crx4Chrome.com.crx"));
	        options.addArguments("--start-maximized");
	        System.out.println();
	        driver = new ChromeDriver(options);
	    }
	  
	 	@Test
	     public void loginPage() throws InterruptedException, IOException 
	     {	
	 	long startTime = System.currentTimeMillis();
	 	JavascriptExecutor js = (JavascriptExecutor) driver;
	 	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(200));
	  
	 	logger.info("Navigating to login page.");
        driver.get("http://127.0.0.1:90/#/login");
        
        ExcelUtils excel = new ExcelUtils("C:\\workspace\\BE\\opargoAutomationTesting\\src\\resources\\test-data\\input-data.xlsx");
        Map<String, String> data = excel.getRowData("Sheet1",2);
        logger.info("Excel data loaded successfully.");
        
        System.out.println("Page Title is " + driver.getTitle());
        logger.info("Page Title is " + driver.getTitle());
        Assert.assertEquals("Opargo", driver.getTitle());
        logger.info("Page title verified.");
        WebElement usernameField = driver.findElement(By.name("username"));
        usernameField.sendKeys("sireesha");
        logger.info("Entered username.");
        
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("DocUser$444");
        logger.info("Entered password.");

        js.executeScript("document.querySelector('.mb5.primary-btn').click();");
        logger.info("Clicked login button.");
        
       
        System.out.println("User Logged inn successfully");
        
        WebElement lastNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("patientLastName")));
        Thread.sleep(10000);
        logger.info("User logged in successfully.");
        
        lastNameField.clear();
        lastNameField.sendKeys(data.get("patientLastName"));
        logger.info("Entered patient last name.");
        
        WebElement firstNameField = driver.findElement(By.name("patientFirstName"));
        firstNameField.sendKeys(data.get("patientFirstName"));
        logger.info("Entered patient first name.");
        
        WebElement middleNameField = driver.findElement(By.name("patientMiddleName"));
        middleNameField.sendKeys(data.get("patientMiddleName"));
        logger.info("Entered patient middle name.");
        
        WebElement birthMonthField = driver.findElement(By.name("patientBirthMonth"));
        birthMonthField.sendKeys(data.get("patientBirthMonth"));
        logger.info("Entered patient birth month.");
        
        WebElement birthDayField = driver.findElement(By.name("patientBirthDay"));
        birthDayField.sendKeys(data.get("patientBirthDay"));
        logger.info("Entered patient birth day.");
        
        WebElement birthYearField = driver.findElement(By.name("patientBirthYear"));
        birthYearField.sendKeys(data.get("patientBirthYear"));
        logger.info("Entered patient birth year.");
        
        WebElement patientLookupButton = driver.findElement(By.cssSelector("input[type='submit'][value='Patient Lookup'].primary-btn"));
        patientLookupButton.click();
        Thread.sleep(10000);
        logger.info("Clicked patient lookup button.");
        
        System.out.println("clicking newpatient button");
        WebElement newPatientButton = driver.findElement(By.cssSelector("button.btn.center-block.primary-btn.mb10"));
        newPatientButton.click();
        Thread.sleep(500);
        logger.info("Clicked new patient button.");
        
        
        System.out.println("Entered New User Name and DOB successfully...");
        
        WebElement genderDropdown = driver.findElement(By.name("patientGender"));
        js.executeScript("arguments[0].scrollIntoView(true);", genderDropdown);
        js.executeScript("arguments[0].value = 'M';", genderDropdown);
        System.out.println("Scrolled to additional details");
        logger.info("Selected gender.");
        
        WebElement option = genderDropdown.findElement(By.xpath("//option[text()=' M']"));
        js.executeScript("arguments[0].selected = true;", option);
        js.executeScript("arguments[0].dispatchEvent(new Event('change'));", genderDropdown);
        System.out.println("Selected value: " + genderDropdown.getAttribute("value"));
        logger.info("Set gender dropdown value.");
        
        WebElement raceDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("patientRace")));
        js.executeScript("arguments[0].value = 'Asian';", raceDropdown);
        js.executeScript("arguments[0].dispatchEvent(new Event('change'));", raceDropdown);
        logger.info("Selected race.");

        WebElement ethnicityDropdown = driver.findElement(By.name("patientEthnicity"));
        js.executeScript("arguments[0].value = 'Hispanic or Latino';", ethnicityDropdown);
        js.executeScript("arguments[0].dispatchEvent(new Event('change'));", ethnicityDropdown);
        logger.info("Selected ethnicity.");
        
        WebElement addressInput = driver.findElement(By.name("patientAddress1"));
        addressInput.sendKeys(data.get("patientAddress1"));
        logger.info("Entered address line 1.");
        
        WebElement addressField2 = driver.findElement(By.name("patientAddress2"));
        addressField2.sendKeys(data.get("patientAddress2"));
        logger.info("Entered address line 2.");
        
        WebElement cityField = driver.findElement(By.name("patientCity"));
        cityField.sendKeys(data.get("patientCity"));
        logger.info("Entered city.");
        
        WebElement stateDropdown = driver.findElement(By.name("patientState"));
        Thread.sleep(50);
        js.executeScript("arguments[0].value = 'CA';", stateDropdown);
        js.executeScript("arguments[0].dispatchEvent(new Event('change'));", stateDropdown);
        logger.info("Selected state.");
        
        WebElement zipCodeField = driver.findElement(By.name("patientZip"));
        zipCodeField.sendKeys(data.get("patientZip"));
        logger.info("Entered zip code.");
        
        WebElement phoneField = driver.findElement(By.name("patientPrimaryPhone"));
        phoneField.sendKeys(data.get("patientPrimaryPhone"));
        logger.info("Entered primary phone.");
        
        WebElement secondaryPhoneField = driver.findElement(By.name("patientSecondaryPhone"));
        secondaryPhoneField.sendKeys(data.get("patientSecondaryPhone"));
        logger.info("Entered secondary phone.");
        
        WebElement emailField = driver.findElement(By.name("patientEmail"));
        emailField.sendKeys(data.get("patientEmail"));
        logger.info("Entered email.");
        
        WebElement commentsTextarea = driver.findElement(By.name("patientComments"));
        commentsTextarea.sendKeys(data.get("patientComments"));
        logger.info("Entered comments.");

        Thread.sleep(200);
        System.out.println("Entered additional details successfully...");
        logger.info("Entered additional details successfully.");

        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,2400)");
        Thread.sleep(100);
        
        
        //SELECTING PREFERENCE PROVIDER
        WebElement dropdownInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("insuranceProvider")));
        js.executeScript("arguments[0].click();", dropdownInput);
        Thread.sleep(300);
        WebElement dropdownMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='dropdown-menu show']")));
        WebElement optionToSelect = dropdownMenu.findElement(By.xpath(".//a[text()='A4 Healthsystems']"));
        js.executeScript("arguments[0].click();", optionToSelect);
        System.out.println("Selected prefered provider succesfully");
        logger.info("Selected preferred insurance provider.");
        
        Thread.sleep(200);
        
        WebElement insuranceIdInput = driver.findElement(By.name("insuranceID"));
        insuranceIdInput.sendKeys(data.get("insuranceID")); // Replace "1234567890" with the desired insurance ID
        logger.info("Entered insurance ID.");
        
        WebElement groupNumberInput = driver.findElement(By.name("insuranceGroupNumber"));
        groupNumberInput.sendKeys(data.get("insuranceGroupNumber")); // Replace "123456" with the desired group number
        logger.info("Entered insurance group number.");
        
        Thread.sleep(100);
        
        System.out.println("Waiting for the first date picker toggle button...");
        WebElement dateInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//button[@aria-label='Open calendar'])[1]")));
        System.out.println("First date picker toggle button found.");
        js.executeScript("arguments[0].click();", dateInput);
        logger.info("Opened first date picker.");

        // Wait for and click the next month button
        System.out.println("Waiting for the next month button...");
        WebElement nextMonthButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("mat-calendar-next-button")));
        System.out.println("Next month button found.");
        js.executeScript("arguments[0].click();", nextMonthButton);
        logger.info("Clicked next month button on first date picker.");
        
        // Wait for and click the specific date button
        System.out.println("Waiting for the specific date button (June 20, 2024)...");
        WebElement dateButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='June 20, 2024']")));
        System.out.println("Specific date button found.");
        js.executeScript("arguments[0].click();", dateButton);
        logger.info("Selected date on first date picker.");
        
        Thread.sleep(100);
   
        //Thread.sleep(2000);
        WebElement insurancePhoneInput = driver.findElement(By.name("insurancePhone"));
        insurancePhoneInput.clear();
        insurancePhoneInput.sendKeys(data.get("insurancePhone"));
        Thread.sleep(50);
        logger.info("Entered insurance phone.");
        
        //SELECTING SECONDARY INSURANCE PROVIDER
        WebElement seconddropdownInput = driver.findElement(By.name("secondaryInsuranceProvider"));
        js.executeScript("arguments[0].click();", seconddropdownInput);
        Thread.sleep(300);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='dropdown-menu show']")));
        WebElement secondoptionToSelect = driver.findElement(By.xpath("//li[@class='ng-star-inserted']/a[text()='Aetna Us Healthcare']"));
        js.executeScript("arguments[0].click();", secondoptionToSelect);
        logger.info("Selected secondary insurance provider.");
        
        
        WebElement insuranceIdField = driver.findElement(By.name("secondaryInsuranceID"));
        insuranceIdField.sendKeys(data.get("secondaryInsuranceID"));
        logger.info("Entered secondary insurance ID.");

        WebElement groupNumberField = driver.findElement(By.name("secondaryInsuranceGroupNumber"));
        groupNumberField.sendKeys(data.get("secondaryInsuranceGroupNumber"));
        Thread.sleep(50);
        logger.info("Entered secondary insurance group number.");

        
        // Locate and click the second date picker toggle button
        System.out.println("Waiting for the second date picker toggle button...");
        WebElement seconddatePickerToggle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//button[@aria-label='Open calendar'])[2]")));
        System.out.println("Second date picker toggle button found.");
        js.executeScript("arguments[0].click();", seconddatePickerToggle);
        js.executeScript("arguments[0].click();", seconddatePickerToggle);


        // Wait for and click the next month button for the second date picker
        System.out.println("Waiting for the next month button for the second date picker...");
        WebElement nextMonthButton1 = wait.until(ExpectedConditions.elementToBeClickable(By.className("mat-calendar-next-button")));
        System.out.println("Next month button for the second date picker found.");
        js.executeScript("arguments[0].click();", nextMonthButton1);
        logger.info("Next month button for the second date picker found.");


        // Wait for and click the specific date button for the second date picker
        System.out.println("Waiting for the specific date button (June 23, 2024)...");
        WebElement dateButton1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='June 23, 2024']")));
        System.out.println("Specific date button for the second date picker found.");
        js.executeScript("arguments[0].click();", dateButton1);
        logger.info("Specific date button for the second date picker found.");

        
        WebElement phoneField1 = driver.findElement(By.name("secondaryInsurancePhone"));
        phoneField1.sendKeys(data.get("secondaryInsurancePhone"));
        logger.info("Entered secondary insurance phone.");
        System.out.println("Successfully entered secondary phone");
        
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,3000)");
        
        Thread.sleep(1000);
        WebElement dropdownElement = driver.findElement(By.name("preferredProvider"));
        
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue("2915");
        logger.info("Selected preferred provider.");
        Thread.sleep(3000);
        

        WebElement inputElement = driver.findElement(By.id("mat-input-2"));
        inputElement.click();
        logger.info("Clicked input element for provider.");

        WebElement autocompletePanel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("mat-autocomplete-panel")));
        List<WebElement> options;
        int previousSize = 0;
        while (true) {
            js.executeScript("arguments[0].scrollTop += arguments[0].offsetHeight;", autocompletePanel);
            Thread.sleep(500);  // Reduce wait time for faster loading checks
            options = driver.findElements(By.cssSelector(".mat-option-text"));
            if (options.size() == previousSize) {
                break;
            }

            // Update the previous size
            previousSize = options.size();
        }

        System.out.println("Total options: " + options.size());
        logger.info("Total options: " + options.size());

        // Select a random option
        Random rand = new Random();
        int randomIndex = rand.nextInt(options.size()); // Generate a random index within the available options
        WebElement randomOption = options.get(randomIndex);

        // Scroll the random option into view if necessary and click it using JavaScript
        js.executeScript("arguments[0].scrollIntoView(true);", randomOption);
        wait.until(ExpectedConditions.elementToBeClickable(randomOption));
        js.executeScript("arguments[0].click();", randomOption);
        logger.info("Selected a random provider option.");


        // Optionally, perform further actions after selection


        WebElement referral_source = driver.findElement(By.name("referralSource"));
        Select referralSource_dropdown = new Select(referral_source);
        referralSource_dropdown.selectByVisibleText("Billboard");
        logger.info("Selected referral source.");


        WebElement officevisit_dropdownElement = driver.findElement(By.name("officevisit"));
        Select officevisit_dropdown = new Select(officevisit_dropdownElement);
        officevisit_dropdown.selectByVisibleText("Acute Visit");
        logger.info("Selected office visit type.");

        
     // Locate and click the second date picker toggle button
        System.out.println("Waiting for the second date picker toggle button...");
        WebElement thirddatePickerToggle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//button[@aria-label='Open calendar'])[3]")));
        System.out.println("Second date picker toggle button found.");
        logger.info("Second date picker toggle button found.");
        js.executeScript("arguments[0].click();", thirddatePickerToggle);

        System.out.println("Waiting for the specific date button (June 28, 2024)...");
        WebElement thirddateButton1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='May 1, 2024']")));
        System.out.println("Specific date button for the second date picker found.");
        logger.info("Specific date button for the second date picker found.");
        js.executeScript("arguments[0].click();", thirddateButton1);
        
        WebElement patientbody_part = driver.findElement(By.name("patientBodyPart"));
        Select patientBodypartdropdown = new Select(patientbody_part);
        patientBodypartdropdown.selectByVisibleText("Knee");
        logger.info("Selected patient body part.");

        
        WebElement patientLocationOpt_dropdownElement = driver.findElement(By.name("patientLocationOpt"));
        Select patientLocationOpt_dropdown = new Select(patientLocationOpt_dropdownElement);
        patientLocationOpt_dropdown.selectByVisibleText("Left");
        logger.info("Selected patient location option.");

        WebElement textareaElement = driver.findElement(By.name("notes"));
        textareaElement.sendKeys(data.get("notes"));
        logger.info("Entered notes.");

        
        WebElement buttonElement = driver.findElement(By.xpath("//input[@type='button' and @value='Find First Available']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttonElement);
        logger.info("Clicked 'Find First Available' button.");

        
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("Execution time: " + executionTime + " milliseconds");
        logger.info("Execution time: " + executionTime + " milliseconds");

        
        Thread.sleep(30000);
        
        try {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".row.bestfit-slot.mb5.ng-star-inserted")));
            List<WebElement> elements = driver.findElements(By.cssSelector(".row.bestfit-slot.mb5.ng-star-inserted"));
            logger.info("List of available slots found. Number of slots: " + elements.size());
            System.out.println("list of elements."+elements);
            for (WebElement element : elements) {
            	   // Wait for the name element to be visible and get the text content using JavaScript
                WebElement nameElement = wait.until(ExpectedConditions.visibilityOf(element.findElement(By.cssSelector(".col-md-3.marspace"))));
                String name = (String) js.executeScript("return arguments[0].textContent.trim();", nameElement);

                // Wait for the time element to be visible and get the text content using JavaScript
                WebElement timeElement = wait.until(ExpectedConditions.visibilityOf(element.findElement(By.cssSelector(".col-md-2.marspace"))));
                String time = (String) js.executeScript("return arguments[0].textContent.trim();", timeElement);
                logger.info("Available Slot - Name: " + name + ", Time: " + time);

                System.out.println("Name: " + name);
                System.out.println("Time: " + time);
//                System.out.println("Location: " + location);
                WebElement scheduleButton = wait.until(ExpectedConditions.elementToBeClickable(element.findElement(By.cssSelector("input[value='Schedule']"))));
                js.executeScript("arguments[0].click();", scheduleButton);
                logger.info("Clicked schedule button for slot - Name: " + name + ", Time: " + time);
                break; // Exit the loop once the desired element is found and clicked
//                }
            }
        } finally {
        	logger.info("Completed scheduling.");
        }
        
        
   
	     }
	 		
		 	
	 	  @AfterMethod
	 	    public void tearDown() throws InterruptedException {
	 		 Thread.sleep(2000);
	 		  if (driver != null) {
//	 	            driver.quit();
	 	        }
	 		 logger.info("Closed the browser.");
	 	    }
}
