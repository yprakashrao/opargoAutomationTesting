package com.example.automate;

import java.awt.AWTException;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 1")
@Feature("Feature 1")
@Listeners(TestExecutionListener.class)
public class ExistingPatientLogin1 {

	private static final Logger logger = LoggerFactory.getLogger(ExistingPatientLogin.class);
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
	@Story("Story 1")
	@Description("Test Description")
	@Severity(SeverityLevel.CRITICAL)
	public void loginPage() throws InterruptedException, IOException, FindFailed, AWTException {

		long startTime = System.currentTimeMillis();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(300));
		driver.get("http://127.0.0.1:90/#/login");

		ExcelUtils excel = new ExcelUtils(
				"C:\\workspace\\BE\\opargoAutomationTesting\\src\\resources\\test-data\\input-data.xlsx");
		Map<String, String> data = excel.getRowData("Sheet1", 4);

//		1.logger.info("Navigating to login page.");
		driver.get("http://127.0.0.1:90/#/login");

		// 2. Opargo title and logo on the browser tab
		Assert.assertEquals(driver.getTitle(), "Opargo");

		// 3. Valid User Login
		WebElement usernameField = driver.findElement(By.name("username"));
		WebElement passwordField = driver.findElement(By.name("password"));
		WebElement loginButton = driver.findElement(By.cssSelector(".mb5.primary-btn"));
		String expectedErrorMessage = "Invalid username/password.";
//        // 4. Invalid Username
		try {
			usernameField.clear();
			passwordField.clear();
			usernameField.sendKeys("invalidUsername");
			passwordField.sendKeys("DocUser$444");
			loginButton.click();
			WebElement errorMessageElement = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb5.text-danger.ng-star-inserted")));
			String actualErrorMessage = errorMessageElement.getText();
			try {
				if (!actualErrorMessage.equals(expectedErrorMessage)) {
					throw new Exception("Error message content mismatch");
				}
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

		// 5. Invalid Password
		usernameField.clear();
		passwordField.clear();
		usernameField.sendKeys("sireesha");
		passwordField.sendKeys("invalidPassword");
		loginButton.click();
		WebElement errorMessageElement1 = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb5.text-danger.ng-star-inserted")));
		String actualErrorMessage1 = errorMessageElement1.getText();
		if (!actualErrorMessage1.equals(expectedErrorMessage)) {
			logger.error("Error: The error message content is incorrect. Expected: " + expectedErrorMessage
					+ ", Actual: " + actualErrorMessage1);
		}
		if (!errorMessageElement1.isDisplayed()) {
			logger.error("Error: No error message displayed for invalid username.");
		}
		System.out.println("Case 5");

//        // 6. Empty Username and Password
		usernameField.clear();
		passwordField.clear();
		loginButton.click();
		WebElement errorMessageElement2 = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb5.text-danger.ng-star-inserted")));
		String actualErrorMessage2 = errorMessageElement2.getText();
		if (!actualErrorMessage2.equals(expectedErrorMessage)) {
			logger.error("Error: The error message content is incorrect. Expected: " + expectedErrorMessage
					+ ", Actual: " + actualErrorMessage2);
		}
		if (!errorMessageElement2.isDisplayed()) {
			logger.error("Error: No error message displayed for invalid username.");
		}
		System.out.println("Case 6");

//        // 7. Empty Username
		usernameField.clear();
		passwordField.sendKeys("validPassword");
		loginButton.click();
		WebElement errorMessageElement3 = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb5.text-danger.ng-star-inserted")));
		String actualErrorMessage3 = errorMessageElement3.getText();
		if (!actualErrorMessage3.equals(expectedErrorMessage)) {
			logger.error("Error: The error message content is incorrect. Expected: " + expectedErrorMessage
					+ ", Actual: " + actualErrorMessage3);
		}
		if (!errorMessageElement3.isDisplayed()) {
			logger.error("Error: No error message displayed for invalid username.");
		}
		System.out.println("Case 7");

//      // 8. Empty Password
		usernameField.sendKeys("validUsername");
		passwordField.clear();
		loginButton.click();
		WebElement errorMessageElement4 = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb5.text-danger.ng-star-inserted")));
		String actualErrorMessage4 = errorMessageElement4.getText();
		if (!actualErrorMessage4.equals(expectedErrorMessage)) {
			logger.error("Error: The error message content is incorrect. Expected: " + expectedErrorMessage
					+ ", Actual: " + actualErrorMessage4);
		}
		if (!errorMessageElement4.isDisplayed()) {
			logger.error("Error: No error message displayed for invalid username.");
		}
		System.out.println("Case 8");

		// 9. Check the placeholders with their label and required field marks
		String usernamePlaceholder = usernameField.getAttribute("placeholder");
		String passwordPlaceholder = passwordField.getAttribute("placeholder");
		if (!usernamePlaceholder.equals("Username")) {
			logger.error(
					"Error: Placeholder is incorrect. Expected 'Username' but found '" + usernamePlaceholder + "'.");
		}

		// Assert the placeholder value for password
		if (!passwordPlaceholder.equals("Password")) {
			logger.error(
					"Error: Placeholder is incorrect. Expected 'Password' but found '" + passwordPlaceholder + "'.");
		}
		System.out.println("Case 9");

		// 10. Check "Login" button color
		String loginButtonColor = loginButton.getCssValue("background-color");
		String expectedColorValue = "rgba(47, 175, 83, 1)";
		if (!loginButtonColor.equals(expectedColorValue)) {
			logger.error("Error: Login button color does not match. Expected: " + expectedColorValue + ", but found: "
					+ loginButtonColor);
		}
		System.out.println("Case 10");

		// 11. Forgot Password Link
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

		// 12. Forgot Username Link
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
			}
			WebElement formElement = driver.findElement(By.cssSelector("form.mt-4"));
			if (!formElement.isDisplayed()) {
				logger.error("Error: Expected element not found on the 'Forgot Password' page.");
			} else {
				WebElement returnToLoginLink = driver.findElement(By.linkText("Return to Login Page"));
				returnToLoginLink.click();
			}
		}
		System.out.println("Case 12");

