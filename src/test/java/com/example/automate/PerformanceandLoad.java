package com.example.automate;

import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerformanceandLoad {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceandLoad.class);

    @Test(invocationCount = 4, threadPoolSize = 3)
    public void loadTesting() {
        long threadId = Thread.currentThread().getId();
        logger.info("Thread Id: {}", threadId);
        System.out.printf("Thread Id : %s%n", threadId);
    }
}
