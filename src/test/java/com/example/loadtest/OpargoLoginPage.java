package com.example.loadtest;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

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
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners(PerformanceListener.class)
public class OpargoLoginPage{
	
	public WebDriver driver;
	
	  @BeforeMethod
	    public void setUp() {
	        System.setProperty("webdriver.chrome.driver", "C:\\Windows\\System32\\chromedriver.exe");
	        ChromeOptions options = new ChromeOptions();
	        options.addExtensions(new File("C:\\workspace\\BE\\loadtesting\\load-test-project\\mpbjkejclgfgadiemmefgebjfooflfhl-2.0.1-Crx4Chrome.com.crx"));
	        options.addArguments("--start-maximized");
	        System.out.println();
	        driver = new ChromeDriver(options);
	    }
	  
	 	@Test
	     public void loginPage() throws InterruptedException, IOException 
	     {	
	 		 long startTime = System.currentTimeMillis();
	 	JavascriptExecutor js = (JavascriptExecutor) driver;
	 	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(300));
	  
        driver.get("http://127.0.0.1:90/#/login");
        ExcelUtils excel = new ExcelUtils("C:\\workspace\\BE\\loadtesting\\load-test-project\\src\\resources\\test-data\\input-data.xlsx");
        Map<String, String> data = excel.getRowData("Sheet1",1);
        System.out.println("Page Title is " + driver.getTitle());
        Assert.assertEquals("Opargo", driver.getTitle());
        WebElement usernameField = driver.findElement(By.name("username"));
        usernameField.sendKeys("yp_co_admin");

        WebElement passwordField = driver.findElement(By.name("password"));

        passwordField.sendKeys("YPRssp78@");
//        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src, 'recaptcha')]")));
//        WebElement recaptchaCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.className("recaptcha-checkbox-checkmark")));
//        js.executeScript("arguments[0].click();", recaptchaCheckbox);
//        
        js.executeScript("document.querySelector('.mb5.primary-btn').click();");
//        Thread.sleep(2000);/
        
       
        System.out.println("User Logged inn successfully");
        
        WebElement lastNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("patientLastName")));
        
        Thread.sleep(10000);

        lastNameField.clear();

        lastNameField.sendKeys("kumar");
        WebElement firstNameField = driver.findElement(By.name("patientFirstName"));
        firstNameField.sendKeys("sumith");
