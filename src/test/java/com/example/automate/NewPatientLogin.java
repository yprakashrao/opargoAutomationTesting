package com.example.automate;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
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
public class NewPatientLogin {

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
	public void loginPage() throws InterruptedException, IOException {
		long startTime = System.currentTimeMillis();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(200));

		ExcelUtils excel = new ExcelUtils(
				"C:\\workspace\\BE\\opargoAutomationTesting\\src\\resources\\test-data\\input-data.xlsx");
		Map<String, String> data = excel.getRowData("Sheet1", 2);
//		logger.info("Excel data loaded successfully.");

//		1.logger.info("Navigating to login page.");
		driver.get("http://127.0.0.1:90/#/login");

		// 2. Opargo title and logo on the browser tab
		Assert.assertEquals(driver.getTitle(), "Opargo");
//		logger.info("Page title verified: Opargo");

		// 3. Valid User Login
		WebElement usernameField = driver.findElement(By.name("username"));
		WebElement passwordField = driver.findElement(By.name("password"));
		WebElement loginButton = driver.findElement(By.cssSelector(".mb5.primary-btn"));

//        // 4. Invalid Username
		usernameField.clear();
		passwordField.clear();
		usernameField.sendKeys("invalidUsername");
		passwordField.sendKeys("DocUser$444");
		loginButton.click();
		WebElement errorMessageElement = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb5.text-danger.ng-star-inserted")));
		String expectedErrorMessage = "Invalid username/password.";
		String actualErrorMessage = errorMessageElement.getText();
		if (!actualErrorMessage.equals(expectedErrorMessage)) {
			logger.error("Error: The error message content is incorrect. Expected: " + expectedErrorMessage
					+ ", Actual: " + actualErrorMessage);
		}
		if (!errorMessageElement.isDisplayed()) {
			logger.error("Error: No error message displayed for invalid username.");
		}
		System.out.println("Case 4");

//        // 5. Invalid Password
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
					+ ", Actual: " + actualErrorMessage);
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
		String expectedColorValue = "rgba(51, 188, 89, 1)";
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

		// 18. // Find the footer element
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
		Thread.sleep(10000);
		System.out.println("Case 22");

		// 1. Verify URL change
		String expectedUrl = "http://127.0.0.1:90/#/home";
		String actualUrl = driver.getCurrentUrl();
		if (!expectedUrl.equals(actualUrl)) {
			logger.error("Error: Login was not successful, URL did not change to the home page. Expected: "
					+ expectedUrl + ", but got: " + actualUrl);
		}

		// 2. Verify the presence of Opargo title and logo on the browser tab
		String expectedTitle = "Opargo";
		if (!driver.getTitle().contains(expectedTitle)) {
			logger.error("Error: Opargo title and logo are not present on the browser tab.");
		}

		// 3. Verify the presence of Patient Information page Icon
		WebElement iconElement = driver
				.findElement(By.xpath("//a[@href='#']/img[@src='./assets/images/practices/doctorsuneel.png']"));
		System.out.println(iconElement);
		if (iconElement == null || !iconElement.isDisplayed()) {
			logger.error("Error: Patient Information page icon is not displayed.");
		}

		// 4. Click on the home tab navigation link and verify
		WebElement homeLink = driver.findElement(By.linkText("Home"));
		homeLink.click();
		if (!expectedUrl.equals(actualUrl)) {
			logger.error("Error: Login was not successful, URL did not change to the home page. Expected: "
					+ expectedUrl + ", but got: " + actualUrl);
		}

		// 5. Verify the presence of the patient information page title
		WebElement patientInfoTitle = driver.findElement(By.xpath("//h2[text()='Patient Information']"));
		if (patientInfoTitle == null || !patientInfoTitle.isDisplayed()) {
			logger.error("Error: Patient Information page title is not displayed.");
		}

		
		//6. Verify the presence and functionality of the "Clear Fields" button
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

	@AfterMethod
	public void tearDown() throws InterruptedException {
		Thread.sleep(2000);
		if (driver != null) {
//	 	            driver.quit();
		}
		logger.info("Closed the browser." + "" + "");
	}
}