		// 14. Take a Tour Link
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
				driver.close();
				driver.switchTo().window(tabHandles.get(0));
			}
		}
		System.out.println("Case 14");

		// 15. Speak with a Representative Link
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
				driver.close();
				driver.switchTo().window(tabHandles.get(0));
			}
		}
		System.out.println("Case 15");

		// 16. Find the "New here?" heading element
		WebElement newHereHeading = driver.findElement(By.xpath("//h1[text()='New here?']"));
		if (!newHereHeading.isDisplayed()) {
			logger.error("Error: 'New here?' heading is not displayed.");
			if (newHereHeading.isDisplayed()) {
				logger.error("Error message displayed: " + newHereHeading.getText());
			}
			throw new AssertionError("Error: 'New here?' heading is not displayed.");
		}
		System.out.println("Case 16");

		// 17. Find the information paragraph element
		WebElement infoParagraph = driver
				.findElement(By.xpath("//p[contains(text(), 'Learn how our scheduling system')]"));
		if (!infoParagraph.isDisplayed()) {
			logger.error("Error: Information paragraph is not displayed.");
			if (infoParagraph.isDisplayed()) {
				logger.error("Error message displayed: " + infoParagraph.getText());
			}
			throw new AssertionError("Error: Information paragraph is not displayed.");
		}
		System.out.println("Case 17");

		// 18. Find the footer element
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
		}
		System.out.println("Case 18");

		// 19. Logo under footer
		List<WebElement> footerLogoElements = driver
				.findElements(By.cssSelector("footer img[src='./assets/images/100x40.png']"));
		if (footerLogoElements.isEmpty()) {
			logger.error("Error: Footer logo element not found.");
		} else {
			WebElement footerLogo = footerLogoElements.get(0);
			if (!footerLogo.isDisplayed()) {
				logger.error("Error: Footer logo is not displayed.");
			}
		}
		System.out.println("Case 19");

		// 20. License reserved message after logo
		WebElement licenseMessage = driver
				.findElement(By.xpath("//footer//li[contains(text(), '© Opargo LLC 2024. All rights reserved.')]"));
		licenseMessage = driver
				.findElement(By.xpath("//footer//li[contains(text(), '© Opargo LLC 2024. All rights reserved.')]"));
		if (!licenseMessage.isDisplayed()) {
			logger.error("Error: License reserved message is not displayed.");
		}
		System.out.println("Case 20");

		// 21. Privacy and security policy under footer
		WebElement privacyPolicyLink = driver.findElement(By.linkText("Privacy and Security Policy."));
		if (!privacyPolicyLink.isDisplayed()) {
			logger.error("Error: Privacy and security policy link is not displayed.");
		}
		System.out.println("Case 21");

		// 22.Valid login
		WebElement username1 = driver.findElement(By.name("username"));
		WebElement password1 = driver.findElement(By.name("password"));
		WebElement login1 = driver.findElement(By.cssSelector(".mb5.primary-btn"));
		username1.clear();
		password1.clear();
		username1.sendKeys("sireesha");
		password1.sendKeys("DocUser$444");
		login1.click();
		System.out.println("Case 22");
		String expectedUrl = "http://127.0.0.1:90/#/home";

		// 1. Verify URL change
		try {
			Thread.sleep(5000); // Replace with an appropriate wait
			String actualUrl = driver.getCurrentUrl();
			try {
				if (!expectedUrl.equals(actualUrl)) {
					throw new Exception("URL mismatch");
				} else {
					System.out.println("INFO: Login was successful, URL did  change to the home page. Expected: "
							+ expectedUrl + ", but got: " + actualUrl);
				}
				Thread.sleep(10000);
			} catch (Exception e) {
				logger.error("Error: Login was not successful, URL did not change to the home page. Expected: "
						+ expectedUrl + ", but got: " + actualUrl);
			}
		} catch (Exception e) {
			logger.error("Error during login process");
		}