//        WebElement middleNameField = driver.findElement(By.name("patientMiddleName"));
//        middleNameField.sendKeys("rao");
        WebElement birthMonthField = driver.findElement(By.name("patientBirthMonth"));
        birthMonthField.sendKeys("11");
        WebElement birthDayField = driver.findElement(By.name("patientBirthDay"));
        birthDayField.sendKeys("29");
        WebElement birthYearField = driver.findElement(By.name("patientBirthYear"));
        birthYearField.sendKeys("1994");
        WebElement patientLookupButton = driver.findElement(By.cssSelector("input[type='submit'][value='Patient Lookup'].primary-btn"));
        patientLookupButton.click();
        Thread.sleep(10000);
        System.out.println("clicking newpatient button");
        WebElement newPatientButton = driver.findElement(By.cssSelector("button.btn.center-block.primary-btn.mb10"));
        newPatientButton.click();
        Thread.sleep(500);
        
        System.out.println("Entered New User Name and DOB successfully...");
        
        WebElement genderDropdown = driver.findElement(By.name("patientGender"));
        js.executeScript("arguments[0].scrollIntoView(true);", genderDropdown);
        js.executeScript("arguments[0].value = 'M';", genderDropdown);
        System.out.println("Scrolled to additional details");
        
        WebElement option = genderDropdown.findElement(By.xpath("//option[text()=' M']"));
        js.executeScript("arguments[0].selected = true;", option);
        js.executeScript("arguments[0].dispatchEvent(new Event('change'));", genderDropdown);
        System.out.println("Selected value: " + genderDropdown.getAttribute("value"));
        
        WebElement raceDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("patientRace")));
        js.executeScript("arguments[0].value = 'Asian';", raceDropdown);
        js.executeScript("arguments[0].dispatchEvent(new Event('change'));", raceDropdown);

        WebElement ethnicityDropdown = driver.findElement(By.name("patientEthnicity"));
        js.executeScript("arguments[0].value = 'Hispanic or Latino';", ethnicityDropdown);
        js.executeScript("arguments[0].dispatchEvent(new Event('change'));", ethnicityDropdown);
        
        WebElement addressInput = driver.findElement(By.name("patientAddress1"));
        addressInput.sendKeys("123 Main Street");
        
        WebElement addressField2 = driver.findElement(By.name("patientAddress2"));
        addressField2.sendKeys("Peddamberpet");
        
        WebElement cityField = driver.findElement(By.name("patientCity"));
        cityField.sendKeys("Hyderabad");
        
        WebElement stateDropdown = driver.findElement(By.name("patientState"));
        Thread.sleep(50);
        js.executeScript("arguments[0].value = 'CA';", stateDropdown);
        js.executeScript("arguments[0].dispatchEvent(new Event('change'));", stateDropdown);
        
        WebElement zipCodeField = driver.findElement(By.name("patientZip"));
        zipCodeField.sendKeys("12345");
        
        WebElement phoneField = driver.findElement(By.name("patientPrimaryPhone"));
        phoneField.sendKeys("1234567890");
        
        WebElement secondaryPhoneField = driver.findElement(By.name("patientSecondaryPhone"));
        secondaryPhoneField.sendKeys("9876543210");
        
        WebElement emailField = driver.findElement(By.name("patientEmail"));
        emailField.sendKeys("example@example.com");
        
        WebElement commentsTextarea = driver.findElement(By.name("patientComments"));
        commentsTextarea.sendKeys("This is a sample comment for testing purposes.");

        Thread.sleep(200);
        System.out.println("Entered additional details successfully...");

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
        
        Thread.sleep(200);
        
        WebElement insuranceIdInput = driver.findElement(By.name("insuranceID"));
        insuranceIdInput.sendKeys("123456789"); // Replace "1234567890" with the desired insurance ID
        
        WebElement groupNumberInput = driver.findElement(By.name("insuranceGroupNumber"));
        groupNumberInput.sendKeys("123456"); // Replace "123456" with the desired group number
        
        Thread.sleep(100);
        
        System.out.println("Waiting for the first date picker toggle button...");
        WebElement dateInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//button[@aria-label='Open calendar'])[1]")));
        System.out.println("First date picker toggle button found.");
        js.executeScript("arguments[0].click();", dateInput);

        // Wait for and click the next month button
        System.out.println("Waiting for the next month button...");
        WebElement nextMonthButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("mat-calendar-next-button")));
        System.out.println("Next month button found.");
        js.executeScript("arguments[0].click();", nextMonthButton);

        // Wait for and click the specific date button
        System.out.println("Waiting for the specific date button (June 20, 2024)...");
        WebElement dateButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='June 20, 2024']")));
        System.out.println("Specific date button found.");
        js.executeScript("arguments[0].click();", dateButton);
        
        Thread.sleep(100);
   
        //Thread.sleep(2000);
        WebElement insurancePhoneInput = driver.findElement(By.name("insurancePhone"));
        insurancePhoneInput.clear();
        insurancePhoneInput.sendKeys("1234567890");
        Thread.sleep(50);
        
        //SELECTING SECONDARY INSURANCE PROVIDER
        WebElement seconddropdownInput = driver.findElement(By.name("secondaryInsuranceProvider"));
        js.executeScript("arguments[0].click();", seconddropdownInput);
        Thread.sleep(300);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='dropdown-menu show']")));
        WebElement secondoptionToSelect = driver.findElement(By.xpath("//li[@class='ng-star-inserted']/a[text()='Aetna Us Healthcare']"));
        js.executeScript("arguments[0].click();", secondoptionToSelect);

        
        WebElement insuranceIdField = driver.findElement(By.name("secondaryInsuranceID"));
        insuranceIdField.sendKeys("ABC12345");
        WebElement groupNumberField = driver.findElement(By.name("secondaryInsuranceGroupNumber"));
        groupNumberField.sendKeys("GRP56789");
        Thread.sleep(50);
        
        // Locate and click the second date picker toggle button
        System.out.println("Waiting for the second date picker toggle button...");
        WebElement seconddatePickerToggle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//button[@aria-label='Open calendar'])[2]")));
        System.out.println("Second date picker toggle button found.");
        js.executeScript("arguments[0].click();", seconddatePickerToggle);

        // Wait for and click the next month button for the second date picker
        System.out.println("Waiting for the next month button for the second date picker...");
        WebElement nextMonthButton1 = wait.until(ExpectedConditions.elementToBeClickable(By.className("mat-calendar-next-button")));
        System.out.println("Next month button for the second date picker found.");
        js.executeScript("arguments[0].click();", nextMonthButton1);

        // Wait for and click the specific date button for the second date picker
        System.out.println("Waiting for the specific date button (June 23, 2024)...");
        WebElement dateButton1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='June 23, 2024']")));
        System.out.println("Specific date button for the second date picker found.");
        js.executeScript("arguments[0].click();", dateButton1);
        
        
        WebElement phoneField1 = driver.findElement(By.xpath("//input[@name='secondaryInsurancePhone']"));
        phoneField1.sendKeys("1234567890");
        System.out.println("Successfully entered secondary phone");
        
        Thread.sleep(1000);
        WebElement dropdownElement = driver.findElement(By.name("preferredProvider"));
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue("2955");
        Thread.sleep(3000);
        
