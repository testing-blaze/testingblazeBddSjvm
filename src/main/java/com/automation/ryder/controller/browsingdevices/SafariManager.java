package com.automation.ryder.controller.browsingdevices;

import com.automation.ryder.controller.configuration.DeviceDriver;
import com.automation.ryder.controller.configuration.EnvironmentFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariDriverService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * @author nauman.shahid
 * Handle safari browser specific settings and create driver instance
 */

public final class SafariManager implements DeviceDriver {
    RemoteWebDriver driver;

    @Override
    public void setupController() {
        SafariDriverService safariDriverService = new SafariDriverService.Builder().usingAnyFreePort().build();

        if ("local".equalsIgnoreCase(EnvironmentFactory.getHub())) {
            driver = new SafariDriver(safariDriverService, CapabilitiesManager.getSafariCapabilities());
            driver.manage().window().maximize();
            driver.manage().timeouts().pageLoadTimeout(500, TimeUnit.SECONDS);
        } else {
            try {
                driver = new RemoteWebDriver(new URL(EnvironmentFactory.getHub() + "/wd/hub"),
                        CapabilitiesManager.getSafariCapabilities());
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