//		// 2. Verify the presence of Opargo title and logo on the browser tab
//		try {
//			String expectedTitle = "Opargo";
//			try {
//				String actualTitle = driver.getTitle();
//				Assert.assertTrue(actualTitle.contains(expectedTitle),
//						"Error: Opargo title and logo are not present on the browser tab.");
//				System.out.println("2. The page title contains the expected title: ");
////               logger.info("The page title contains the expected title: " + expectedTitle);
//			} catch (AssertionError e) {
//				logger.error("Error: Opargo title and logo are not present on the browser tab.", e);
//			}
//		} catch (Exception e) {
//			// Log any other exceptions that occur
//			logger.error("An exception occurred: ", e);
//		}

		// 3. Verify the presence of Patient Information page Icon
//		try {
//			@SuppressWarnings("unchecked")
//			Optional<WebElement> iconElementOpt = findElement(driver,
//					By.xpath("//a[@href='#']/img[@src='./assets/images/practices/doctorsuneel.png']"));
//			System.out.println(iconElementOpt.isPresent());
//			if (iconElementOpt.isPresent()) {
//				WebElement iconElement = iconElementOpt.get();
//				System.out.println("3. Icon element found: " + iconElement);
//			} else {
//				logger.error("Error: Icon element not found.");
//			}
//		} catch (NoSuchElementException e) {
//			logger.error("Error: Patient Information page icon is not displayed.");
//		}

		// 4. Click on the home tab navigation link and verify
		try {
			wait.until(ExpectedConditions.urlToBe(expectedUrl));
			String actualUrl = driver.getCurrentUrl();
			Assert.assertEquals("Error: Login was not successful, URL did not change to the home page.", expectedUrl,
					actualUrl);
			logger.info("The URL changed to the expected home page URL.");
		} catch (AssertionError e) {
			String actualUrl = driver.getCurrentUrl();
			logger.error("Error: Login was not successful, URL did not change to the home page. Expected: "
					+ expectedUrl + ", but got: " + actualUrl, e);
		} catch (Exception e) {
			logger.error("Error: An issue occurred while waiting for the URL to change.");
		}
