package com.automation.ryder.controller.reports;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import static com.automation.ryder.library.misc.ConsoleFormatter.COLOR;
import static com.automation.ryder.library.misc.ConsoleFormatter.COLOR.*;
import static com.automation.ryder.library.misc.ConsoleFormatter.setTextColor;

public enum LogLevel {
    CRITICAL(YELLOW, "[Critical]"),
    INFO(BLUE, "[Info]"),
    IMPORTANT(CYAN, "[Imp]"),
    ERROR(RED, "[Err]"),
    EMPTY_LABEL(BRIGHT_WHITE, "");

    private COLOR logColor;
    private String log;
    private static int longestLogLevel = 0;

    public String getLog() {
        return setTextColor(this.logColor, this.log + " ".repeat(getLongestLogLevel() - this.log.length() + 1));
    }

    LogLevel(COLOR logColor, String log) {
        this.logColor = logColor;
        this.log = log;
    }

    private int getLongestLogLevel() {
        if (longestLogLevel == 0) {
            for (LogLevel level : LogLevel.values()) {
                if (level.log.length() > longestLogLevel) {
                    longestLogLevel = level.log.length();
                }
            }
        }
        return longestLogLevel;
    }
}
