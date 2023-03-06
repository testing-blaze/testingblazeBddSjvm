package com.automation.ryder.controller.configuration;

import com.automation.ryder.library.core.Properties_Logs;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Keep track of environment information
 *
 * @author nauman.shahid
 */
public final class EnvironmentFactory {
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    private static String projectName;
    private static Properties_Logs propertiesLogs;

    public static String getProjectName() {
        if (projectName == null) return "";
        else
            return projectName;
    }

    public static void setProjectName(String projectName) {
        EnvironmentFactory.projectName = projectName;
    }

    private static String orgName;

    public static String getOrgName() {
        if (orgName == null) return "";
        else
            return orgName;
    }

    public static void setOrgName(String orgName) {
        EnvironmentFactory.orgName = orgName;
    }


    private static String projectPath;

    public static String getProjectPath() {
        if (projectPath == null) return "";
        else
            return projectPath;
    }

    public static void setProjectPath(String projectPath) {
        EnvironmentFactory.projectPath = projectPath;
    }


    private static String environmentUrl;

    public static String getEnvironmentUrl() {
        if (environmentUrl == null) {
            try {
                environmentUrl = getProps().ReadPropertyFile("environment.properties", EnvironmentFactory.getEnvironmentName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return environmentUrl;
    }

    private static String environmentName;

    public static void setEnvironmentName(String envName) {
        environmentName = envName;
    }

    public static String getEnvironmentName() {
        if (environmentName == null) {
            environmentName = System.getProperty("env") != null ? System.getProperty("env").toUpperCase() : "QA";
        }
        return environmentName;
    }

    private static String azureServiceBusUrl;
    public static String getAzureServiceBusUrl() {
        if (azureServiceBusUrl == null) {
            try {
                azureServiceBusUrl = getProps().ReadPropertyFile("environment.properties", EnvironmentFactory.getEnvironmentName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return azureServiceBusUrl;
    }

    private static String appName;
    private static boolean checkedAppName = false;

    public static String getAppName() {
        if (!checkedAppName) {
            checkedAppName = true;
            appName = System.getProperty("appName");
        }
        return appName;
    }

    private static String hub;

    public static String getHub() {
        if (hub == null) {
            hub = System.getProperty("hub") != null ? System.getProperty("hub") : "local";
        }
        return hub;
    }

    private static String device;

    public static String getBrowsingDevice() {
        if (device == null) {
            device = System.getProperty("device") != null ? System.getProperty("device") : "chrome";
        }
        return device;
    }

    private static String browserVersion;

    public static String getBrowserVersion() {
        if (browserVersion == null) {
            if (System.getProperty("browserVersion") != null) {
                browserVersion = System.getProperty("browserVersion");
            } else if ("chrome".equalsIgnoreCase(getBrowsingDevice()) && !"default".equalsIgnoreCase(getDriverVersion())) {
                browserVersion = getDriverVersion().substring(0, getDriverVersion().indexOf("."));
            } else {
                browserVersion = "default";
            }
        }
        return browserVersion;
    }

    private static String driverVersion;

    public static String getDriverVersion() {
        if (driverVersion == null) {
            if (System.getProperty("driverVersion") != null) {
                driverVersion = System.getProperty("driverVersion");
            } else {
                driverVersion = "default";
            }
        }
        return driverVersion;
    }

    private static int maxWaitTime = 0;

    public static int getMaxWaitTime() {
        if (maxWaitTime == 0) {
            maxWaitTime = System.getProperty("waitTime") != null ? Integer.parseInt(System.getProperty("waitTime")) : 10;
        }
        return maxWaitTime;
    }

    private static String executionMode;

    public static String getExecutionMode() {
        if (executionMode == null) {
            executionMode = "true".equalsIgnoreCase(System.getProperty("headless")) ? "Headless" : "Browser UI";
        }
        return executionMode;
    }
    private static final Boolean isHeadless = "true".equalsIgnoreCase(System.getProperty("headless"));
    public static Boolean isHeadless() {
        return isHeadless;
    }

    private static Integer slowDownExecutionTime;

    public static Integer getSlowDownExecutionTime() {
        if (slowDownExecutionTime == null) {
            slowDownExecutionTime = System.getProperty("slowDownExecution") != null ? Integer.parseInt(System.getProperty("slowDownExecution")) : 0;
        }
        return slowDownExecutionTime;
    }

    private static String scenarioTag;

    public static String getScenarioTag() {
        if (scenarioTag == null) {
            if (System.getProperty("tags") != null) scenarioTag = System.getProperty("tags");
        }
        return scenarioTag;
    }

    public static String executionDate;
    public static void setExecutionDate(String exeDate) {
        executionDate = exeDate;
    }
    public static String getExecutionDate() {
        if (executionDate == null) {
            executionDate = System.getProperty("setExecutionDate") != null ? System.getProperty("setExecutionDate"): LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));;
        }
        return executionDate;
    }

    private static Boolean sendMail;
    public static Boolean getSendMail() {
        if (sendMail == null) {
            sendMail = System.getProperty("sendMail") != null && System.getProperty("sendMail").equalsIgnoreCase("yes") ? true:false;
        }
        return sendMail;
    }

    private static Properties_Logs getProps(){
        return new Properties_Logs();
    }



}