//		  Locate the input field and click to activate the autocomplete
//        System.out.println("Waiting for the input field...");
//        WebElement inputField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("mat-input-2")));
//        System.out.println("Input field found.");
//        inputField.sendKeys("ADAMS, DAVID");

        WebElement inputElement = driver.findElement(By.id("mat-input-2"));
        inputElement.click();

        // Wait for the autocomplete panel to be visible
        WebElement autocompletePanel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("mat-autocomplete-panel")));

        // Initialize JavaScript Executor

        // Variables to store options and their count
        List<WebElement> options;
        int previousSize = 0;

        // Continuously scroll in small steps and check for new options
        while (true) {
            // Scroll down the panel incrementally
            js.executeScript("arguments[0].scrollTop += arguments[0].offsetHeight / 2;", autocompletePanel);

            // Wait for new options to load (adjust the wait time as needed)
            Thread.sleep(500);  // Reduce wait time for faster loading checks

            // Retrieve the updated list of options
            options = driver.findElements(By.cssSelector(".mat-option-text"));

            // Check if the size of the options list has changed
            if (options.size() == previousSize) {
                // If the size hasn't changed, assume all options are loaded
                break;
            }

            // Update the previous size
            previousSize = options.size();
        }

        System.out.println("Total options: " + options.size());

        // Select a random option
        Random rand = new Random();
        int randomIndex = rand.nextInt(options.size()); // Generate a random index within the available options
        WebElement randomOption = options.get(randomIndex);

        // Scroll the random option into view if necessary and click it using JavaScript
        js.executeScript("arguments[0].scrollIntoView(true);", randomOption);
        wait.until(ExpectedConditions.elementToBeClickable(randomOption));
        js.executeScript("arguments[0].click();", randomOption);


        // Optionally, perform further actions after selection

        
        WebElement referral_source = driver.findElement(By.name("referralSource"));
        Select referralSource_dropdown = new Select(referral_source);
        referralSource_dropdown.selectByVisibleText("Billboard");


        // Verify the selected value
//        String selectedValue = (String) js.executeScript("return arguments[0].value;", selectElement);
//        System.out.println("Selected value: " + selectedValue);
        
//        WebElement AppoinmentypeselectElement = wait1.until(ExpectedConditions.presenceOfElementLocated(By.name("officevisit")));
//        WebElement Appoinmentypeoption = AppoinmentypeselectElement.findElement(By.xpath("//option[text()=' New Patient ']"));
//        js.executeScript("arguments[0].value = 1: Object;", AppoinmentypeselectElement);
//        js.executeScript("arguments[0].dispatchEvent(new Event('change'));", AppoinmentypeselectElement);
        WebElement officevisit_dropdownElement = driver.findElement(By.name("officevisit"));
        Select officevisit_dropdown = new Select(officevisit_dropdownElement);
        officevisit_dropdown.selectByVisibleText("Acute Visit");

        
     // Locate and click the second date picker toggle button
        System.out.println("Waiting for the second date picker toggle button...");
        WebElement thirddatePickerToggle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//button[@aria-label='Open calendar'])[3]")));
        System.out.println("Second date picker toggle button found.");
        js.executeScript("arguments[0].click();", thirddatePickerToggle);

//        // Wait for and click the next month button for the second date picker
//        System.out.println("Waiting for the next month button for the second date picker...");
//        WebElement thirdMonthButton1 = wait.until(ExpectedConditions.elementToBeClickable(By.className("mat-calendar-next-button")));
//        System.out.println("Next month button for the second date picker found.");
//        js.executeScript("arguments[0].click();", thirdMonthButton1);

        // Wait for and click the specific date button for the second date picker
        System.out.println("Waiting for the specific date button (June 28, 2024)...");
        WebElement thirddateButton1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='May 1, 2024']")));
        System.out.println("Specific date button for the second date picker found.");
        js.executeScript("arguments[0].click();", thirddateButton1);
        
        WebElement patientbody_part = driver.findElement(By.name("patientBodyPart"));
        Select patientBodypartdropdown = new Select(patientbody_part);
        patientBodypartdropdown.selectByVisibleText("Knee");
        
        WebElement patientLocationOpt_dropdownElement = driver.findElement(By.name("patientLocationOpt"));
        Select patientLocationOpt_dropdown = new Select(patientLocationOpt_dropdownElement);
        patientLocationOpt_dropdown.selectByVisibleText("Left");
        
        WebElement textareaElement = driver.findElement(By.name("notes"));
        textareaElement.sendKeys("This is a test note.");
        
        WebElement buttonElement = driver.findElement(By.xpath("//input[@type='button' and @value='Find First Available']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttonElement);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("Execution time: " + executionTime + " milliseconds");
//        Thread.sleep(150000);
        try {
            // Wait until the buttons are visible and clickable
        	List<WebElement> scheduleButtons = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
        		    By.cssSelector("input[type='submit'][value='Schedule'].btn.primary-btn-sm.primary-btn.dmWarning.ng-star-inserted")
        		));

            System.out.println("Specific schedule button for the scheduling the appointment."+scheduleButtons);
            // Click the first button in the list
            if (!scheduleButtons.isEmpty()) {
                scheduleButtons.get(0).click();
                System.out.println("Clicked the first 'Schedule' button successfully.");
            } else {
                throw new NoSuchElementException("No 'Schedule' buttons found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
// driver.quit();
        }
        
        
   
	     }
	 		
		 	
	 	  @AfterMethod
	 	    public void tearDown() throws InterruptedException {
	 		 Thread.sleep(2000);
	 		  if (driver != null) {
//	 	            driver.quit();
	 	        }
	 	    }
}
