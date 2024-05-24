package com.example.loadtest;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerformanceListener extends TestListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceListener.class);

    @Override
    public void onTestSuccess(ITestResult tr) {
        long durationMillis = tr.getEndMillis() - tr.getStartMillis();
        logger.info("Test Method: {}, Duration: {}ms", tr.getName(), durationMillis);
        System.out.println("Test Method: " + tr.getName() + ", Duration: " + durationMillis + "ms");
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        logger.error("Test Method: {} FAILED", tr.getName());
        System.out.println("Test Method: " + tr.getName() + " FAILED");
        // Implement custom logic to handle test failure, such as capturing screenshots or logging additional information
    }

    // Implement other listener methods as needed
}
