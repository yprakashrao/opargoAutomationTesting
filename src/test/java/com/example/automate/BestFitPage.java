package com.example.automate;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BestFitPage {
	  private WebDriver driver;
	    private JavascriptExecutor js;
	    public BestFitPage(WebDriver driver) {
	    	this.driver = driver;
	        this.js = (JavascriptExecutor) driver;
	    }
	    public void clickScheduleButton(String rowClass, String buttonCssSelector) {
	        try {
	            String xpathExpression = "//div[contains(@class, '" + rowClass + "')]";
	            List<WebElement> elementsWithText = driver.findElements(By.xpath(xpathExpression));

	            js.executeScript("window.scrollTo(0, 0);");
	            System.out.println("Found the elements to be clicked and scrolled down: " + elementsWithText.size());
	            for (WebElement elementWithText : elementsWithText) {
	                try {
	                    // Scroll the specific element into view
	                    js.executeScript("arguments[0].scrollIntoView(true);", elementWithText);
	                    Thread.sleep(1000); // Optional wait to ensure smooth scroll

	                    // Find the "Schedule" button within the current element
	                    WebElement scheduleButton = elementWithText.findElement(By.cssSelector(buttonCssSelector));
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
	    }
}
