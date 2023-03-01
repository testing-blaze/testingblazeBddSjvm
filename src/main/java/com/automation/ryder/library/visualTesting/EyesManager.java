package com.automation.ryder.library.visualTesting;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.config.Configuration;
import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import com.applitools.eyes.visualgrid.services.RunnerOptions;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import com.automation.ryder.controller.configuration.EnvironmentFactory;
import com.automation.ryder.library.core.JavaScript;
import com.automation.ryder.library.core.Properties_Logs;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;

public class EyesManager {

	private WebDriver driver;
	private static Eyes eyes;
	private static String appName;
	private String testName;
	private static Configuration config;
	public static BatchInfo batch;
	// static VisualGridRunner runner;
	static VisualGridRunner runner;
	private static boolean applitoolsExecFlag = false;
	public JavaScript js;
	public EyeUtil eutil;

	public EyesManager(JavaScript js, EyeUtil eutil) {
		this.js = js;
		this.eutil=eutil;
	}

	public void setupEyes() {

		applitoolsExecFlag = true;

		appName = "UVS US Website";

		// Initialize the Runner for your test.
		runner = new VisualGridRunner(new RunnerOptions().testConcurrency(5));
		// runner=new ClassicRunner();
		runner.setDontCloseBatches(true);

		// Initialize the eyes SDK
		eyes = new Eyes(runner);

		config = new Configuration();

		// You can get your api key from the Applitools dashboard

		try {
			config.setApiKey(new Properties_Logs().ReadPropertyFile("config.properties", "APPLITOOLAPIKEY"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		// config.addDeviceEmulation(DeviceName.iPad, ScreenOrientation.PORTRAIT);
		config.setBatch(batch);
		eyes.setConfiguration(config);

	}

	public void setDesktopBrowser(String browser) {

		if (applitoolsExecFlag) {

			String brwslist[] = browser.split(",");

			for (String bType : brwslist) {

				String browserType = EnvironmentFactory.getBrowsingDevice();
				switch (browserType) {

				case "CHROME":
					config.addBrowser(1920, 947, BrowserType.CHROME);

					break;

				case "FIREFOX":
					config.addBrowser(1920, 947, BrowserType.FIREFOX);
					break;

				case "EDGE":
					config.addBrowser(1920, 947, BrowserType.EDGE_CHROMIUM);
					break;

				default:
					System.out.println("Default browser type would be used");
					break;

				}

			}

//		config.addDeviceEmulation(DeviceName.iPad, ScreenOrientation.PORTRAIT);
			eyes.setConfiguration(config);
		}

	}

	public static void setBatch() {

		batch = new BatchInfo("UVS Test Automation Suite");

	}

	public void setBatchName(String batchName) {

		eyes.setBatch(new BatchInfo(batchName));
	}

	public void setTestName(String testName) {

		this.testName = testName;
	}

	public void validateWindow(String stepName)  {

		if (applitoolsExecFlag) {

			eyes.open(driver, appName, testName);
			eyes.checkWindow(stepName, false);
		}

	}

	public void validateFullViewPagewithLazyLoading(String stepName) throws InterruptedException {

		if (applitoolsExecFlag) {

			// js.waitForPageLoad(driver);
			eutil.ScrollToTriggerLazyLoading(driver);
			eyes.open(driver, appName, testName);
			eyes.check(Target.window().fully().withName(stepName));
		}

	}

	public void validateFullViewPageignoreWebElements(WebElement[] elements,String stepName) throws InterruptedException {

		if (applitoolsExecFlag) {
			eutil.ScrollToTriggerLazyLoading(driver);
			eyes.open(driver, appName, testName);
			eyes.check(Target.window().ignore(elements)
					.fully().withName(stepName));			
		}
	}
	
	public void validateFullViewPageLazyLoadingigWebElements(WebElement[] elements,String stepName) throws InterruptedException {

		if (applitoolsExecFlag) {

			eutil.ScrollToTriggerLazyLoading(driver);
			eyes.open(driver, appName, testName);
			eyes.check(Target.window().ignore(elements)
					.fully().withName(stepName));
			
		}
		
		
		

	}

	public void validateFullViewPage(String stepName)  {

		if (applitoolsExecFlag) {

			eyes.open(driver, appName, testName);
			eyes.check(Target.window().fully().withName(stepName));
		}

	}

	

	public void abort() {

		try {
			eyes.closeAsync();
			eyes.abort();
		} catch (Exception e) {
			eyes.abortIfNotClosed();
		}

	}



	public void printResult() {

		if (applitoolsExecFlag) {

			EyeReport ereport = new EyeReport(runner);
			ereport.AssertResult();

		}

	}

}