//		System.out.println("4");

		// 5. Verify the presence of the patient information page title
		try {
			WebElement patientInfoHeading = wait
					.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h2.mt5")));
			try {
				Assert.assertNotNull(patientInfoHeading, "Error: Patient Information page title is not displayed.");
				Assert.assertTrue(patientInfoHeading.isDisplayed(),
						"Error: Patient Information page title is not displayed.");
				System.out.println("5. The Patient Information page title is displayed.");
			} catch (AssertionError e) {
				logger.error("Error: Patient Information page title is not displayed.", e);
			}
		} catch (Exception e) {
			logger.error("Error: Patient Information page title is not present.", e);
		}

		// 6. Verify the presence and functionality of the "Clear Fields" button
		try {
			WebElement clearFieldsButton = wait.until(ExpectedConditions
					.presenceOfElementLocated(By.cssSelector("button.btn.btn-default.pull-right.ng-star-inserted")));
			WebElement clearFieldsIcon = clearFieldsButton
					.findElement(By.cssSelector("i.glyphicon.glyphicon-erase.v-center"));

			try {
				Assert.assertTrue(clearFieldsIcon.isDisplayed(),
						"Error: Clear fields button icon is not appropriately placed.");
				System.out.println("6. The Clear fields button icon is appropriately placed and displayed.");
			} catch (AssertionError e) {
				logger.error("Error: Clear fields button icon is not appropriately placed.", e);
			}

		} catch (Exception e) {
			logger.error("Error: Clear fields button or its icon is not present.", e);
		}

		// 7. Ensure the clear fields button icon is appropriately placed
		try {
			WebElement clearFieldsButton = wait.until(ExpectedConditions
					.presenceOfElementLocated(By.cssSelector("button.btn.btn-default.pull-right.ng-star-inserted")));
			WebElement clearFieldsIcon = clearFieldsButton
					.findElement(By.cssSelector("i.glyphicon.glyphicon-erase.v-center"));

			// Calculate the positions and sizes
			Point buttonLocation = clearFieldsButton.getLocation();
			Dimension buttonSize = clearFieldsButton.getSize();
			Point iconLocation = clearFieldsIcon.getLocation();
			Dimension iconSize = clearFieldsIcon.getSize();

			int buttonCenterX = buttonLocation.getX() + buttonSize.getWidth() / 2;
			int buttonCenterY = buttonLocation.getY() + buttonSize.getHeight() / 2;
			int iconCenterX = iconLocation.getX() + iconSize.getWidth() / 2;
			int iconCenterY = iconLocation.getY() + iconSize.getHeight() / 2;

			// Define acceptable margins for position checks
			int marginX = 50;
			int marginY = 50;
			boolean isPositionedCorrectly = Math.abs(buttonCenterX - iconCenterX) <= marginX
					&& Math.abs(buttonCenterY - iconCenterY) <= marginY;

			// Check if the icon is appropriately placed
			try {
				Assert.assertTrue(isPositionedCorrectly);
				System.out.println("7. Clear fields button icon is appropriately placed.");
			} catch (AssertionError e) {
				logger.error("Error: Clear fields button icon is not appropriately placed. Expected center: ("
						+ buttonCenterX + ", " + buttonCenterY + "), but got: (" + iconCenterX + ", " + iconCenterY
						+ ")", e);
			}

		} catch (Exception e) {
			logger.error("Error: Clear fields button or its icon is not present.", e);
		}

		// 8. Test clear fields functionality
		WebElement clearFieldsButton = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.cssSelector("button.btn.btn-default.pull-right.ng-star-inserted")));
		WebElement lastNameField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.name("patientLastName")));
		WebElement firstNameField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.name("patientFirstName")));
		WebElement middleNameField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.name("patientMiddleName")));
		WebElement birthMonthField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.name("patientBirthMonth")));
		WebElement birthDayField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.name("patientBirthDay")));
		WebElement birthYearField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.name("patientBirthYear")));
		WebElement patientLookupButton = driver
				.findElement(By.cssSelector("input[type='submit'][value='Patient Lookup'].primary-btn"));

		try {
			lastNameField.sendKeys("Test");
			firstNameField.sendKeys("Test");
			middleNameField.sendKeys("Test");
			birthMonthField.sendKeys("11");
			birthDayField.sendKeys("18");
			birthYearField.sendKeys("1994");

			// Click the Clear Fields button
			wait.until(ExpectedConditions.elementToBeClickable(clearFieldsButton));
			clearFieldsButton.click();

			// Check if fields are cleared
			try {
				boolean fieldsCleared = lastNameField.getAttribute("value").isEmpty()
						&& firstNameField.getAttribute("value").isEmpty()
						&& middleNameField.getAttribute("value").isEmpty()
						&& birthMonthField.getAttribute("value").isEmpty()
						&& birthDayField.getAttribute("value").isEmpty()
						&& birthYearField.getAttribute("value").isEmpty();
				Assert.assertTrue(fieldsCleared, "Error: 'Clear Fields' button does not clear the input fields.");
				System.out.println("8. Clear Fields button successfully cleared all fields.");
			} catch (AssertionError e) {
				logger.error("Error: 'Clear Fields' button does not clear the input fields.", e);
				throw e; // Re-throw the exception to indicate the test failure
			}

		} catch (Exception e) {
			logger.error("Error: Could not find Clear Fields button or one of the input fields.", e);
		}

		// 9. Verify functionality of the manage profile and signout dropdown
