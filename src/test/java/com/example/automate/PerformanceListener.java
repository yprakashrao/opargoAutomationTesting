package com.example.automate;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerformanceListener extends TestListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceListener.class);

    @Override
    public void onTestSuccess(ITestResult tr) {
        long durationMillis = tr.getEndMillis() - tr.getStartMillis();
        logger.info("Test Method PASSED: {}, Duration: {}ms", tr.getName(), durationMillis);
        System.out.println("Test Method: " + tr.getName() + ", Duration: " + durationMillis + "ms");
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        logger.error("Test Method: {} FAILED", tr.getName());
        System.out.println("Test Method FAILED: " + tr.getName() + " FAILED");
    }

    // Implement other listener methods as needed
}
