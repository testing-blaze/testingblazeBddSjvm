package com.automation.ryder.controller.browsingdevices;

import com.automation.ryder.controller.configuration.DeviceDriver;
import com.automation.ryder.controller.configuration.EnvironmentFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * @author nauman.shahid
 * Handle chrome browser specific settings and create driver instance
 */

public final class ChromeManager implements DeviceDriver {
    private RemoteWebDriver driver;
    private ChromeDriverService service;
    private static Boolean disableDriverEnforcedDownloadActivity = true;

    @Override
    public void setupController() {
        if ("true".equalsIgnoreCase(System.getProperty("chromedebuglogs"))) {
            System.setProperty("webdriver.chrome.logfile", System.getProperty("user.dir") + "/target/chromedriver.log");
            System.setProperty("webdriver.chrome.verboseLogging", "true");
            //System.setProperty("webdriver.chrome.silentOutput", "true");
        }

        if(!"default".equalsIgnoreCase(EnvironmentFactory.getBrowserVersion())
                && !"default".equalsIgnoreCase(EnvironmentFactory.getDriverVersion())) {
            WebDriverManager.chromedriver()
                    .browserVersion(EnvironmentFactory.getBrowserVersion())
                    .driverVersion(EnvironmentFactory.getDriverVersion())
                    .setup();
        } else if (!"default".equalsIgnoreCase(EnvironmentFactory.getBrowserVersion())) {
            WebDriverManager.chromedriver()
                    .browserVersion(EnvironmentFactory.getBrowserVersion())
                    .setup();
        } else if (!"default".equalsIgnoreCase(EnvironmentFactory.getDriverVersion())) {
            WebDriverManager.chromedriver().useBetaVersions()
                    .driverVersion(EnvironmentFactory.getDriverVersion())
                    .setup();
        } else {
            WebDriverManager.chromedriver().useBetaVersions().setup();
        }

        service = new ChromeDriverService.Builder().usingDriverExecutable(new File(WebDriverManager.chromedriver().getBinaryPath())).usingAnyFreePort().build();
        try {
            service.start();
        } catch (IOException e) {

        }

            if ("local".equalsIgnoreCase(EnvironmentFactory.getHub())) {
                driver = new RemoteWebDriver(service.getUrl(), CapabilitiesManager.getChromeCapabilities());
                if (!EnvironmentFactory.isHeadless()) {
                    driver.manage().window().maximize();
                }
                driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
            } else {
                try {
                    driver = new RemoteWebDriver(new URL(EnvironmentFactory.getHub() + "/wd/hub"),
                            CapabilitiesManager.getChromeCapabilities());
                    driver.setFileDetector(new LocalFileDetector());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

    }

    @Override
    public WebDriver getDriver() {
        return driver;
    }

    @Override
    public void stopServiceProvider() {
        service.stop();
    }
}
