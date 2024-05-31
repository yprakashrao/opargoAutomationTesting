package com.example.automate;

import org.testng.IExecutionListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestExecutionListener implements ITestListener, ISuiteListener, IExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(TestExecutionListener.class);

    @Override
    public void onExecutionStart() {
        logger.info("===============================================");
        logger.info("TestNG is starting the execution");
        logger.info("===============================================");
    }

    @Override
    public void onExecutionFinish() {
        logger.info("===============================================");
        logger.info("TestNG has finished the execution");
        logger.info("===============================================\n");
    }

    @Override
    public void onStart(ISuite suite) {
        logger.info("===============================================");
        logger.info("Suite '{}' is starting", suite.getName());
        logger.info("===============================================");
    }

    @Override
    public void onFinish(ISuite suite) {
        logger.info("===============================================");
        logger.info("Suite '{}' has finished", suite.getName());
        int totalTests = suite.getAllMethods().size();
        int totalPassed = suite.getResults().values().stream()
                               .flatMap(r -> r.getTestContext().getPassedTests().getAllResults().stream())
                               .mapToInt(result -> 1).sum();
        int totalFailed = suite.getResults().values().stream()
                               .flatMap(r -> r.getTestContext().getFailedTests().getAllResults().stream())
                               .mapToInt(result -> 1).sum();
        int totalSkipped = suite.getResults().values().stream()
                                .flatMap(r -> r.getTestContext().getSkippedTests().getAllResults().stream())
                                .mapToInt(result -> 1).sum();

        logger.info("Total tests run: {}, Passes: {}, Failures: {}, Skips: {}", totalTests, totalPassed, totalFailed, totalSkipped);
        logger.info("===============================================");
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Test '{}' from class '{}' is starting", result.getMethod().getMethodName(), result.getTestClass().getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test '{}' from class '{}' passed", result.getMethod().getMethodName(), result.getTestClass().getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.info("Test '{}' from class '{}' failed", result.getMethod().getMethodName(), result.getTestClass().getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.info("Test '{}' from class '{}' skipped", result.getMethod().getMethodName(), result.getTestClass().getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.info("Test '{}' from class '{}' failed but within success percentage", result.getMethod().getMethodName(), result.getTestClass().getName());
    }

    @Override
    public void onStart(ITestContext context) {
        logger.info("Test context '{}' is starting", context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("Test context '{}' has finished", context.getName());
    }
}
