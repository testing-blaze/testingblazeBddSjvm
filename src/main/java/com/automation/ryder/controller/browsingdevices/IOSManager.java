package com.automation.ryder.controller.browsingdevices;

import com.automation.ryder.controller.configuration.DeviceDriver;
import com.automation.ryder.controller.configuration.EnvironmentFactory;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Handles launching IOS Apps
 * @author nauman.shahid
 */

public final class IOSManager implements DeviceDriver {
    private RemoteWebDriver driver;

    @Override
    public void setupController() {
        try {
            driver = new IOSDriver<>(new URL(EnvironmentFactory.getHub() + "/wd/hub"),
                    CapabilitiesManager.getIosCapabilities());
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
