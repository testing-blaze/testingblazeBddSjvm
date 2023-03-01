package com.automation.ryder.controller.actionshub.coreActions.mobile;

import com.automation.ryder.controller.browsingdevices.BrowsingDeviceBucket;
import com.automation.ryder.controller.configuration.DeviceDriver;
import com.automation.ryder.controller.configuration.EnvironmentFactory;
import com.automation.ryder.controller.exception.RyderFrameworkException;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebElement;

/**
 * @author nauman.shahid

 * Handles IOS specific special methods and IOS driver instance
 */
public final class IOS {
    private DeviceDriver device;

    public IOS(BrowsingDeviceBucket device){
        this.device=device;
    }

    /**
     * returns IOS specific driver instance
     *
     * @return
     */
    @SuppressWarnings("unchecked") // If statement ensures unchecked cast is safe
    public IOSDriver<WebElement> getIOSMobileDriver() {
        if (!"ios".equalsIgnoreCase(EnvironmentFactory.getBrowsingDevice())) {
            throw new RyderFrameworkException("In order to use 'IOS' library, System parameter 'device' must be set to 'ios'.\n" +
                    "Parameter 'device' was found to be '" + EnvironmentFactory.getBrowsingDevice() + "'");
        }
        return (IOSDriver<WebElement>) device.getDriver();
    }
}
