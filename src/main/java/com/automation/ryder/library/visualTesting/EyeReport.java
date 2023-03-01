package com.automation.ryder.library.visualTesting;

import com.applitools.eyes.TestResultContainer;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import org.testng.Assert;

public class EyeReport {

	private VisualGridRunner runner;

	public EyeReport(VisualGridRunner runner) {

		this.runner = runner;

	}

	public void AssertResult()

	{
		TestResultsSummary allTestResults = runner.getAllTestResults(false);
		System.out.println(allTestResults);

		for (TestResultContainer summary : allTestResults) {
			Throwable ex = summary.getException();
			if (ex != null) {
				Assert.assertTrue(false, ex.toString());
			} else {
				TestResults result = summary.getTestResults();
				if (result == null) {
					Assert.assertTrue(false, "Issue with getting visual validations results. Please check");
				} else {
					System.out.println("Status is ===>" + result.getStatus());
					Assert.assertEquals(result.getStatus().toString().toLowerCase(), "passed",
							"Visual validations failed for the test");

				}
			}
		}
	}

	public void printUnformattedResult() {

		TestResultsSummary allTestResults = runner.getAllTestResults(false);
		System.out.println(allTestResults);

		System.out.println("**********************************************************************");

		for (TestResultContainer summary : allTestResults) {
			Throwable ex = summary.getException();
			if (ex != null) {
				System.out.printf("System error occured while checking target=>" + ex.getMessage());
			} else {
				TestResults result = summary.getTestResults();
				if (result == null) {
					System.out.printf("No test results information available");
				} else {
					System.out.printf(
							"URL = %s, \nAppName = %s, testname = %s, Browser = %s,OS = %s, viewport = %dx%d, acessibility = %s\n",
							result.getUrl(), result.getAppName(), result.getName(), result.getHostApp(),
							result.getHostOS(), result.getHostDisplaySize().getWidth(),
							result.getHostDisplaySize().getHeight(), "passedaccessibility"
					// getAccessibilityStatus(result)
					);
				}
			}
		}

	}

}