//		try {
//			WebElement profileDropdown = wait
//					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-bs-toggle='dropdown']")));
//			js.executeScript("arguments[0].click();", profileDropdown);
//
//			// Find the parent element and get the dropdown actions
//			WebElement parentElement = wait.until(ExpectedConditions
//					.presenceOfElementLocated(By.cssSelector("div.d-flex.row.forms.justify-content-end")));
//			List<WebElement> dropdownActions = parentElement.findElements(By.tagName("a"));
//
//			// Wait to ensure the dropdown actions are fully loaded
//			Thread.sleep(5000);
//
//			// Check if the dropdown actions list is empty
//			try {
//				Assert.assertFalse(dropdownActions.isEmpty(),
//						"Error: No actions available in the manage profile and signout dropdown.");
//				System.out.println("9. Dropdown actions are present.");
//				// Check if any action in the dropdown is missing a description
//				for (WebElement action : dropdownActions) {
//					try {
//						Assert.assertFalse(action.getText().isEmpty(),
//								"Error: Description for an action in the dropdown is missing.");
//					} catch (AssertionError e) {
//						logger.error("Error: Description for an action in the dropdown is missing.", e);
//						throw e; // Re-throw the exception to indicate the test failure
//					}
//				}
//			} catch (AssertionError e) {
//				logger.error("Error: No actions available in the manage profile and signout dropdown.", e);
//				throw e; // Re-throw the exception to indicate the test failure
//			}
//
//		} catch (Exception e) {
//			logger.error("Error: Unable to interact with profile dropdown or find parent element.", e);
//		}

		// 10. Verify the format and color of the Patient Lookup button.
		try {
			Optional<WebElement> patientLookupButtonOpt = findElement(driver,
					By.cssSelector("input[type='submit'][value='Patient Lookup'].primary-btn"));
			patientLookupButtonOpt.ifPresentOrElse(button -> {
				String expectedButtonColor = "rgba(51, 188, 89, 1)";
				String actualButtonColor = button.getCssValue("background-color");

				try {
					assert expectedButtonColor.equals(actualButtonColor)
							: "Error: Patient Information button color is incorrect. Expected button color is "
									+ expectedButtonColor + " but the actual color is " + actualButtonColor;
					System.out.println("10. Patient Information button color is correct.");
				} catch (AssertionError e) {
					logger.error("Assertion error: ", e);
				}
			}, () -> logger.error("Error: Patient Lookup button not found."));

		} catch (Exception e) {
			logger.error("An exception occurred: ", e);
		}

		// 11. Ensure it initiates the search process effectively.
		try {
			Optional<WebElement> lastNameFieldOpt = findElement(driver, By.name("patientLastName"));
			Optional<WebElement> firstNameFieldOpt = findElement(driver, By.name("patientFirstName"));
			Optional<WebElement> middleNameFieldOpt = findElement(driver, By.name("patientMiddleName"));
			Optional<WebElement> birthMonthFieldOpt = findElement(driver, By.name("patientBirthMonth"));
			Optional<WebElement> birthDayFieldOpt = findElement(driver, By.name("patientBirthDay"));
			Optional<WebElement> birthYearFieldOpt = findElement(driver, By.name("patientBirthYear"));
			Optional<WebElement> patientLookupButtonOpt = findElement(driver,
					By.cssSelector("input[type='submit'][value='Patient Lookup'].primary-btn"));

			// Clear fields and send data
			lastNameFieldOpt.ifPresent(WebElement::clear);
			firstNameFieldOpt.ifPresent(WebElement::clear);
			middleNameFieldOpt.ifPresent(WebElement::clear);
			birthMonthFieldOpt.ifPresent(WebElement::clear);
			birthDayFieldOpt.ifPresent(WebElement::clear);
			birthYearFieldOpt.ifPresent(WebElement::clear);

			lastNameFieldOpt.ifPresent(e -> e.sendKeys("ASDF"));
			patientLookupButtonOpt.ifPresent(WebElement::click);
			Thread.sleep(10000);
			Optional<WebElement> secondaryButtonOpt = findElement(driver,
					By.cssSelector(".btn.btn-default.center-block.secondary-btn.w340"));
			secondaryButtonOpt.ifPresent(WebElement::click);
			System.out.println("11. Ensured it id initiating the search process effectively.");
		} catch (Exception e) {
			logger.error("An exception occurred: ", e);
		}

		// 12. Existing User with Complete Name and date of birth Match
		try {
			clearAndFillFields(driver);
			Optional<WebElement> patientLookupButtonOpt = findElement(driver,
					By.cssSelector("input[type='submit'][value='Patient Lookup'].primary-btn"));
			patientLookupButtonOpt.ifPresentOrElse(button -> {
				try {
					lastNameField.sendKeys("kumar");
					firstNameField.sendKeys("Akshay");
					birthMonthField.sendKeys("04");
					birthDayField.sendKeys("14");
					birthYearField.sendKeys("2022");
					button.click();
					Thread.sleep(5000); // Wait for the page to load
					logger.info("Clicked the Patient Lookup button.");

					// Wait for and click the cancel button
					Optional<WebElement> cancelButtonOpt = findElement(wait,
							By.cssSelector(".btn.btn-default.center-block.secondary-btn.w340"));
					cancelButtonOpt.ifPresentOrElse(cancelButton -> {
						cancelButton.click();
						System.out.println("12. Clicked the Cancel button.");
					}, () -> logger.error("Cancel button not found."));
				} catch (InterruptedException e) {
					logger.error("Thread was interrupted: ", e);
					Thread.currentThread().interrupt();
				}
			}, () -> logger.error("Patient Lookup button not found."));

		} catch (Exception e) {
			logger.error("An exception occurred: ", e);
		}

		// 13. Existing User with Complete Name and date of birth Match
		try {
			clearAndFillFields(driver);
			lastNameField.sendKeys("kumar");
			patientLookupButton.click();
			System.out.println("13. Clicked the Patient Lookup button after entering last name.");
			Thread.sleep(5000);
			WebElement cancelButton = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.cssSelector(".btn.btn-default.center-block.secondary-btn.w340")));
			cancelButton.click();
		} catch (Exception e) {
			logger.error("An exception occurred: ", e);
		}

		// 14. Existing User with Complete Name and date of birth Match
		try {
			clearAndFillFields(driver);
			lastNameField.sendKeys("kumar");
			firstNameField.sendKeys("Akshay");
			patientLookupButton.click();
			Thread.sleep(5000);
			WebElement Cancelbutton = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.cssSelector(".btn.btn-default.center-block.secondary-btn.w340")));
			Cancelbutton.click();
			System.out.println("Clicked the Cancel button.");
		} catch (Exception e) {
			logger.error("An exception occurred: ", e);
		}

		// 15. Existing User with Complete Name and date of birth Match
		try {
			clearAndFillFields(driver);
			lastNameField.sendKeys(data.get("patientLastName"));
			firstNameField.sendKeys(data.get("patientFirstName"));
			middleNameField.sendKeys(data.get("patientMiddleName"));
			birthMonthField.sendKeys(data.get("patientBirthMonth"));
			birthDayField.sendKeys(data.get("patientBirthDay"));
			birthYearField.sendKeys(data.get("patientBirthYear"));
			patientLookupButton.click();
			Thread.sleep(5000);
			WebElement cancelButton = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.cssSelector(".btn.btn-default.center-block.secondary-btn.w340")));
			cancelButton.click();
			System.out.println("15. Clicked the Patient Lookup button after entering patient details.");

		} catch (Exception e) {
			logger.error("An exception occurred: ", e);
		}

