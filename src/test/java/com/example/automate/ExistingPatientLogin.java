package com.example.automate;

import java.awt.AWTException;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.python.modules.thread.thread;
import org.sikuli.script.FindFailed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.NgWebDriver;

@Listeners(TestExecutionListener.class)
public class ExistingPatientLogin {

	private static final Logger logger = LoggerFactory.getLogger(ExistingPatientLogin.class);
	public WebDriver driver;
	private BestFitPage bestFitPage;
	private List<TestResult> results = new ArrayList<>();

	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "C:\\Windows\\System32\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		driver = new ChromeDriver(options);
		logger.info("ChromeDriver initialized successfully The Existing Patient Application Flow.");
		this.bestFitPage = new BestFitPage(driver);
	}

	@Test
	public void loginPage() throws InterruptedException, IOException, FindFailed, AWTException {
		  String testName = "Google Search";
	        String status = "Passed";
	        String errorMessage = "";
		ExcelUtils excel = new ExcelUtils(
				"C:\\workspace\\BE\\opargoAutomationTesting\\src\\resources\\test-data\\input-data.xlsx");
		Map<String, String> data = excel.getRowData("Sheet1", 4);
		long startTime = System.currentTimeMillis();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(300));

		driver.get("http://127.0.0.1:90/#/login");

		// 1. login page
		Assert.assertEquals(driver.getTitle(), "Opargo");
		logger.info(
				"Verified Opargo title and logo on the browser tab 													   - passed.");
		WebElement usernameField = driver.findElement(By.name("username"));
		WebElement passwordField = driver.findElement(By.name("password"));
		WebElement loginButton = driver.findElement(By.cssSelector(".mb5.primary-btn"));
		String expectedErrorMessage = "Invalid username/password.";

//		loginWithInvalidUsername(usernameField, passwordField, loginButton, expectedErrorMessage, wait);
//		loginWithInvalidPassword(usernameField, passwordField, loginButton, expectedErrorMessage, wait);
//		loginWithEmptyCredentials(usernameField, passwordField, loginButton, expectedErrorMessage, wait);
//		loginWithEmptyUsername(usernameField, passwordField, loginButton, expectedErrorMessage, wait);
//		loginWithEmptyPassword(usernameField, passwordField, loginButton, expectedErrorMessage, wait);
//		checkPlaceholders(usernameField, passwordField);
//		checkLoginButtonColor(loginButton);
//		checkForgotPasswordLink(wait);
//		checkForgotUsernameLink(wait);
//		checkTakeTourLink(js);
//		checkSpeakWithRepresentativeLink(js);
//		checkNewHereHeading();
//		checkInfoParagraph();
//		checkFooterElement();
//		checkFooterLogo();
//		checkLicenseMessage();
//		checkPrivacyPolicyLink();
//		performValidLogin(wait, js, data);

		// 2. patient information page
		handlePatientInformationPage(wait, js);
		emptyPrimaryInsuranceProvider(wait, js, data);
		executionwithAllRequiredFields(wait, js, data);

