package com.example.automate;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.openqa.selenium.interactions.Actions;
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
public class NewPatientLogin{
	
	private static final Logger logger = LoggerFactory.getLogger(NewPatientLogin.class);

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
	  
        ExcelUtils excel = new ExcelUtils("C:\\workspace\\BE\\opargoAutomationTesting\\src\\resources\\test-data\\input-data.xlsx");
        Map<String, String> data = excel.getRowData("Sheet1",2);
        logger.info("Excel data loaded successfully.");
        
        logger.info("Navigating to login page.");
        driver.get("http://127.0.0.1:90/#/login");

        // 2. Opargo title and logo on the browser tab
        Assert.assertEquals(driver.getTitle(), "Opargo");
        logger.info("Page title verified: Opargo");

        // 3. Valid User Login
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.cssSelector(".mb5.primary-btn"));

        usernameField.sendKeys("validUsername");
        passwordField.sendKeys("validPassword");
        loginButton.click();
        logger.info("Valid login attempted.");
        Thread.sleep(10000);
        Assert.assertEquals(driver.getCurrentUrl(), "http://127.0.0.1:90/#/home", "Error: Login was not successful, URL did not change to the home page.");
        
        // 4. Invalid Username
        usernameField.clear();
        passwordField.clear();
        usernameField.sendKeys("invalidUsername");
        passwordField.sendKeys("validPassword");
        loginButton.click();
        logger.info("Invalid username login attempted.");
        Assert.assertTrue(driver.findElement(By.id("errorMessageId")).isDisplayed(), "Error: No error message displayed for invalid username.");
        // Add assertions to verify error message

        // 5. Invalid Password
        usernameField.clear();
        passwordField.clear();
        usernameField.sendKeys("validUsername");
        passwordField.sendKeys("invalidPassword");
        loginButton.click();
        logger.info("Invalid password login attempted.");
        Assert.assertTrue(driver.findElement(By.id("errorMessageId")).isDisplayed(), "Error: No error message displayed for invalid password.");
        // Add assertions to verify error message

        // 6. Empty Username and Password
        usernameField.clear();
        passwordField.clear();
        loginButton.click();
        logger.info("Empty username and password login attempted.");
        // Add assertions to verify error message

        // 7. Empty Username
        usernameField.clear();
        passwordField.sendKeys("validPassword");
        loginButton.click();
        logger.info("Empty username login attempted.");
        // Add assertions to verify error message

        // 8. Empty Password
        usernameField.sendKeys("validUsername");
        passwordField.clear();
        loginButton.click();
        logger.info("Empty password login attempted.");
        // Add assertions to verify error message

        // 9. Password Visibility
        WebElement passwordVisibilityToggle = driver.findElement(By.cssSelector(".password-visibility-toggle"));
        passwordVisibilityToggle.click();
        logger.info("Password visibility toggled.");
        // Add assertions to verify password visibility

        // 10. Forgot Password Link
        WebElement forgotPasswordLink = driver.findElement(By.linkText("Forgot Password"));
        Assert.assertTrue(forgotPasswordLink.isDisplayed());
        logger.info("Forgot password link is displayed.");

        // 11. Forgot Username Link
        WebElement forgotUsernameLink = driver.findElement(By.linkText("Forgot Username"));
        Assert.assertTrue(forgotUsernameLink.isDisplayed());
        logger.info("Forgot username link is displayed.");

        // 12. Recaptcha working or not
        WebElement recaptcha = driver.findElement(By.className("g-recaptcha"));
        Assert.assertTrue(recaptcha.isDisplayed());
        logger.info("Recaptcha is displayed.");

        // 13. Take a Tour Link
        WebElement takeTourLink = driver.findElement(By.linkText("Take a Tour"));
        Assert.assertTrue(takeTourLink.isDisplayed());
        logger.info("Take a tour link is displayed.");

        // 14. Speak with a Representative Link
        WebElement speakWithRepLink = driver.findElement(By.linkText("Speak with a Representative"));
        Assert.assertTrue(speakWithRepLink.isDisplayed());
        logger.info("Speak with a representative link is displayed.");

        // 15. New User? Heading
        WebElement newUserHeading = driver.findElement(By.cssSelector(".new-user-heading"));
        Assert.assertTrue(newUserHeading.isDisplayed());
        logger.info("New user heading is displayed.");

        // 16. Information for New User
        WebElement newUserInfo = driver.findElement(By.cssSelector(".new-user-info"));
        Assert.assertTrue(newUserInfo.isDisplayed());
        logger.info("Information for new user is displayed.");

        // 17. Footer in the black color
        WebElement footer = driver.findElement(By.cssSelector(".footer"));
        String footerColor = footer.getCssValue("background-color");
        Assert.assertEquals(footerColor, "rgba(0, 0, 0, 1)");
        logger.info("Footer color verified: black");

        // 18. Logo under footer
        WebElement footerLogo = driver.findElement(By.cssSelector(".footer-logo"));
        Assert.assertTrue(footerLogo.isDisplayed());
        logger.info("Footer logo is displayed.");

        // 19. License reserved message after logo
        WebElement licenseMessage = driver.findElement(By.cssSelector(".license-message"));
        Assert.assertTrue(licenseMessage.isDisplayed());
        logger.info("License reserved message is displayed.");

        // 20. Privacy and security policy under footer
        WebElement privacyPolicyLink = driver.findElement(By.linkText("Privacy and Security Policy"));
        Assert.assertTrue(privacyPolicyLink.isDisplayed());
        logger.info("Privacy and security policy link is displayed.");

        // 21. Check the placeholders with their label and required field marks
        WebElement usernameLabel = driver.findElement(By.cssSelector("label[for='username']"));
        WebElement passwordLabel = driver.findElement(By.cssSelector("label[for='password']"));
        Assert.assertTrue(usernameLabel.isDisplayed());
        Assert.assertTrue(passwordLabel.isDisplayed());
        logger.info("Username and password labels are displayed.");
        Assert.assertTrue(usernameField.getAttribute("placeholder").contains("Username"));
        Assert.assertTrue(passwordField.getAttribute("placeholder").contains("Password"));
        logger.info("Username and password placeholders are correct.");

        // 22. Check "Login" button functionality
        // This is already covered in valid and invalid login cases

        // 23. Check "Login" button color
        String loginButtonColor = loginButton.getCssValue("background-color");
        Assert.assertEquals(loginButtonColor, "expected-color-value");
        logger.info("Login button color verified.");

        // 24. Check "Login" button color on hover
        // Simulate hover and check color
        Actions actions = new Actions(driver);
        actions.moveToElement(loginButton).perform();
        String loginButtonHoverColor = loginButton.getCssValue("background-color");
        Assert.assertEquals(loginButtonHoverColor, "expected-hover-color-value");
        logger.info("Login button hover color verified.");
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
        Thread.sleep(5000);
        

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
        officevisit_dropdown.selectByIndex(2);
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

        
        Thread.sleep(60000);
        
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
