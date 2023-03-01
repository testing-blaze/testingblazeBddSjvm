package com.automation.ryder.controller.configuration;

import com.automation.ryder.controller.reports.LogLevel;
import com.automation.ryder.controller.reports.ReportController;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author nauman.shahid
 * Manages appium server initialization and ports management
 */

public final class AppiumController {
    private AppiumDriverLocalService service;
    private ReportController reportController;

    public AppiumController(ReportController reportController){
        this.reportController=reportController;
    }
    private static final int DEFAULT_PORT = System.getProperty("hub") != null ? Integer.parseInt(System.getProperty("hub").split(":")[2]) : 4723;

    /**
     * Build Appium Service Execute Appium Server
     */
    public void startServer() {
        killPort();
        service = new AppiumServiceBuilder()
                .withIPAddress(System.getProperty("hub") !=null ? System.getProperty("hub").split(":")[1].replaceAll("//", ""):"0.0.0.0")
                .usingPort(DEFAULT_PORT)
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(GeneralServerFlag.LOG_LEVEL, "error")
                .build();
        service.start();
        reportController.write(LogLevel.INFO, "Appium Server is running: " + service.isRunning());
    }

    /**
     * Stops Service
     */
    public void stopServer() {
        service.stop();
        killPort();
    }

    /**
     * default method to close the port if it is open due to last execution
     */
    private void killPort() {
        try {
            new ServerSocket(DEFAULT_PORT).close();
        } catch (IOException e) {
            reportController.write(LogLevel.INFO, "Socket closed");
        }
    }
}