//    	16.Existing User with Date of Birth with Future date
//		try {
//            clearAndFillFields(driver);
//			lastNameField.sendKeys("kumar");
//			firstNameField.sendKeys("Akshay");
//			birthMonthField.sendKeys("12");
//			birthDayField.sendKeys("02");
//			birthYearField.sendKeys("2024");
//			patientLookupButton.click();
//			Thread.sleep(100);
//			WebElement errorMessage = driver.findElement(By.cssSelector("p.text-danger"));
//			String errorText = errorMessage.getText();
//			if ("Invalid Date".equals(errorText)) {
//                logger.error("Error: Invalid Date");
//            } else {
//                System.out.println("16. No error message displayed or different error message found.");
//            }
//		} catch (Exception e) {
//			logger.error("An exception occurred: ", e);
//		}

//		17. Existing User with correct name and Incorrect Date of Birth
//		try {
//			clearAndFillFields(driver);
//		lastNameField.sendKeys("kumar");
//		firstNameField.sendKeys("Akshay");
//		birthMonthField.sendKeys(data.get("patientBirthMonth"));
//		birthDayField.sendKeys(data.get("patientBirthDay"));
//		birthYearField.sendKeys(data.get("patientBirthYear"));
//		patientLookupButton.click();
//        System.out.println("17. Existing User with correct name and Incorrect Date of Birth");
//		Thread.sleep(10000);
//		List<WebElement> rows = driver.findElements(By.cssSelector("tr.mat-row.cdk-row.ng-star-inserted"));
//		if (!rows.isEmpty()) {
//			logger.error("Error: Existing User with correct name and Incorrect Date of Birth is Failed");
//		}
//		}catch(Exception e) {
//			 logger.error("An exception occurred: ", e);
//		}

