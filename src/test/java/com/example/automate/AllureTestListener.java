package com.example.automate;

import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class AllureTestListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = ((ExistingPatientLogin) result.getInstance()).getDriver();
        AllureUtils.takeScreenshot(driver, "Test Failed");
        AllureUtils.addTextAttachment("Error", result.getThrowable().getMessage());
    }
}