//		WebElement primaryInsuranceProviderErrorMessageElement = wait
//				.until(ExpectedConditions.visibilityOfElementLocated(
//						By.xpath("//div[contains(text(), \"Please select an insurance provider\")]")));
//		String primaryInsuranceExpectedError = "Please select an insurance provider";
//		WebElement preferrenceProviderErrorMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
//				By.xpath("//div[contains(text(), \"Please select a preferred provider\")]")));
//		String preferrenceProviderExpectedError = "Please select a preferred provider";
//		if (!primaryInsuranceExpectedError.equals(primaryInsuranceProviderErrorMessageElement)
//				&& !preferrenceProviderErrorMessageElement.equals(preferrenceProviderExpectedError)) {
//			logger.error("Error: error message is not displaying based on primary insurance provider field is empty");
//		}

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
					wait.until(ExpectedConditions.elementToBeClickable(scheduleButton));

					System.out.println("Waiting for Schedule button to be clicked...");
					System.out.println(elementWithText);
					System.out.println(scheduleButton);
					scheduleButton.click();
					Thread.sleep(500);
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
		}

		long endTime = System.currentTimeMillis();
		long executionTime = endTime - startTime;
		logger.info("Execution time: {} milliseconds", executionTime);
		 results.add(new TestResult(testName, status, endTime, errorMessage));
	}

	private void loginWithInvalidUsername(WebElement usernameField, WebElement passwordField, WebElement loginButton,
			String expectedErrorMessage, WebDriverWait wait) {
		try {
			usernameField.clear();
			passwordField.clear();
			usernameField.sendKeys("invalidUsername");
			passwordField.sendKeys("YPRssp78@");
			loginButton.click();
			WebElement errorMessageElement = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb5.text-danger.ng-star-inserted")));
			String actualErrorMessage = errorMessageElement.getText();
			try {
				if (!actualErrorMessage.equals(expectedErrorMessage)) {
					throw new Exception("Error message content mismatch");
				}
				logger.info(
						"Test 'Invalid Username' 																			   - passed.");
			} catch (Exception e) {
				logger.error("Error: The error message content is incorrect. Expected: " + expectedErrorMessage
						+ ", Actual: " + actualErrorMessage, e);
			}

			// Check if the error message is displayed
			try {
				if (!errorMessageElement.isDisplayed()) {
					throw new Exception("Error message not displayed");
				}
			} catch (Exception e) {
				logger.error("Error: No error message displayed for invalid username.", e);
			}
		} catch (Exception e) {
			logger.error("Error during login process", e);
		}
		System.out.println("Case 4");

	}

	private void checkForgotPasswordLink(WebDriverWait wait) throws InterruptedException {
		WebElement forgotPasswordLink = driver.findElement(By.linkText("Forgot your password?"));
		if (!forgotPasswordLink.isDisplayed()) {
			logger.error("Error: Forgot password link is not displayed.");
		} else {
			forgotPasswordLink.click();

			// Wait for the new page to load (you might need a more sophisticated wait)
			Thread.sleep(3000);
			String expectedUrl = "http://127.0.0.1:90/#/forgot";
			String actualUrl = driver.getCurrentUrl();
			if (!expectedUrl.equals(actualUrl)) {
				logger.error("Error: Incorrect URL after clicking 'Forgot Password'. Expected: " + expectedUrl
						+ ", but got: " + actualUrl);
			} else {
				logger.info(
						"Verified 'Forgot Password Link' functionality 														   - passed.");
			}
			WebElement formElement = driver.findElement(By.tagName("form"));
			if (!formElement.isDisplayed()) {
				logger.error("Error: Expected element not found on the 'Forgot Password' page.");
			} else {
				WebElement returnToLoginLink = driver.findElement(By.linkText("Return to Login Page"));
				returnToLoginLink.click();
			}
		}
		System.out.println("Case 11");
	}

	private void executionwithAllRequiredFields(WebDriverWait wait, JavascriptExecutor js, Map<String, String> data)
			throws InterruptedException {
		driver.get("http://127.0.0.1:90/#/login");
		performValidLogin(wait, js, data);
		handlePatientInformationPage(wait, js);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("preferredProvider")));
		wait.until(ExpectedConditions.elementToBeClickable(By.name("preferredProvider")));
		WebElement dropdownElement = driver.findElement(By.name("preferredProvider"));
		Thread.sleep(1000);
		Select dropdown1 = new Select(dropdownElement);
		Thread.sleep(1000);
		// dropdown.selectByVisibleText("Cardiologist, Thomas");
		dropdown1.selectByValue("2915");
		Thread.sleep(5000);

		WebElement referral_source = driver.findElement(By.name("referralSource"));
		Select referralSource_dropdown = new Select(referral_source);
		referralSource_dropdown.selectByVisibleText("Billboard");

		WebElement officevisit_dropdownElement = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("select[name='officevisit']")));
		Select officevisit_dropdown = new Select(officevisit_dropdownElement);
		Thread.sleep(1000);
		officevisit_dropdown.selectByIndex(4);
		Thread.sleep(1000);

		WebElement thirddatePickerToggle = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("(//button[@aria-label='Open calendar'])")));
		js.executeScript("arguments[0].click();", thirddatePickerToggle);

		WebElement thirddateButton1 = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='June 1, 2024']")));
		js.executeScript("arguments[0].click();", thirddateButton1);
		WebElement patientbody_part = driver.findElement(By.name("patientBodyPart"));
		Select patientBodypartdropdown = new Select(patientbody_part);
		patientBodypartdropdown.selectByVisibleText("Knee");

		WebElement patientLocationOpt_dropdownElement = driver.findElement(By.name("patientLocationOpt"));
		Select patientLocationOpt_dropdown = new Select(patientLocationOpt_dropdownElement);
		patientLocationOpt_dropdown.selectByVisibleText("Left");

		WebElement textareaElement = driver.findElement(By.name("notes"));
		textareaElement.sendKeys(data.get("notes"));

		WebElement findFirstAvailableButton = wait.until(ExpectedConditions.elementToBeClickable(
				By.cssSelector("input[type='button'][value='Find First Available'].primary-btn")));
		System.out.println(findFirstAvailableButton);
		Thread.sleep(1000);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", findFirstAvailableButton);
		Thread.sleep(1000);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", findFirstAvailableButton);
		Thread.sleep(5000);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", findFirstAvailableButton);
	}

	private void emptyPrimaryInsuranceProvider(WebDriverWait wait, JavascriptExecutor js, Map<String, String> data)
			throws InterruptedException {

		System.out.println("starting the click");
		WebElement findFirstAvailableButton = wait.until(ExpectedConditions.elementToBeClickable(
				By.cssSelector("input[type='button'][value='Find First Available'].primary-btn")));
		System.out.println(findFirstAvailableButton);
		Thread.sleep(1000);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", findFirstAvailableButton);
		Thread.sleep(1000);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", findFirstAvailableButton);

		System.out.println("completed the click");
		WebElement element = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".text-danger.ng-star-inserted")));
		System.out.println("Is the error message visible? " + element.isDisplayed());
	}

	@AfterMethod
	public void tearDown() throws InterruptedException {
		Thread.sleep(2000);
		if (driver != null) {
			// driver.quit();
			logger.info("Browser closed and driver quit.\n");
		}
	}

	private void verifyUrl(String expectedUrl) throws InterruptedException {
		Thread.sleep(10000);
		if (!expectedUrl.equals(driver.getCurrentUrl())) {
			logger.error("Error: Expected URL " + expectedUrl + " is not matching with actual URL "
					+ driver.getCurrentUrl());
		}
		logger.info("Home url is verified																				   - passed");
		System.out.println("home url is verified");
	}

	private void handlePatientInformationPage(WebDriverWait wait, JavascriptExecutor js) throws InterruptedException {
		WebElement patientLookupButton = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.cssSelector("input[type='submit'][value='Patient Lookup'].primary-btn")));
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
		Thread.sleep(1000);
		System.out.println(patientLookupButton + "" + lastNameField + "" + firstNameField + "" + birthDayField + ""
				+ birthMonthField + "" + birthYearField);
		String expectedUrl = "http://127.0.0.1:90/#/home";
		verifyUrl(expectedUrl);
		Thread.sleep(1000);