//      18. Existing User with Incorrect Name Spelling
//	try {
//		clearAndFillFields(driver);
//		lastNameField.sendKeys("kumr");
//		firstNameField.sendKeys("Akshay");
//		birthMonthField.sendKeys("04");
//		birthDayField.sendKeys("14");
//		birthYearField.sendKeys("2022");
//		patientLookupButton.click();
//		Thread.sleep(5000);
//		List<WebElement> rows = driver.findElements(By.cssSelector("tr.mat-row.cdk-row.ng-star-inserted"));
//		if (!rows.isEmpty()) {
//			logger.error("Error: Existing User with correct name and Incorrect Date of Birth is Failed");
//		}}catch(Exception e) {
//			 logger.error("An exception occurred: ", e);
//		}

//	   19.     Existing Users with Multiple Matches
//	try {	
//		clearAndFillFields(driver);
//		lastNameField.sendKeys("kumar");
//		patientLookupButton.click();
//		try {
//			WebElement searchPatientField = wait
//					.until(ExpectedConditions.visibilityOfElementLocated(By.id("mat-input-1")));
//
//		} catch (Exception e) {
//			// Log an error if the element is not visible within the timeout
//			logger.error("Search Patient field is not visible after waiting.", e);
//		}}catch(Exception e) {
//			logger.error("Search Patient field is not visible after waiting.", e);
//		}
//		System.out.println("19");

//			 20. Empty Fields
//		try{clearAndFillFields(driver);
//		patientLookupButton.click();
//		try {
//			WebElement errorMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
//					"//div[contains(text(), \"Please enter a patient's first name, last name, or full date of birth to continue\")]")));
//		} catch (Exception e) {
//			logger.error("Error message is not visible after waiting.", e);
//		}}catch(Exception e) {
//			logger.error("Error message is not visible after waiting.", e);
//		}
//		System.out.println("20");
//
//		lastNameField.clear();
//		lastNameField.sendKeys("kumar");
//		WebElement searchResult = driver.findElement(By.cssSelector("search-result-selector"));
//		if (searchResult == null || !searchResult.isDisplayed()) {
//			logger.error("Error: Patient Information button does not initiate the search process effectively.");
//		}
//		System.out.println("10");

