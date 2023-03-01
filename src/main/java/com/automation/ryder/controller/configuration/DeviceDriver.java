package com.automation.ryder.controller.configuration;

import org.openqa.selenium.WebDriver;

/**
 *
 * @author nauman.shahid
 * Interface for device/browser instances. The instance will be singleton and managed by DI.
 */

public interface DeviceDriver {

    WebDriver getDriver();

    void setupController();

    void stopServiceProvider();

}
