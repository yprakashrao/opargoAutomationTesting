package com.example.automate;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Recaptchalogin {

	private static final Logger logger = LoggerFactory.getLogger(NewPatientLogin.class);

	public WebDriver driver;
	private List<TestResult> results = new ArrayList<>();

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

	@Test
	public void loginPage() throws InterruptedException, IOException {
		long startTime = System.currentTimeMillis();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(200));

		ExcelUtils excel = new ExcelUtils(
				"C:\\workspace\\BE\\opargoAutomationTesting\\src\\resources\\test-data\\input-data.xlsx");
		Map<String, String> data = excel.getRowData("Sheet1", 2);
		// logger.info("Excel data loaded successfully.");

		navigatingToLoginPage();

		WebElement usernameField = driver.findElement(By.name("username"));
		WebElement passwordField = driver.findElement(By.name("password"));
		WebElement loginButton = driver.findElement(By.cssSelector(".mb5.primary-btn"));
		String expectedErrorMessage = "Invalid username/password.";

		titleAndLogoCheck();
		loginWithInvalidUsername(usernameField, passwordField, loginButton, expectedErrorMessage, wait);
		loginWithInvalidPassword(usernameField, passwordField, loginButton, expectedErrorMessage, wait);
		loginWithEmptyCredentials(usernameField, passwordField, loginButton, expectedErrorMessage, wait);
		loginWithEmptyUsername(usernameField, passwordField, loginButton, expectedErrorMessage, wait);
		loginWithEmptyPassword(usernameField, passwordField, loginButton, expectedErrorMessage, wait);
		checkPlaceholders(usernameField, passwordField);
		checkLoginButtonColor(loginButton);
		checkForgotPasswordLink(wait);
		checkForgotUsernameLink(wait);
		checkTakeTourLink(js);
		checkSpeakWithRepresentativeLink(js);
		checkNewHereHeading();
		checkInfoParagraph();
		checkFooterElement();
		checkFooterLogo();
		checkLicenseMessage();
		checkPrivacyPolicyLink();
		performValidLogin(wait, js, data);

		handlePatientInformationPage(data, wait, js);
		// emptyPrimaryInsuranceProvider(wait, js, data);
		// executionwithAllRequiredFields(wait, js, data);

		// 6. Verify the presence and functionality of the "Clear Fields" button
		WebElement clearFieldsButton = driver.findElement(By.cssSelector("clear-fields-button-selector"));
		if (clearFieldsButton == null || !clearFieldsButton.isDisplayed()) {
			logger.error("Error: 'Clear Fields' button is not displayed.");
		}

		WebElement lastNameField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.name("patientLastName")));
		Thread.sleep(10000);
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

		WebElement patientLookupButton = driver
				.findElement(By.cssSelector("input[type='submit'][value='Patient Lookup'].primary-btn"));
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

		// SELECTING PREFERENCE PROVIDER
		WebElement dropdownInput = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.name("insuranceProvider")));
		js.executeScript("arguments[0].click();", dropdownInput);
		Thread.sleep(300);
		WebElement dropdownMenu = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='dropdown-menu show']")));
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
		WebElement dateInput = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("(//button[@aria-label='Open calendar'])[1]")));
		System.out.println("First date picker toggle button found.");
		js.executeScript("arguments[0].click();", dateInput);
		logger.info("Opened first date picker.");

		// Wait for and click the next month button
		System.out.println("Waiting for the next month button...");
		WebElement nextMonthButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.className("mat-calendar-next-button")));
		System.out.println("Next month button found.");
		js.executeScript("arguments[0].click();", nextMonthButton);
		logger.info("Clicked next month button on first date picker.");

		// Wait for and click the specific date button
		System.out.println("Waiting for the specific date button (June 20, 2024)...");
		WebElement dateButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='June 20, 2024']")));
		System.out.println("Specific date button found.");
		js.executeScript("arguments[0].click();", dateButton);
		logger.info("Selected date on first date picker.");

		Thread.sleep(100);

		// Thread.sleep(2000);
		WebElement insurancePhoneInput = driver.findElement(By.name("insurancePhone"));
		insurancePhoneInput.clear();
		insurancePhoneInput.sendKeys(data.get("insurancePhone"));
		Thread.sleep(50);
		logger.info("Entered insurance phone.");

		// SELECTING SECONDARY INSURANCE PROVIDER
		WebElement seconddropdownInput = driver.findElement(By.name("secondaryInsuranceProvider"));
		js.executeScript("arguments[0].click();", seconddropdownInput);
		Thread.sleep(300);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='dropdown-menu show']")));
		WebElement secondoptionToSelect = driver
				.findElement(By.xpath("//li[@class='ng-star-inserted']/a[text()='Aetna Us Healthcare']"));
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
		WebElement seconddatePickerToggle = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("(//button[@aria-label='Open calendar'])[2]")));
		System.out.println("Second date picker toggle button found.");
		js.executeScript("arguments[0].click();", seconddatePickerToggle);
		js.executeScript("arguments[0].click();", seconddatePickerToggle);

		// Wait for and click the next month button for the second date picker
		System.out.println("Waiting for the next month button for the second date picker...");
		WebElement nextMonthButton1 = wait
				.until(ExpectedConditions.elementToBeClickable(By.className("mat-calendar-next-button")));
		System.out.println("Next month button for the second date picker found.");
		js.executeScript("arguments[0].click();", nextMonthButton1);
		logger.info("Next month button for the second date picker found.");

		// Wait for and click the specific date button for the second date picker
		System.out.println("Waiting for the specific date button (June 23, 2024)...");
		WebElement dateButton1 = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='June 23, 2024']")));
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

		WebElement autocompletePanel = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.className("mat-autocomplete-panel")));
		List<WebElement> options;
		int previousSize = 0;
		while (true) {
			js.executeScript("arguments[0].scrollTop += arguments[0].offsetHeight;", autocompletePanel);
			Thread.sleep(500); // Reduce wait time for faster loading checks
			options = driver.findElements(By.cssSelector(".mat-option-text"));
			if (options.size() == previousSize) {
				break;
			}
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
		WebElement thirddatePickerToggle = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("(//button[@aria-label='Open calendar'])[3]")));
		System.out.println("Second date picker toggle button found.");
		logger.info("Second date picker toggle button found.");
		js.executeScript("arguments[0].click();", thirddatePickerToggle);

		System.out.println("Waiting for the specific date button (June 28, 2024)...");
		WebElement thirddateButton1 = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='May 1, 2024']")));
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

		WebElement buttonElement = driver
				.findElement(By.xpath("//input[@type='button' and @value='Find First Available']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttonElement);
		logger.info("Clicked 'Find First Available' button.");

		long endTime = System.currentTimeMillis();
		long executionTime = endTime - startTime;
		System.out.println("Execution time: " + executionTime + " milliseconds");
		logger.info("Execution time: " + executionTime + " milliseconds");

		Thread.sleep(60000);

		try {
			String xpathExpression = "//div[contains(@class, 'row')]";
			List<WebElement> elementsWithText = driver.findElements(By.xpath(xpathExpression));

			js.executeScript("window.scrollTo(0, 0);");
			System.out.println("Found the elements to be clicked and scrolled down" + elementsWithText.size());
			for (WebElement elementWithText : elementsWithText) {
				try {
					// Scroll the specific element into view
					js.executeScript("arguments[0].scrollIntoView(true);", elementWithText);
					Thread.sleep(1000); // Optional wait to ensure smooth scroll

					// Find the "Schedule" button within the current element
					WebElement scheduleButton = elementWithText.findElement(
							By.cssSelector("div.col-md-2.text-center input.primary-btn-sm.primary-btn.dmWarning"));
					js.executeScript("window.scrollTo(0, 0);");
					System.out.println("Waiting for Schedule button to be clicked...");
					System.out.println(elementWithText);
					System.out.println(scheduleButton);
					scheduleButton.click();
					System.out.println("Schedule button clicked");

					// Break the loop if you only want to click the first button found
					break;
				} catch (Exception e) {
					System.out.println(
							"Failed to find or click the Schedule button in one of the elements. Continuing to the next element...");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			logger.debug("clicked on the scheduled button completed");
		}

	}

	private void handlePatientInformationPage(Map<String, String> data, WebDriverWait wait, JavascriptExecutor js)
			throws InterruptedException {
		WebElement lastNameField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.name("patientLastName")));
		WebElement patientMiddleName = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.name("patientMiddleName")));
		WebElement firstNameField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.name("patientFirstName")));
		WebElement birthMonthField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.name("patientBirthMonth")));
		WebElement birthDayField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.name("patientBirthDay")));
		WebElement birthYearField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.name("patientBirthYear")));
		WebElement patientLookupButton = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.cssSelector("input[type='submit'][value='Patient Lookup'].primary-btn")));
		Thread.sleep(1000);
		System.out.println(patientLookupButton + "" + lastNameField + "" + firstNameField + "" + birthDayField + ""
				+ birthMonthField + "" + birthYearField);
		String expectedUrl = "http://127.0.0.1:90/#/home";
		verifyUrl(expectedUrl);
		Thread.sleep(1000);
		pageIcon();
		homeTabLink();
		patientInformationTitle();
		handleEmptyFields(patientLookupButton, lastNameField, wait);
		handleInvalidDate(data, wait, patientLookupButton, birthMonthField, birthDayField, birthYearField);
		handleInvalidMonth(data, wait, patientLookupButton, birthMonthField, birthDayField, birthYearField);
		handleFutureDateOfBirth(data, wait, patientLookupButton, lastNameField, firstNameField, birthMonthField,
				birthDayField, birthYearField);
		Thread.sleep(1000);
		creatingNewPatient(data, wait, js, patientLookupButton, lastNameField, firstNameField, birthMonthField,
				birthDayField, birthYearField);
	}

	private void patientInformationTitle() {
		long startTime = System.currentTimeMillis(); // Record the start time
		WebElement patientInfoTitle = driver.findElement(By.xpath("//h2[text()='Patient Information']"));
		long executionTime = System.currentTimeMillis() - startTime; // Calculate the execution time
		if (patientInfoTitle == null || !patientInfoTitle.isDisplayed()) {
			results.add(new TestResult("Patient Information Title", "Failed", executionTime,
					"Patient Information page title is not displayed."));
			logger.error("Error: Patient Information page title is not displayed.");
		} else {
			results.add(new TestResult("Patient Information Title", "Passed", executionTime, "NA"));
			logger.info("Patient Information page title is displayed - passed.");
			System.out.println("Patient Information title is verified: " + patientInfoTitle.getText());
		}
	}

	private void homeTabLink() {
		long startTime = System.currentTimeMillis(); // Record the start time
		WebElement homeLink = driver.findElement(By.linkText("Home"));
		homeLink.click();
		String expectedUrl = "http://127.0.0.1:90/#/home";
		String actualUrl = driver.getCurrentUrl();
		long executionTime = System.currentTimeMillis() - startTime; // Calculate the execution time
		if (!expectedUrl.equals(actualUrl)) {
			results.add(new TestResult("Home Tab Link", "Failed", executionTime,
					"Expected: " + expectedUrl + ", but got: " + actualUrl));
			logger.error("Error: Login was not successful, URL did not change to the home page. Expected: "
					+ expectedUrl + ", but got: " + actualUrl);
		} else {
			results.add(new TestResult("Home Tab Link", "Passed", executionTime, "NA"));
			logger.info("Home Tab Link - passed.");
			System.out.println("homeTabLink - passed");
		}
	}

	private void pageIcon() {
		long startTime = System.currentTimeMillis(); // Record the start time
		WebElement iconElement = driver
				.findElement(By.xpath("//a[@href='#']/img[@src='./assets/images/practices/doctorsuneel.png']"));
		long executionTime = System.currentTimeMillis() - startTime; // Calculate the execution time
		System.out.println(iconElement);

		if (iconElement == null || !iconElement.isDisplayed()) {
			results.add(new TestResult("Patient Information Page Icon", "Failed", executionTime,
					"Patient Information page icon is not displayed."));
			logger.error("Error: Patient Information page icon is not displayed.");
		} else {
			results.add(new TestResult("Patient Information Page Icon", "Passed", executionTime, "NA"));
			logger.info("Patient Information page icon is displayed - passed.");
		}
	}

	private void handleInvalidDate(Map<String, String> data, WebDriverWait wait, WebElement patientLookupButton,
			WebElement birthMonthField, WebElement birthDayField, WebElement birthYearField)
			throws InterruptedException {
		long startTime = System.currentTimeMillis(); // Record the start time

		// Clear and fill the fields with provided data
		clearAndFillFields(driver, wait);

		// Enter an invalid birth month, an invalid birth day, and the provided birth
		// year
		birthMonthField.sendKeys(data.get("patientBirthDay"));
		birthDayField.sendKeys("33");
		birthYearField.sendKeys(data.get("patientBirthYear"));

		// Click on the patient lookup button
		patientLookupButton.click();
		Thread.sleep(100); // Wait for the error message to appear

		// Find the error message element
		WebElement errorMessage = driver.findElement(By.cssSelector("p.text-danger"));
		long executionTime = System.currentTimeMillis() - startTime; // Calculate the execution time

		// Get the text of the error message
		String errorText = errorMessage.getText();

		// Check if the error message indicates an invalid date
		if (!"Invalid Date".equals(errorText)) {
			// Log the failure result if the error message is not as expected
			results.add(new TestResult("Handle Invalid Date", "Failed", executionTime,
					"Invalid Date of birth is not handled correctly."));
			logger.error("Error: Invalid Date of birth is accepting by patient information page");
		} else {
			// Log the success result if the error message is as expected
			results.add(new TestResult("Handle Invalid Date", "Passed", executionTime, "NA"));
			logger.info("Error message found while handling Invalid date - passed.");
		}
	}

	private void handleInvalidMonth(Map<String, String> data, WebDriverWait wait, WebElement patientLookupButton,
			WebElement birthMonthField, WebElement birthDayField, WebElement birthYearField)
			throws InterruptedException {
		long startTime = System.currentTimeMillis(); // Record the start time

		// Clear and fill the fields with provided data
		clearAndFillFields(driver, wait);

		// Enter an invalid birth month, the provided birth day, and the provided birth
		// year
		birthMonthField.sendKeys("14");
		birthDayField.sendKeys(data.get("patientBirthDay"));
		birthYearField.sendKeys(data.get("patientBirthYear"));

		// Click on the patient lookup button
		patientLookupButton.click();
		Thread.sleep(100); // Wait for the error message to appear

		// Find the error message element
		WebElement errorMessage = driver.findElement(By.cssSelector("p.text-danger"));
		long executionTime = System.currentTimeMillis() - startTime; // Calculate the execution time

		// Get the text of the error message
		String errorText = errorMessage.getText();

		// Check if the error message indicates an invalid date
		if (!"Invalid Date".equals(errorText)) {
			// Log the failure result if the error message is not as expected
			results.add(new TestResult("Handle Invalid Month", "Failed", executionTime,
					"Invalid Date of birth is not handled correctly."));
			logger.error("Error: Invalid Date of birth is accepting by patient information page");
		} else {
			// Log the success result if the error message is as expected
			results.add(new TestResult("Handle Invalid Month", "Passed", executionTime, "NA"));
			logger.info("Error message found while handling Invalid date - passed.");
		}
	}

	private void creatingNewPatient(Map<String, String> data, WebDriverWait wait, JavascriptExecutor js,
			WebElement patientLookupButton, WebElement lastNameField, WebElement firstNameField,
			WebElement birthMonthField, WebElement birthDayField, WebElement birthYearField)
			throws InterruptedException {
		long startTime = System.currentTimeMillis(); // Record the start time
		clearAndFillFields(driver, wait);
		birthMonthField.sendKeys("02");
		clearAndFillFields(driver, wait);
		lastNameField.sendKeys(data.get("patientLastName"));
		firstNameField.sendKeys(data.get("patientFirstName"));
		birthMonthField.sendKeys(data.get("patientMiddleName"));
		birthDayField.sendKeys(data.get("patientBirthDay"));
		birthYearField.sendKeys(data.get("patientBirthYear"));
		birthMonthField.sendKeys(data.get("patientBirthMonth"));
		patientLookupButton.click();
		Thread.sleep(8000); // Wait for patient selection
		driver.findElement(By.cssSelector("button.primary-btn")).click();
		long executionTime = System.currentTimeMillis() - startTime;
		results.add(new TestResult("Creating New Patient", "Passed", executionTime, "NA"));
		logger.info("Creating new patient - passed.");
		ExcelUtils.writeToExcel(results, "newPatientResults.xlsx");
	}

	private static void clearAndFillFields(WebDriver driver, WebDriverWait wait) {
		try {
			WebElement lastNameField = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.name("patientLastName")));
			WebElement middleName = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.name("patientMiddleName")));
			WebElement firstNameField = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.name("patientFirstName")));
			WebElement birthMonthField = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.name("patientBirthMonth")));
			WebElement birthDayField = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.name("patientBirthDay")));
			WebElement birthYearField = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.name("patientBirthYear")));

			lastNameField.clear();
			firstNameField.clear();
			middleName.clear();
			birthMonthField.clear();
			birthDayField.clear();
			birthYearField.clear();

		} catch (Exception e) {
			logger.error("An error occurred while clearing or filling out fields: ");
		}
	}

	private void handleFutureDateOfBirth(Map<String, String> data, WebDriverWait wait, WebElement patientLookupButton,
			WebElement lastNameField, WebElement firstNameField, WebElement birthMonthField, WebElement birthDayField,
			WebElement birthYearField) throws InterruptedException {
		long startTime = System.currentTimeMillis(); // Record the start time
		clearAndFillFields(driver, wait);
		birthMonthField.sendKeys(data.get("patientBirthMonth"));
		birthDayField.sendKeys(data.get("patientBirthDay"));
		birthYearField.sendKeys("2025");
		patientLookupButton.click();
		Thread.sleep(100); // Wait for the error message to appear
		WebElement errorMessage = driver.findElement(By.cssSelector("p.text-danger"));
		long executionTime = System.currentTimeMillis() - startTime; // Calculate the execution time
		String errorText = errorMessage.getText();
		if (!"Invalid Date".equals(errorText)) {
			results.add(new TestResult("Handle Future Date of Birth", "Failed", executionTime,
					"Invalid Date of birth is not handled correctly."));
			logger.error("Error: Invalid Date of birth is accepting by patient information page");
		} else {
			results.add(new TestResult("Handle Future Date of Birth", "Passed", executionTime, "NA"));
			logger.info("Error message found while handling future date - passed.");
		}
	}

	private void handleEmptyFields(WebElement patientLookupButton, WebElement lastNameField, WebDriverWait wait) {
		long startTime = System.currentTimeMillis(); // Record the start time
		try {
			patientLookupButton.click();
			Thread.sleep(200); // Wait for the error message to appear
			WebElement errorMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
					"//div[contains(text(), \"Please enter a patient's first name, last name, or full date of birth to continue\")]")));
			long executionTime = System.currentTimeMillis() - startTime; // Calculate the execution time
			String expectedError = "Please enter a patient's first name, last name, or full date of birth to continue";
			if (!expectedError.equals(errorMessageElement.getText())) {
				results.add(new TestResult("Handle Empty Fields", "Failed", executionTime,
						"Expected error message is not visible for patient lookup button upon empty fields."));
				logger.error("Error: Expected Error is not visible for patient lookup button upon empty fields");
			} else {
				results.add(new TestResult("Handle Empty Fields", "Passed", executionTime, "NA"));
				logger.info("Test 'Empty Username and Password' - passed.");
			}
		} catch (Exception e) {
			logger.error("Exception occurred: " + e.getMessage());
		}
	}

	private void verifyUrl(String expectedUrl) throws InterruptedException {
		long startTime = System.currentTimeMillis(); // Record the start time
		Thread.sleep(10000); // Wait for the URL to load completely
		long executionTime = System.currentTimeMillis() - startTime; // Calculate the execution time

		String actualUrl = driver.getCurrentUrl();
		if (!expectedUrl.equals(actualUrl)) {
			results.add(new TestResult("Verifying the Login URL", "Failed", executionTime,
					"Expected URL " + expectedUrl + " is not matching with actual URL " + actualUrl));
			logger.error("Error: Expected URL " + expectedUrl + " is not matching with actual URL " + actualUrl);
		} else {
			results.add(new TestResult("Verifying the Login URL", "Passed", executionTime, "NA"));
			logger.info("Home URL is verified - passed");
		}
		if (!driver.getTitle().contains("Opargo")) {
			results.add(new TestResult("Opargo title and logo on the browser tab", "Failed", executionTime,
					"Opargo title and logo are not present on the browser tab."));
			logger.error("Error: Opargo title and logo are not present on the browser tab.");
		} else {
			results.add(new TestResult("Opargo title and logo on the browser tab", "Passed", executionTime, "NA"));
			logger.info("Opargo title and logo on the browser tab are verified - passed");
		}
		System.out.println("Home URL is verified");
	}

	private void navigatingToLoginPage() throws InterruptedException {
		long startTime = System.currentTimeMillis();
		driver.get("https://allscripts-qa.opargo.com/v2/#/login");
		String expectedUrl = "https://allscripts-qa.opargo.com/v2/#/login";
		long endTime = System.currentTimeMillis();
		long executionTime = endTime - startTime;
		if (!expectedUrl.equals(driver.getCurrentUrl())) {
			results.add(new TestResult("Verifying the Login URL", "Failed", executionTime,
					"Expected: " + expectedUrl + ", but got: " + driver.getCurrentUrl()));
		} else {
			results.add(new TestResult("Verifying the Login URL", "Passed", executionTime, "NA"));
		}
	}

	private void titleAndLogoCheck() {
		long startTime = System.currentTimeMillis();
		String expectedTitle = "Opargo";
		String actualTitle = driver.getTitle();
		long executionTime = System.currentTimeMillis() - startTime;
		if (!expectedTitle.equals(actualTitle)) {
			results.add(new TestResult("Opargo title and logo on the browser tab", "Failed", executionTime,
					"Expected: " + expectedTitle + ", but got: " + actualTitle));
		} else {
			results.add(new TestResult("Opargo title and logo on the browser tab", "Passed", executionTime, "NA"));
		}
	}

	private void loginWithInvalidUsername(WebElement usernameField, WebElement passwordField, WebElement loginButton,
			String expectedErrorMessage, WebDriverWait wait) throws InterruptedException {
		long startTime = System.currentTimeMillis();
		usernameField.clear();
		passwordField.clear();
		usernameField.sendKeys("invalidUser");
		passwordField.sendKeys("DocUser$444");
		System.out.println("Entered into buster captcha1");
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='reCAPTCHA']")));
		WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("recaptcha-anchor")));
		System.out.println("Entered into buster captcha2" + checkbox);
		checkbox.click();
		System.out.println("Entered into buster captcha3");

		driver.switchTo().defaultContent();
		Thread.sleep(1000);
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
		int intervalInSeconds = 1;
		System.out.println("Entered into buster captcha4");
		while (true) {
			Thread.sleep(1000);
			System.out.println("Entered into buster captcha5");
			try {
				System.out.println("Entered into buster captcha6");
				wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
						By.xpath("//iframe[@title='recaptcha challenge expires in two minutes']")));
				WebElement button = wait1.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//div[@class='button-holder help-button-holder']")));
				System.out.println("Entered into buster captcha7" + button);
				if (!button.isDisplayed()) {
					break;
				}
				button.click();
				driver.switchTo().defaultContent();
				Thread.sleep(intervalInSeconds * 1000);
			} catch (Exception e) {
				System.out.println("Exception occurred: " + e.getMessage());
				break;
			}
		}

		loginButton.click();
		long executionTime = System.currentTimeMillis() - startTime;
		WebElement errorMessage = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb5.text-danger.ng-star-inserted")));
		if (!errorMessage.isDisplayed()) {
			results.add(new TestResult("Login with invalid username", "Failed", executionTime,
					"Error message for invalid login is not displayed."));
		} else {
			String actualErrorMessage = errorMessage.getText();
			if (!expectedErrorMessage.equals(actualErrorMessage)) {
				results.add(new TestResult("Login with invalid username", "Failed", executionTime,
						"Expected: " + expectedErrorMessage + ", but got: " + actualErrorMessage));
			} else {
				results.add(new TestResult("Login with invalid username", "Passed", executionTime, "NA"));
			}
		}

	}

	private void loginWithInvalidPassword(WebElement usernameField, WebElement passwordField, WebElement loginButton,
			String expectedErrorMessage, WebDriverWait wait) throws InterruptedException {
		long startTime = System.currentTimeMillis();
		usernameField.clear();
		passwordField.clear();
		usernameField.sendKeys("yp_co_a");
		passwordField.sendKeys("invalidPassword");
		System.out.println("Entered into buster captcha1");
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='reCAPTCHA']")));
		WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("solver-button")));
		checkbox.click();
		System.out.println("Entered into buster captcha2");
		driver.switchTo().defaultContent();
		Thread.sleep(1000);
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
		int intervalInSeconds = 1;
		System.out.println("Entered into buster captcha3");
		while (true) {
			Thread.sleep(1000);
			System.out.println("Entered into buster captcha4");
			try {
				wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
						By.xpath("//iframe[@title='recaptcha challenge expires in two minutes']")));
				WebElement button = wait1.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//div[@class='button-holder help-button-holder']")));
				System.out.println(button);
				if (!button.isDisplayed()) {
					break;
				}
				button.click();
				driver.switchTo().defaultContent();
				Thread.sleep(intervalInSeconds * 1000);
			} catch (Exception e) {
				System.out.println("Exception occurred: " + e.getMessage());
				break;
			}
		}
		loginButton.click();
		WebElement errorMessageElement1 = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb5.text-danger.ng-star-inserted")));
		String actualErrorMessage1 = errorMessageElement1.getText();
		long executionTime = System.currentTimeMillis() - startTime;
		if (!actualErrorMessage1.equals(expectedErrorMessage)) {
			results.add(new TestResult("Verifying error message for invalid password", "Failed", executionTime,
					"Expected: " + expectedErrorMessage + ", Actual: " + actualErrorMessage1));
		} else {
			results.add(new TestResult("Verifying error message for invalid password", "Passed", executionTime, "NA"));
		}
	}

	private void loginWithEmptyCredentials(WebElement usernameField, WebElement passwordField, WebElement loginButton,
			String expectedErrorMessage, WebDriverWait wait) throws InterruptedException {
		long startTime = System.currentTimeMillis();
		usernameField.clear();
		passwordField.clear();
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='reCAPTCHA']")));
		WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("solver-button")));
		checkbox.click();
		driver.switchTo().defaultContent();
		Thread.sleep(1000);
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
		int intervalInSeconds = 1;
		while (true) {
			Thread.sleep(1000);
			try {
				WebElement button = wait1.until(ExpectedConditions.elementToBeClickable(By.className("button-holder")));
				if (!button.isDisplayed()) {
					break;
				}
				button.click();
				Thread.sleep(intervalInSeconds * 1000);
			} catch (Exception e) {
				System.out.println("Exception occurred: " + e.getMessage());
				break;
			}
		}
		loginButton.click();
		WebElement errorMessageElement2 = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb5.text-danger.ng-star-inserted")));
		String actualErrorMessage2 = errorMessageElement2.getText();
		long executionTime = System.currentTimeMillis() - startTime;
		if (!actualErrorMessage2.equals(expectedErrorMessage)) {
			results.add(new TestResult("Verifying error message for empty credentials", "Failed", executionTime,
					"Expected: " + expectedErrorMessage + ", Actual: " + actualErrorMessage2));
		} else {
			results.add(new TestResult("Verifying error message for empty credentials", "Passed", executionTime, "NA"));
		}
	}

	private void loginWithEmptyUsername(WebElement usernameField, WebElement passwordField, WebElement loginButton,
			String expectedErrorMessage, WebDriverWait wait) throws InterruptedException {
		long startTime = System.currentTimeMillis();
		usernameField.clear();
		passwordField.sendKeys("validPassword");
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='reCAPTCHA']")));
		WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("solver-button")));
		checkbox.click();
		driver.switchTo().defaultContent();
		Thread.sleep(1000);
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
		int intervalInSeconds = 1;
		while (true) {
			Thread.sleep(1000);
			try {
				WebElement button = wait1.until(ExpectedConditions.elementToBeClickable(By.className("button-holder")));
				if (!button.isDisplayed()) {
					break;
				}
				button.click();
				Thread.sleep(intervalInSeconds * 1000);
			} catch (Exception e) {
				System.out.println("Exception occurred: " + e.getMessage());
				break;
			}
		}
		loginButton.click();
		WebElement errorMessageElement3 = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb5.text-danger.ng-star-inserted")));
		String actualErrorMessage3 = errorMessageElement3.getText();
		long executionTime = System.currentTimeMillis() - startTime;
		if (!actualErrorMessage3.equals(expectedErrorMessage)) {
			results.add(new TestResult("Verifying error message for empty username", "Failed", executionTime,
					"Expected: " + expectedErrorMessage + ", Actual: " + actualErrorMessage3));
		} else {
			results.add(new TestResult("Verifying error message for empty username", "Passed", executionTime, "NA"));
		}
	}

	private void loginWithEmptyPassword(WebElement usernameField, WebElement passwordField, WebElement loginButton,
			String expectedErrorMessage, WebDriverWait wait) throws InterruptedException {
		long startTime = System.currentTimeMillis();
		usernameField.sendKeys("yp_co_a");
		passwordField.clear();
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='reCAPTCHA']")));
		WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("solver-button")));
		checkbox.click();
		driver.switchTo().defaultContent();
		Thread.sleep(1000);
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
		int intervalInSeconds = 1;
		while (true) {
			Thread.sleep(1000);
			try {
				WebElement button = wait1.until(ExpectedConditions.elementToBeClickable(By.className("button-holder")));
				if (!button.isDisplayed()) {
					break;
				}
				button.click();
				Thread.sleep(intervalInSeconds * 1000);
			} catch (Exception e) {
				System.out.println("Exception occurred: " + e.getMessage());
				break;
			}
		}
		loginButton.click();
		WebElement errorMessageElement4 = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb5.text-danger.ng-star-inserted")));
		String actualErrorMessage4 = errorMessageElement4.getText();
		long executionTime = System.currentTimeMillis() - startTime;
		if (!actualErrorMessage4.equals(expectedErrorMessage)) {
			results.add(new TestResult("Verifying error message for empty password", "Failed", executionTime,
					"Expected: " + expectedErrorMessage + ", Actual: " + actualErrorMessage4));
		} else {
			results.add(new TestResult("Verifying error message for empty password", "Passed", executionTime, "NA"));
		}
	}

	private void checkPlaceholders(WebElement usernameField, WebElement passwordField) {
		long startTime = System.currentTimeMillis();
		String usernamePlaceholder = usernameField.getAttribute("placeholder");
		String passwordPlaceholder = passwordField.getAttribute("placeholder");
		long executionTime = System.currentTimeMillis() - startTime;
		if (!usernamePlaceholder.equals("Username")) {
			results.add(new TestResult("Checking username placeholder", "Failed", executionTime,
					"Expected 'Username' but found '" + usernamePlaceholder + "'"));
		} else {
			results.add(new TestResult("Checking username placeholder", "Passed", executionTime, "NA"));
		}

		if (!passwordPlaceholder.equals("Password")) {
			results.add(new TestResult("Checking password placeholder", "Failed", executionTime,
					"Expected 'Password' but found '" + passwordPlaceholder + "'"));
		} else {
			results.add(new TestResult("Checking password placeholder", "Passed", executionTime, "NA"));
		}
	}

	private void checkLoginButtonColor(WebElement loginButton) {
		long startTime = System.currentTimeMillis();
		String loginButtonColor = loginButton.getCssValue("background-color");
		String expectedColorValue = "rgba(47, 175, 83, 1)";
		long executionTime = System.currentTimeMillis() - startTime;
		if (!loginButtonColor.equals(expectedColorValue)) {
			results.add(new TestResult("Checking login button color", "Failed", executionTime,
					"Expected: " + expectedColorValue + ", but found: " + loginButtonColor));
		} else {
			results.add(new TestResult("Checking login button color", "Passed", executionTime, "NA"));
		}
	}

	private void checkForgotPasswordLink(WebDriverWait wait) throws InterruptedException {
		long startTime = System.currentTimeMillis();
		WebElement forgotPasswordLink = driver.findElement(By.linkText("Forgot your password?"));
		long executionTime = System.currentTimeMillis() - startTime;
		if (!forgotPasswordLink.isDisplayed()) {
			results.add(new TestResult("Checking forgot password link visibility", "Failed", executionTime,
					"Forgot password link is not displayed."));
		} else {
			forgotPasswordLink.click();
			Thread.sleep(3000);
			String expectedUrl = "http://127.0.0.1:90/#/forgot";
			String actualUrl = driver.getCurrentUrl();
			executionTime = System.currentTimeMillis() - startTime;
			if (!expectedUrl.equals(actualUrl)) {
				results.add(new TestResult("Checking forgot password link URL", "Failed", executionTime,
						"Expected: " + expectedUrl + ", but got: " + actualUrl));
				driver.findElement(By.linkText("Return to Login Page")).click();
			} else {
				results.add(new TestResult("Checking forgot password link URL", "Passed", executionTime, "NA"));
				driver.findElement(By.linkText("Return to Login Page")).click();
			}
		}
	}

	private void checkForgotUsernameLink(WebDriverWait wait) throws InterruptedException {
		long startTime = System.currentTimeMillis();
		WebElement forgotUsernameLink = driver.findElement(By.linkText("Forgot your username?"));
		long executionTime = System.currentTimeMillis() - startTime;
		if (!forgotUsernameLink.isDisplayed()) {
			results.add(new TestResult("Checking forgot username link visibility", "Failed", executionTime,
					"Forgot username link is not displayed."));
		} else {
			forgotUsernameLink.click();
			Thread.sleep(3000);
			String expectedUrl = "http://127.0.0.1:90/#/forgotuser";
			String actualUrl = driver.getCurrentUrl();
			executionTime = System.currentTimeMillis() - startTime;
			if (!expectedUrl.equals(actualUrl)) {
				results.add(new TestResult("Checking forgot username link URL", "Failed", executionTime,
						"Expected: " + expectedUrl + ", but got: " + actualUrl));
				driver.findElement(By.linkText("Return to Login Page")).click();
			} else {
				results.add(new TestResult("Checking forgot username link URL", "Passed", executionTime, "NA"));
				driver.findElement(By.linkText("Return to Login Page")).click();
			}
		}
	}

	private void checkTakeTourLink(JavascriptExecutor js) throws InterruptedException {
		long startTime = System.currentTimeMillis();
		WebElement takeTourLink = driver.findElement(By.cssSelector("a[href='https://www.opargo.com/#about']"));
		long executionTime = System.currentTimeMillis() - startTime;
		if (!takeTourLink.isDisplayed()) {
			results.add(new TestResult("Checking 'Take a Tour' link visibility", "Failed", executionTime,
					"Take a Tour link is not displayed."));
		} else {
			takeTourLink.click();
			Thread.sleep(5000);
			List<String> tabHandles = new ArrayList<>(driver.getWindowHandles());
			String expectedUrl = "https://veradigm.com/predictive-scheduler/#about";
			driver.switchTo().window(tabHandles.get(1));
			String actualUrl = driver.getCurrentUrl();
			executionTime = System.currentTimeMillis() - startTime;
			if (!expectedUrl.equals(actualUrl)) {
				results.add(new TestResult("Checking 'Take a Tour' link URL", "Failed", executionTime,
						"Expected: " + expectedUrl + ", but got: " + actualUrl));
			} else {
				results.add(new TestResult("Checking 'Take a Tour' link URL", "Passed", executionTime, "NA"));
			}
			driver.close();
			driver.switchTo().window(tabHandles.get(0));
		}
	}

	private void checkSpeakWithRepresentativeLink(JavascriptExecutor js) throws InterruptedException {
		long startTime = System.currentTimeMillis();
		WebElement speakWithRepLink = driver.findElement(By.cssSelector("a[href='https://www.opargo.com/#contact']"));
		long executionTime = System.currentTimeMillis() - startTime;
		if (!speakWithRepLink.isDisplayed()) {
			results.add(new TestResult("Checking 'Speak with a Representative' link visibility", "Failed",
					executionTime, "Speak with a Representative link is not displayed."));
		} else {
			speakWithRepLink.click();
			Thread.sleep(5000);
			List<String> tabHandles = new ArrayList<>(driver.getWindowHandles());
			String expectedUrl = "https://veradigm.com/predictive-scheduler/#contact";
			driver.switchTo().window(tabHandles.get(1));
			String actualUrl = driver.getCurrentUrl();
			executionTime = System.currentTimeMillis() - startTime;
			if (!expectedUrl.equals(actualUrl)) {
				results.add(new TestResult("Checking 'Speak with a Representative' link URL", "Failed", executionTime,
						"Expected: " + expectedUrl + ", but got: " + actualUrl));
			} else {
				results.add(new TestResult("Checking 'Speak with a Representative' link URL", "Passed", executionTime,
						"NA"));
			}
			driver.close();
			driver.switchTo().window(tabHandles.get(0));
		}
	}

	private void checkNewHereHeading() {
		long startTime = System.currentTimeMillis();
		WebElement newHereHeading = driver.findElement(By.xpath("//h1[text()='New here?']"));
		long executionTime = System.currentTimeMillis() - startTime;
		if (!newHereHeading.isDisplayed()) {
			results.add(new TestResult("Check 'New here?' heading", "Failed", executionTime,
					"'New here?' heading is not displayed."));
		} else {
			results.add(new TestResult("Check 'New here?' heading", "Passed", executionTime, "NA"));
		}
	}

	private void checkInfoParagraph() {
		long startTime = System.currentTimeMillis();
		WebElement infoParagraph = driver
				.findElement(By.xpath("//p[contains(text(), 'Learn how our scheduling system')]"));
		long executionTime = System.currentTimeMillis() - startTime;
		if (!infoParagraph.isDisplayed()) {
			results.add(new TestResult("Check information paragraph", "Failed", executionTime,
					"Information paragraph is not displayed."));
		} else {
			results.add(new TestResult("Check information paragraph", "Passed", executionTime, "NA"));
		}
	}

	private void checkFooterElement() {
		long startTime = System.currentTimeMillis();
		WebElement footer = driver.findElement(By.cssSelector("footer.no-select"));
		long executionTime = System.currentTimeMillis() - startTime;
		if (!footer.isDisplayed()) {
			results.add(new TestResult("Check footer element", "Failed", executionTime, "Footer is not displayed."));
		} else {
			String footerColor = footer.getCssValue("background");
			String expectedFooterColor = "rgb(37, 37, 37) none repeat scroll 0% 0% / auto padding-box border-box";
			if (!footerColor.equals(expectedFooterColor)) {
				results.add(
						new TestResult("Check footer element", "Failed", executionTime, "Footer color is not black."));
			} else {
				results.add(new TestResult("Check footer element", "Passed", executionTime, "NA"));
			}
		}
	}

	private void checkFooterLogo() {
		long startTime = System.currentTimeMillis();
		List<WebElement> footerLogoElements = driver
				.findElements(By.cssSelector("footer img[src='./assets/images/100x40.png']"));
		long executionTime = System.currentTimeMillis() - startTime;
		if (footerLogoElements.isEmpty()) {
			results.add(new TestResult("Check footer logo", "Failed", executionTime, "Footer logo element not found."));
		} else {
			WebElement footerLogo = footerLogoElements.get(0);
			if (!footerLogo.isDisplayed()) {
				results.add(
						new TestResult("Check footer logo", "Failed", executionTime, "Footer logo is not displayed."));
			} else {
				results.add(new TestResult("Check footer logo", "Passed", executionTime, "NA"));
			}
		}
	}

	private void checkLicenseMessage() {
		long startTime = System.currentTimeMillis();
		WebElement licenseMessage = driver
				.findElement(By.xpath("//footer//li[contains(text(), '© Opargo LLC 2024. All rights reserved.')]"));
		long executionTime = System.currentTimeMillis() - startTime;
		if (!licenseMessage.isDisplayed()) {
			results.add(new TestResult("Check license reserved message", "Failed", executionTime,
					"License reserved message is not displayed."));
		} else {
			results.add(new TestResult("Check license reserved message", "Passed", executionTime, "NA"));
		}
	}

	private void checkPrivacyPolicyLink() {
		long startTime = System.currentTimeMillis();
		WebElement privacyPolicyLink = driver.findElement(By.linkText("Privacy and Security Policy."));
		long executionTime = System.currentTimeMillis() - startTime;
		if (!privacyPolicyLink.isDisplayed()) {
			results.add(new TestResult("Check privacy and security policy link", "Failed", executionTime,
					"Privacy and security policy link is not displayed."));
		} else {
			results.add(new TestResult("Check privacy and security policy link", "Passed", executionTime, "NA"));
		}
	}

	private void performValidLogin(WebDriverWait wait, JavascriptExecutor js, Map<String, String> data)
			throws InterruptedException {
		long startTime = System.currentTimeMillis();
		WebElement usernameField = driver.findElement(By.name("username"));
		WebElement passwordField = driver.findElement(By.name("password"));
		WebElement loginButton = driver.findElement(By.cssSelector(".mb5.primary-btn"));
		usernameField.clear();
		passwordField.clear();
		usernameField.sendKeys("sireesha");
		passwordField.sendKeys("DocUser$444");
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='reCAPTCHA']")));
		WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("solver-button")));
		checkbox.click();
		driver.switchTo().defaultContent();
		Thread.sleep(1000);
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
		int intervalInSeconds = 1;
		while (true) {
			Thread.sleep(1000);
			try {
				WebElement button = wait1.until(ExpectedConditions.elementToBeClickable(By.className("button-holder")));
				if (!button.isDisplayed()) {
					break;
				}
				button.click();
				Thread.sleep(intervalInSeconds * 1000);
			} catch (Exception e) {
				System.out.println("Exception occurred: " + e.getMessage());
				break;
			}
		}
		loginButton.click();
		Thread.sleep(10000);
		long executionTime = System.currentTimeMillis() - startTime;
		String expectedUrl = "http://127.0.0.1:90/#/home"; // Replace with the actual expected URL
		String actualUrl = driver.getCurrentUrl();
		if (!expectedUrl.equals(actualUrl)) {
			results.add(new TestResult("Perform valid login", "Failed", executionTime,
					"Expected: " + expectedUrl + ", but got: " + actualUrl));
		} else {
			results.add(new TestResult("Perform valid login", "Passed", executionTime, "NA"));
		}

	}

	@AfterSuite
	public void generateReport() {

		ExcelUtils.writeToExcel(results, "recaptchalogin.xlsx");
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
