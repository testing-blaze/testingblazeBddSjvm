package com.automation.ryder.runner;

import com.automation.ryder.controller.configuration.EnvironmentFactory;
import com.automation.ryder.controller.reports.SendMail;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;

import java.io.IOException;


@CucumberOptions(features = { "classpath:features" }, glue = { "classpath:com.automation.ryder.controller.configuration",
		"classpath:com.automation.ryder.steps"}, plugin = { "pretty", "json:target/ryderAutomation.json",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}, tags = "@sample1")

/**
 * @modified: Modified for parallel run
 * @modifier: nauman.shahidx
 * @date: 01/30/2023
 */

public class RyderTestRunner extends AbstractTestNGCucumberTests {

	@Override
	@DataProvider(parallel = true)
	public Object[][] scenarios() {
		return super.scenarios();
	}

	@AfterSuite(alwaysRun = true)
	public static void sendMailwithResults() throws IOException {

		try {
			if (EnvironmentFactory.getSendMail()) {
				SendMail.sendReport();
			} else {
				System.out.println("Mail with test result not sent as flag is set to NO");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	/*
	@AfterSuite(alwaysRun = true)
	public void trial(){
		System.out.println("After suite");
	}


	@BeforeSuite(alwaysRun = true)
	public static void getEnvDetails() {
		EnvironmentFactory.setDefaultEnv();
		if(null != System.getProperty("visualtesting") && "true".equalsIgnoreCase(System.getProperty("visualtesting"))) {
			EyesAttributes.scenarioiter = new ArrayList<String>();
			EyesAttributes.scen_num = 1;
			EyesManager.setBatch();
		}
		
	}
	
	@AfterSuite(alwaysRun = true)
	public static void sendMailwithResults() throws IOException {

		try {
			System.out.println("Sendmail flag set is ==>" + SendMail.sendMailflag);
			if (SendMail.sendMailflag.equals("YES")) {
				SendMail.sendReport();
			} else {
				System.out.println("Mail with test result not sent as flag is set to NO");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}*/
	
}