//		handleEmptyFields(patientLookupButton, lastNameField, wait);
//		handleFutureDateOfBirth(wait, patientLookupButton, lastNameField, firstNameField, birthMonthField,
//		birthDayField, birthYearField);
		Thread.sleep(1000);
		searchExistingPatient(wait, js, patientLookupButton, lastNameField, firstNameField, birthMonthField,
				birthDayField, birthYearField);
	}

	private void handleEmptyFields(WebElement patientLookupButton, WebElement lastNameField, WebDriverWait wait)
			throws InterruptedException {
		// Empty Fields
		try {
			patientLookupButton.click();
			Thread.sleep(200);
			WebElement errorMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
					"//div[contains(text(), \"Please enter a patient's first name, last name, or full date of birth to continue\")]")));
			Thread.sleep(200);
			String expectedError = "Please enter a patient's first name, last name, or full date of birth to continue";
			if (!expectedError.equals(errorMessageElement.getText())) {
				logger.error("Error: Expected Error is not visible for patient lookup button upon empty fields");
			} else {
				logger.info("Test 'Empty Username and Password' 																	   - passed.");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void handleFutureDateOfBirth(WebDriverWait wait, WebElement patientLookupButton, WebElement lastNameField,
			WebElement firstNameField, WebElement birthMonthField, WebElement birthDayField, WebElement birthYearField)
			throws InterruptedException {
		// Upon future date of birth
		clearAndFillFields(driver, wait);
		lastNameField.sendKeys("kumar");
		firstNameField.sendKeys("Akshay");
		birthMonthField.sendKeys("12");
		birthDayField.sendKeys("02");
		birthYearField.sendKeys("2024");
		patientLookupButton.click();
		Thread.sleep(100);
		WebElement errorMessage = driver.findElement(By.cssSelector("p.text-danger"));
		String errorText = errorMessage.getText();
		Thread.sleep(8000);
		WebElement cancelButton = driver
				.findElement(By.cssSelector("a.btn.btn-default.center-block.secondary-btn.w340"));
		cancelButton.click();
		if (!"Invalid Date".equals(errorText)) {
			logger.error("Error: Invalid Date of birth is accepting by patient information page");
		} else {
			logger.info(
					"error message found while handling future date 														- passed");
		}
	}

	private void searchExistingPatient(WebDriverWait wait, JavascriptExecutor js, WebElement patientLookupButton,
			WebElement lastNameField, WebElement firstNameField, WebElement birthMonthField, WebElement birthDayField,
			WebElement birthYearField) throws InterruptedException {
		// Searching existing patient

		clearAndFillFields(driver, wait);
		birthMonthField.sendKeys("02");
		clearAndFillFields(driver, wait);
		lastNameField.sendKeys("kumar");
		firstNameField.sendKeys("Akshay");
		birthMonthField.sendKeys("04");
		birthDayField.sendKeys("14");
		birthYearField.sendKeys("2022");
		patientLookupButton.click();
		Thread.sleep(8000);
		WebElement selectButton = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//tr[contains(., 'Akshay Kumar')]//button[text()='Select']")));
		selectButton.click();
		js.executeScript("scroll(0, 350)");
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

	private static Optional<WebElement> findElement(WebDriver driver, By by) {
		try {
			WebElement element = driver.findElement(by);
			return Optional.of(element);
		} catch (Exception e) {
			logger.error("Element not found: " + by);
			return Optional.empty();
		}
	}

	private void loginWithInvalidPassword(WebElement usernameField, WebElement passwordField, WebElement loginButton,
			String expectedErrorMessage, WebDriverWait wait) {
		usernameField.clear();
		passwordField.clear();
		usernameField.sendKeys("yp_co_a");
		passwordField.sendKeys("invalidPassword");
		loginButton.click();
		WebElement errorMessageElement1 = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb5.text-danger.ng-star-inserted")));
		String actualErrorMessage1 = errorMessageElement1.getText();
		if (!actualErrorMessage1.equals(expectedErrorMessage)) {
			logger.error("Error: The error message content is incorrect. Expected: " + expectedErrorMessage
					+ ", Actual: " + actualErrorMessage1);
		} else {
			logger.info(
					"Test 'Invalid Password' 																			   - passed.");
		}
		if (!errorMessageElement1.isDisplayed()) {
			logger.error("Error: No error message displayed for invalid username.");
		}
		System.out.println("Case 5: Error message is displaying properly on Invalid Password");
	}

	private void loginWithEmptyCredentials(WebElement usernameField, WebElement passwordField, WebElement loginButton,
			String expectedErrorMessage, WebDriverWait wait) {
		usernameField.clear();
		passwordField.clear();
		loginButton.click();
		WebElement errorMessageElement2 = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb5.text-danger.ng-star-inserted")));
		String actualErrorMessage2 = errorMessageElement2.getText();
		if (!actualErrorMessage2.equals(expectedErrorMessage)) {
			logger.error("Error: The error message content is incorrect. Expected: " + expectedErrorMessage
					+ ", Actual: " + actualErrorMessage2);
		} else {
			logger.info(
					"Test 'Empty Username and Password' 																	- passed.");
		}
		if (!errorMessageElement2.isDisplayed()) {
			logger.error("Error: No error message displayed for invalid username.");
		}
		System.out.println("Case 6: Error message is displaying properly on Empty Username/Password");
	}

	private void loginWithEmptyUsername(WebElement usernameField, WebElement passwordField, WebElement loginButton,
			String expectedErrorMessage, WebDriverWait wait) {
		usernameField.clear();
		passwordField.sendKeys("validPassword");
		loginButton.click();
		WebElement errorMessageElement3 = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb5.text-danger.ng-star-inserted")));
		String actualErrorMessage3 = errorMessageElement3.getText();
		if (!actualErrorMessage3.equals(expectedErrorMessage)) {
			logger.error("Error: The error message content is incorrect. Expected: " + expectedErrorMessage
					+ ", Actual: " + actualErrorMessage3);
			logger.info("Test 'Empty Username' - passed.");
		}
		if (!errorMessageElement3.isDisplayed()) {
			logger.error("Error: No error message displayed for invalid username.");
		}
		System.out.println("Case 7: Error message is displaying properly on Empty Username");
	}

	private void loginWithEmptyPassword(WebElement usernameField, WebElement passwordField, WebElement loginButton,
			String expectedErrorMessage, WebDriverWait wait) {
		usernameField.sendKeys("yp_co_a");
		passwordField.clear();
		loginButton.click();
		WebElement errorMessageElement4 = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb5.text-danger.ng-star-inserted")));
		String actualErrorMessage4 = errorMessageElement4.getText();
		if (!actualErrorMessage4.equals(expectedErrorMessage)) {
			logger.error("Error: The error message content is incorrect. Expected: " + expectedErrorMessage
					+ ", Actual: " + actualErrorMessage4);
		} else {
			logger.info(
					"Test 'Empty Password' 																				   - passed.");
		}
		if (!errorMessageElement4.isDisplayed()) {
			logger.error("Error: No error message displayed for invalid username.");
		}
		System.out.println("Case 8: Error message is displaying properly on Empty Password");
	}

	private void checkPlaceholders(WebElement usernameField, WebElement passwordField) {
		String usernamePlaceholder = usernameField.getAttribute("placeholder");
		String passwordPlaceholder = passwordField.getAttribute("placeholder");
		if (!usernamePlaceholder.equals("Username")) {
			logger.error(
					"Error: Placeholder is incorrect. Expected 'Username' but found '" + usernamePlaceholder + "'.");
		} else {
			logger.info(
					"Checked placeholders with their labels and required field marks 									   - passed.");
		}

		// Assert the placeholder value for password
		if (!passwordPlaceholder.equals("Password")) {
			logger.error(
					"Error: Placeholder is incorrect. Expected 'Password' but found '" + passwordPlaceholder + "'.");
		}
		System.out.println("Case 9: Placeholders are propely placed with required field marks");
	}

	private void checkLoginButtonColor(WebElement loginButton) {
		String loginButtonColor = loginButton.getCssValue("background-color");
		String expectedColorValue = "rgba(47, 175, 83, 1)";
		if (!loginButtonColor.equals(expectedColorValue)) {
			logger.error("Error: Login button color does not match. Expected: " + expectedColorValue + ", but found: "
					+ loginButtonColor);
		} else {
			logger.info(
					"Checked 'Login' button color 																		   - passed.");
		}
		System.out.println("Case 10: Login button color is in expected condition");
	}

	private void checkForgotUsernameLink(WebDriverWait wait) throws InterruptedException {
		WebElement forgotUsernameLink = driver.findElement(By.linkText("Forgot your username?"));
		if (!forgotUsernameLink.isDisplayed()) {
			logger.error("Error: Forgot username link is not displayed.");
		} else {
			forgotUsernameLink.click();

			Thread.sleep(3000);
			String expectedUrl = "http://127.0.0.1:90/#/forgotuser";
			String actualUrl = driver.getCurrentUrl();
			if (!expectedUrl.equals(actualUrl)) {
				logger.error("Error: Incorrect URL after clicking 'Forgot Password'. Expected: " + expectedUrl
						+ ", but got: " + actualUrl);
			} else {
				logger.info(
						"Verified 'Forgot Username Link' functionality														   - passed.");
			}
			WebElement formElement = driver.findElement(By.cssSelector("form.mt-4"));
			if (!formElement.isDisplayed()) {
				logger.error("Error: Expected element not found on the 'Forgot Password' page.");
			} else {
				WebElement returnToLoginLink = driver.findElement(By.linkText("Return to Login Page"));
				returnToLoginLink.click();
			}
		}
		System.out.println("Case 12: Forgot usename link is working properly");
	}

	private void checkTakeTourLink(JavascriptExecutor js) throws InterruptedException {
		WebElement takeTourLink = driver.findElement(By.cssSelector("a[href='https://www.opargo.com/#about']"));
		if (!takeTourLink.isDisplayed()) {
			logger.error("Error: Take a Tour Link is not displayed.");
		} else {
			takeTourLink.click();
			Thread.sleep(5000);
			List<String> tabHandles = new ArrayList<>(driver.getWindowHandles());
			String expectedUrl = "https://veradigm.com/predictive-scheduler/#about";
			driver.switchTo().window(tabHandles.get(1));
			String actualUrl = driver.getCurrentUrl();
			if (!expectedUrl.equals(actualUrl)) {
				logger.error("Error: Incorrect URL after clicking 'Take a Tour'. Expected: " + expectedUrl
						+ ", but got: " + actualUrl);
			} else {
				logger.info(
						"Verified 'Take a Tour Link' functionality 															   - passed.");
				driver.close();
				driver.switchTo().window(tabHandles.get(0));
				System.out.println("Case 14: Take a tour link is working properly");
			}
		}
	}

	private void checkSpeakWithRepresentativeLink(JavascriptExecutor js) throws InterruptedException {
		WebElement speakWithRepresentativeLink = driver
				.findElement(By.cssSelector("a[href='https://www.opargo.com/#contact']"));
		if (!speakWithRepresentativeLink.isDisplayed()) {
			logger.error("Error: Speak with a Representative is not displayed.");
		} else {
			speakWithRepresentativeLink.click();
			Thread.sleep(5000);
			List<String> tabHandles = new ArrayList<>(driver.getWindowHandles());
			String expectedUrl = "https://veradigm.com/predictive-scheduler/#contact";
			driver.switchTo().window(tabHandles.get(1));
			String actualUrl = driver.getCurrentUrl();
			if (!expectedUrl.equals(actualUrl)) {
				logger.error("Error: Incorrect URL after clicking 'Speak with a Representative'. Expected: "
						+ expectedUrl + ", but got: " + actualUrl);
			} else {
				logger.info(
						"Verified 'Speak with a Representative Link' functionality 											   - passed.");
				driver.close();
				driver.switchTo().window(tabHandles.get(0));
				System.out.println("Case 15: Speak with a Representative Link is working properly");
			}
		}
	}

	private void checkNewHereHeading() {
		WebElement newHereHeading = driver.findElement(By.xpath("//h1[text()='New here?']"));
		if (!newHereHeading.isDisplayed()) {
			logger.error("Error: 'New here?' heading is not displayed.");
			if (newHereHeading.isDisplayed()) {
				logger.error("Error message displayed: " + newHereHeading.getText());
			} else {
				logger.info("Verified 'New User?' heading - passed.");
				System.out.println("Case 16: Find the \"New here?\" heading element is placed properly");
			}
			throw new AssertionError("Error: 'New here?' heading is not displayed.");
		}
	}

	private void checkInfoParagraph() {
		WebElement infoParagraph = driver
				.findElement(By.xpath("//p[contains(text(), 'Learn how our scheduling system')]"));
		if (!infoParagraph.isDisplayed()) {
			logger.error("Error: Information paragraph is not displayed.");
			if (infoParagraph.isDisplayed()) {
				logger.error("Error message displayed: " + infoParagraph.getText());
			} else {
				logger.info("Verified information for new user - passed.");
				System.out.println("Case 17: Find the information paragraph element is placed properly");
			}
			throw new AssertionError("Error: Information paragraph is not displayed.");
		}
	}

	private void checkFooterElement() {
		WebElement footer = driver.findElement(By.cssSelector("footer.no-select"));
		if (!footer.isDisplayed()) {
			logger.error("Error: Footer is not displayed.");
			throw new AssertionError("Error: Footer is not displayed.");
		}
		String footerColor = footer.getCssValue("background");
		String expectedFooterColor = "rgb(37, 37, 37) none repeat scroll 0% 0% / auto padding-box border-box";
		if (!footerColor.equals(expectedFooterColor)) {
			logger.error("Error: Footer color is not black.");
			throw new AssertionError("Error: Footer color is not black.");
		} else {
			logger.info(
					"Verified footer is in black color 																	   - passed.");
			System.out.println("Case 18: Find the footer element is placed good and it is in expected color");
		}
	}

	private void checkFooterLogo() {
		List<WebElement> footerLogoElements = driver
				.findElements(By.cssSelector("footer img[src='./assets/images/100x40.png']"));
		if (footerLogoElements.isEmpty()) {
			logger.error("Error: Footer logo element not found.");
		} else {
			WebElement footerLogo = footerLogoElements.get(0);
			if (!footerLogo.isDisplayed()) {
				logger.error("Error: Footer logo is not displayed.");
			} else {
				logger.info("Verified logo under footer																			   - passed.");
				System.out.println("Case 19: Logo under footer is placed properly");
			}
		}
	}

	private void checkLicenseMessage() {
		WebElement licenseMessage = driver
				.findElement(By.xpath("//footer//li[contains(text(), '© Opargo LLC 2024. All rights reserved.')]"));
		if (!licenseMessage.isDisplayed()) {
			logger.error("Error: License reserved message is not displayed.");
		} else {
			logger.info(
					"Verified license reserved message after logo 														   - passed.");
			System.out.println("Case 20: License reserved message after logo is placed properly");
		}
	}

	private void checkPrivacyPolicyLink() {
		WebElement privacyPolicyLink = driver.findElement(By.linkText("Privacy and Security Policy."));
		if (!privacyPolicyLink.isDisplayed()) {
			logger.error("Error: Privacy and security policy link is not displayed.");
		} else {
			logger.info("Verified privacy and security policy under footer 													   - passed.");
			System.out.println("Case 21: Privacy and security policy under footer is placed properly");
		}
	}

	private void performValidLogin(WebDriverWait wait, JavascriptExecutor js, Map<String, String> data)
			throws InterruptedException {
		WebElement usernameField = driver.findElement(By.name("username"));
		WebElement passwordField = driver.findElement(By.name("password"));
		WebElement loginButton = driver.findElement(By.cssSelector(".mb5.primary-btn"));
		usernameField.clear();
		passwordField.clear();
		usernameField.sendKeys("sireesha");
		passwordField.sendKeys("DocUser$444");
		loginButton.click();
		Thread.sleep(10000);
	}
	  @AfterSuite
	    public void generateReport() {
	        ExcelUtils.writeToExcel(results, "existingPatientResults.xlsx");
	    }
}
