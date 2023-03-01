package com.automation.ryder.library.visualTesting;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class EyeUtil {
	
	private WebDriver driver;
	
	public EyeUtil(WebDriver driver)
	
	{
		
		this.driver=driver;
	}
	
	public  void ScrollToTriggerLazyLoading(WebDriver driver) throws InterruptedException {
		long posA = 0;
		long posB = 0;
		long scrollPos = 0;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		do {
			posA = (long) js.executeScript("return window.scrollY;");
			scrollPos += 300;
			js.executeScript("window.scrollTo(0, " + scrollPos + ");");
			posB = (long) js.executeScript("return window.scrollY;");
		} while (posA != posB);
		Thread.sleep(2000);
		js.executeScript("window.scrollTo(0, 0);");
		Thread.sleep(2000);
	}

}
