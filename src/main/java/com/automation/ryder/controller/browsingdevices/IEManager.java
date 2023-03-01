package com.automation.ryder.controller.browsingdevices;

import com.automation.ryder.controller.configuration.DeviceDriver;
import com.automation.ryder.controller.configuration.EnvironmentFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * @author nauman.shahid
 * Handle IE browser specific settings and create driver instance
 */

public final class IEManager implements DeviceDriver {
    RemoteWebDriver driver;

    @Override
    public void setupController() {
        if ("ie-32".equalsIgnoreCase(EnvironmentFactory.getBrowsingDevice())) {
            WebDriverManager.iedriver().useBetaVersions().arch32().setup();
        } else {
            WebDriverManager.iedriver().useBetaVersions().arch64().setup();
        }

        if ("local".equalsIgnoreCase(EnvironmentFactory.getHub())) {
            driver = new InternetExplorerDriver(CapabilitiesManager.getIeCapabilities());
            driver.manage().window().maximize();
            driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
        } else {
            try {
                driver = new RemoteWebDriver(new URL(EnvironmentFactory.getHub() + "/wd/hub"),
                        CapabilitiesManager.getIeCapabilities());
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
