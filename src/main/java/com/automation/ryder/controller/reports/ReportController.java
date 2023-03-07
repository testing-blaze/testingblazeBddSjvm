package com.automation.ryder.controller.reports;


import com.automation.ryder.controller.configuration.ScenarioController;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

public final class ReportController {
    private String mostRecentLog;
    private ScenarioController scenarioController;
    public ReportController(ScenarioController scenarioController) {
        this.scenarioController=scenarioController;
    }

    /**
     * Writes a log to the report, prefaced by a given LogLevel and ICON
     *
     * @param logLevel  The LogLevel of the written report
     * @param reportLog The message what should be written to the log
     */
    public void write(LogLevel logLevel, String reportLog) {
        if (!isDuplicateLog(reportLog)) {
            printLogs(logLevel,reportLog);
        }
    }

    /**
     * Writes a given log to the HTML report.
     */
    private void printLogs(LogLevel logLevel,String reportLog) {
        ExtentCucumberAdapter.addTestStepLog("* &nbsp;"+reportLog);
        System.out.println(logLevel.getLog()+" "+reportLog);
    }

    /**
     * Checks if a String is the same as the previous String that was passed to the method
     *
     * @param reportLog The String to check
     * @return true if the previous String passed to the method is equal to the current String, false otherwise.
     */
    private boolean isDuplicateLog(String reportLog) {
        if (mostRecentLog == null) {
            this.mostRecentLog = reportLog;
            return false;
        } else if (reportLog.equalsIgnoreCase(mostRecentLog)) {
            return true;
        } else {
            this.mostRecentLog = reportLog;
            return false;
        }
    }
}
