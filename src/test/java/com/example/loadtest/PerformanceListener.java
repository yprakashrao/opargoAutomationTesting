package com.example.loadtest;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class PerformanceListener extends TestListenerAdapter {
    @Override
    public void onTestSuccess(ITestResult tr) {
        long durationMillis = tr.getEndMillis() - tr.getStartMillis();
        System.out.println("Test Method: " + tr.getName() + ", Duration: " + durationMillis + "ms");
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        System.out.println("Test Method: " + tr.getName() + " FAILED");
        // Implement custom logic to handle test failure, such as capturing screenshots or logging additional information
    }

    // Implement other listener methods as needed
}
