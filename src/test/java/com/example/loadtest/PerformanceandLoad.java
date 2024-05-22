package com.example.loadtest;

import org.testng.annotations.Test;

public class PerformanceandLoad {
	 @Test(invocationCount = 4, threadPoolSize = 3)
     public void loadTesting() {
  System.out.printf("Thread Id : %s%n", Thread.currentThread().getId());
 }

}
