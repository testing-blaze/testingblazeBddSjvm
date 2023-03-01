package com.automation.ryder.controller.browsingdevices;

import com.automation.ryder.controller.configuration.DeviceDriver;
import org.openqa.selenium.WebDriver;

public class BrowsingDeviceBucket implements DeviceDriver {
    private DeviceDriver delegate;

    public BrowsingDeviceBucket() {
        String browserInit = null != System.getProperty("device") ? System.getProperty("device").toLowerCase(): "chrome";
        switch (browserInit) {
            case "firefox":
                delegate = new FireFoxManager();
                break;
            case "chrome":
                delegate = new ChromeManager();
                break;
            case "ie":
                delegate = new IEManager();
                break;
            case "ie-32":
                delegate = new IEManager();
                break;
            case "android":
                delegate = new AndroidManager();
                break;
            case "ios":
                delegate = new IOSManager();
                break;
            case "edge":
                delegate = new EdgeManager();
                break;
            case "edge-32":
                delegate = new EdgeManager();
                break;
            case "safari":
                delegate = new SafariManager();
                break;
            default:
                delegate = new ChromeManager();
        }
    }

    @Override
    public WebDriver getDriver() {
        return delegate.getDriver();
    }

    @Override
    public void setupController() {
        delegate.setupController();
    }

    @Override
    public void stopServiceProvider() {
        delegate.stopServiceProvider();
    }
}
