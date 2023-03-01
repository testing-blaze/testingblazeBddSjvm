package com.automation.ryder.controller.browsingdevices;

import com.automation.ryder.controller.configuration.DeviceDriver;
import com.automation.ryder.controller.configuration.EnvironmentFactory;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author nauman.shahid
 * Initialize Android
 */

public final class AndroidManager implements DeviceDriver {
    private RemoteWebDriver driver;

    @Override
    public void setupController() {
        try {
            driver = new AndroidDriver<>(new URL(EnvironmentFactory.getHub() + "/wd/hub"),
                    CapabilitiesManager.getAndroidCapabilities());
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void stopServiceProvider() {
        // To be implemented
    }


    @Override
    public WebDriver getDriver() {
        return driver;
    }
}