//      WebElement lastNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("patientLastName")));
		try {
			clearAndFillFields(driver);
			lastNameField.sendKeys("kumar");
			logger.debug("Patient last name entered.");
			Thread.sleep(10000);
			patientLookupButton.click();
			logger.info("Clicked patient lookup button.");
		} catch (Exception e) {

		}
		
		WebElement selectButton = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//tr[contains(., 'Kkk Kumar')]//button[text()='Select']")));
		selectButton.click();
		logger.info("Clicked select button for patient Kkk Kumar.");

//      js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
		js.executeScript("scroll(0, 350)");
//      WebElement dropdownElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='preferredProvider']")));
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

		WebElement officevisit_dropdownElement = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("select[name='officevisit']")));
		Select officevisit_dropdown = new Select(officevisit_dropdownElement);
		Thread.sleep(1000);
		officevisit_dropdown.selectByIndex(4);
		Thread.sleep(1000);
		logger.debug("Office visit time selected.");

		WebElement thirddatePickerToggle = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("(//button[@aria-label='Open calendar'])")));
		js.executeScript("arguments[0].click();", thirddatePickerToggle);
		logger.debug("Opened date picker.");

		WebElement thirddateButton1 = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='May 1, 2024']")));
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

		Thread.sleep(2000);

		WebElement findFirstAvailableButton = wait.until(ExpectedConditions.elementToBeClickable(
				By.cssSelector("input[type='button'][value='Find First Available'].primary-btn")));
		System.out.println(findFirstAvailableButton);
		Thread.sleep(1000);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", findFirstAvailableButton);
		Thread.sleep(1000);
		findFirstAvailableButton.click();
		logger.debug("First click of findFirstAvailableButton completed");
		Thread.sleep(6000);
		findFirstAvailableButton.click();
		logger.debug("Second click of findFirstAvailableButton completed");

		Thread.sleep(60000);
		logger.info("Clicked 'Find First Available' button.");

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
		}

		long endTime = System.currentTimeMillis();
		long executionTime = endTime - startTime;
		logger.info("Execution time: {} milliseconds", executionTime);
	}
	
	


	@AfterMethod
	public void tearDown() throws InterruptedException {
		Thread.sleep(2000);
		if (driver != null) {
//        	driver.quit();
			logger.info("Browser closed and driver quit.\n");
		}

	}

	private static void clearAndFillFields(WebDriver driver) {
		try {
			WebElement lastNameField = driver.findElement(By.name("patientLastName"));
			WebElement firstNameField = driver.findElement(By.name("patientFirstName"));
			WebElement middleNameField = driver.findElement(By.name("patientMiddleName"));
			WebElement birthMonthField = driver.findElement(By.name("patientBirthMonth"));
			WebElement birthDayField = driver.findElement(By.name("patientBirthDay"));
			WebElement birthYearField = driver.findElement(By.name("patientBirthYear"));

			lastNameField.clear();
			firstNameField.clear();
			middleNameField.clear();
			birthMonthField.clear();
			birthDayField.clear();
			birthYearField.clear();

			logger.info("Filled out the patient information fields.");
		} catch (Exception e) {
			logger.error("An error occurred while clearing or filling out fields: ", e);
		}
	}

	private static Optional<WebElement> findElement(WebDriver driver, By by) {
		try {
			WebElement element = driver.findElement(by);
			return Optional.of(element);
		} catch (Exception e) {
			logger.error("Element not found: " + by, e);
			return Optional.empty();
		}
	}

	private static Optional<WebElement> findElement(WebDriverWait wait, By by) {
		try {
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			return Optional.of(element);
		} catch (Exception e) {
			logger.error("Element not found: " + by, e);
			return Optional.empty();
		}
	}
}
