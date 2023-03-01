
package com.automation.ryder.controller.browsingdevices;

import com.automation.ryder.controller.configuration.DeviceDriver;
import com.automation.ryder.controller.configuration.EnvironmentFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * @author nauman.shahid
 * Handle firefox browser specific settings and create driver instance
 */

public final class FireFoxManager implements DeviceDriver {
    RemoteWebDriver driver;

    @Override
    public void setupController() {
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"null");

        if(!"default".equalsIgnoreCase(EnvironmentFactory.getBrowserVersion())
                && !"default".equalsIgnoreCase(EnvironmentFactory.getDriverVersion())) {
            WebDriverManager.firefoxdriver()
                    .browserVersion(EnvironmentFactory.getBrowserVersion())
                    .driverVersion(EnvironmentFactory.getDriverVersion())
                    .setup();
        } else if (!"default".equalsIgnoreCase(EnvironmentFactory.getBrowserVersion())) {
            WebDriverManager.firefoxdriver()
                    .browserVersion(EnvironmentFactory.getBrowserVersion())
                    .setup();
        } else if (!"default".equalsIgnoreCase(EnvironmentFactory.getDriverVersion())) {
            WebDriverManager.firefoxdriver().useBetaVersions()
                    .driverVersion(EnvironmentFactory.getDriverVersion())
                    .setup();
        } else {
            WebDriverManager.firefoxdriver().useBetaVersions().setup();
        }

        if ("local".equalsIgnoreCase(EnvironmentFactory.getHub())) {
            driver = new FirefoxDriver(CapabilitiesManager.getFirefoxCapabilities());
            driver.manage().window().maximize();
            driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
        } else {
            try {
                driver = new RemoteWebDriver(new URL(EnvironmentFactory.getHub() + "/wd/hub"),
                        CapabilitiesManager.getFirefoxCapabilities());
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
        // To be implemented
    }
}
