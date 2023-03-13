package com.automation.ryder.controller.configuration;

import com.automation.ryder.controller.browsingdevices.BrowsingDeviceBucket;
import com.automation.ryder.controller.reports.ReportController;
import com.automation.ryder.controller.reports.TestMetrics;
import com.automation.ryder.library.core.Convert;
import com.automation.ryder.library.misc.ScreenCapture;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.awt.*;
import java.io.IOException;


import static com.automation.ryder.library.misc.ConsoleFormatter.COLOR;
import static com.automation.ryder.library.misc.ConsoleFormatter.setTextColor;

/**
 * Class responsible for connecting dots between projects framework and
 * core framework
 * Currently implemented in calling projects
 *
 * @author nauman.shahid
 */
public final class BootStrap {
    private final DeviceDriver device;
    private ScenarioController scenarioController;
    private Convert convert;
    private ScreenCapture screenCapture;
    private DockerController dockerController;
    private AppiumController appiumController;
    private final int threadCount = System.getProperty("threads") == null ? 0 : Integer.parseInt(System.getProperty("threads"));

    /**
     * setup the launching device
     *
     * @param device
     * @author nauman.shahid
     */
    public BootStrap(BrowsingDeviceBucket device, ScenarioController scenarioController, Convert convert, ScreenCapture screenCapture, DockerController dockerController, AppiumController appiumController) {
        createLogo();
        this.device = device;
        this.scenarioController=scenarioController;
        this.convert=convert;
        this.screenCapture=screenCapture;
        this.dockerController=dockerController;
        this.appiumController=appiumController;
    }


    /**
     * Handles various pre-test execution parameters
     *
     * @throws IOException
     * @throws AWTException
     * @author nauman.shahid
     */
    @Before(order=1)
    public synchronized void initializer(Scenario scenario) throws IOException, AWTException {
        this.scenarioController.setScenario(scenario);
        if ("true".equalsIgnoreCase(System.getProperty("recordVideo"))) {
            ScreenCapture.startRecordVideo();
        }
        if ("true".equalsIgnoreCase(System.getProperty("docker"))) {
            dockerController.startDocker();
        }
        if ("android".equalsIgnoreCase(EnvironmentFactory.getBrowsingDevice()) || "ios".equalsIgnoreCase(EnvironmentFactory.getBrowsingDevice())) {
            appiumController.startServer();
            if (System.getProperty("appName")==null) {
                runBrowser();
            } else {
                runMobileApp();
            }
        } else {
            runBrowser();
        }

       scenarioController.printInitialLogs();
    }

    /**
     * Handles various post test execution parameters
     *
     * @throws IOException
     */
    @After(order=1)
    public synchronized void theEnd(Scenario scenario) throws IOException {
        TestMetrics.captureTestMetrics(scenario);
        if("api".equalsIgnoreCase(System.getProperty("device"))) return;
        if ("true".equalsIgnoreCase(System.getProperty("docker"))) {
            dockerController.stopDocker();
        }
        if ("android".equalsIgnoreCase(EnvironmentFactory.getBrowsingDevice()) || "ios".equalsIgnoreCase(EnvironmentFactory.getBrowsingDevice())) {
            mobileWrapUp();
        } else {
            browserWrapUp();
        }
    }

    /**
     * Capture screen shot to add up to cucumber report
     *
     * @author nauman.shahid
     */
    private synchronized void captureScreenshot() {
        try {
            if (System.getProperty("enableFullScreenShot") != null && "true".equalsIgnoreCase(System.getProperty("enableFullScreenShot"))) {
               scenarioController.getScenario().attach(((convert.imageToByteArray(screenCapture.captureFullScreenShot(), "PNG"))), "image/png", scenarioController.getScenario().getSourceTagNames().toString());
            } else {
                scenarioController.getScenario().attach(((TakesScreenshot) device.getDriver()).getScreenshotAs(OutputType.BYTES), "image/png", scenarioController.getScenario().getSourceTagNames().toString());
            }
        } catch (Exception e) {
            // Handles exception if thrown for any reason
        }
    }

    /**
     * @throws IOException
     * @author nauman.shahid
     */
    private synchronized void runBrowser() {
        if("api".equalsIgnoreCase(System.getProperty("device"))) return;
        device.setupController();
        device.getDriver().get(EnvironmentFactory.getEnvironmentUrl());
    }

    /**
     * @author nauman.shahid
     */
    private synchronized void runMobileApp() {
        device.setupController();
    }

    /**
     * @throws IOException
     * @author nauman.shahid
     */
    private synchronized void browserWrapUp() throws IOException {
        if("api".equalsIgnoreCase(System.getProperty("device"))) return;
        if ("true".equalsIgnoreCase(System.getProperty("recordVideo"))) {
            ScreenCapture.stopRecordVideo();
        }

        if ((scenarioController.getScenario().isFailed()) || ("true".equalsIgnoreCase(System.getProperty("enableScreenShotsForAll")))) {
            captureScreenshot();
        }

        device.getDriver().manage().deleteAllCookies();
        device.getDriver().quit();
        device.stopServiceProvider();
    }

    /**
     * @author nauman.shahid
     */
    private synchronized void mobileWrapUp() {
        captureScreenshot();
        device.getDriver().quit();
        appiumController.stopServer();
    }

    private synchronized void createLogo() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(setTextColor(COLOR.values()[i], "*".repeat(i)));
        }
        System.out.println("Ryder BDD Automation Framework");
        for (int i = 5; i >= 1; i--) {
            System.out.println(setTextColor(COLOR.values()[i], "*".repeat(i)));
        }
    }
}
