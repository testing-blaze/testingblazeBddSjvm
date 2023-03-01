package com.automation.ryder.controller.actionshub.coreActions.mobile;

import com.automation.ryder.controller.browsingdevices.BrowsingDeviceBucket;
import com.automation.ryder.controller.configuration.DeviceDriver;
import com.automation.ryder.controller.configuration.EnvironmentFactory;
import com.automation.ryder.controller.exception.RyderFrameworkException;
import io.appium.java_client.android.AndroidBatteryInfo;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.WebElement;

/**
 * @author nauman.shahid

 * Handles Android specific special methods and android driver instance
 */
public final class Android {
    private static final String UI_SCROLLABLE_SCROLL_INTO_VIEW = "new UiScrollable(new UiSelector()).scrollIntoView";
    private static final String END_UI_SCROLLABLE = "\"));";
    private DeviceDriver device;

    public Android(BrowsingDeviceBucket device){
        this.device=device;
    }

    /**
     * scroll to element using resource id.
     *
     * @param resourceID
     */
    public void toScrollUsingResourceID(String resourceID) {
        driver().findElementByAndroidUIAutomator(
                UI_SCROLLABLE_SCROLL_INTO_VIEW + "(new UiSelector().resourceId(\"" + resourceID + END_UI_SCROLLABLE);
    }

    /**
     * scroll to specific text
     *
     * @param elementText
     */
    public void toScrollUsingText(String elementText) {
        driver().findElementByAndroidUIAutomator(
                UI_SCROLLABLE_SCROLL_INTO_VIEW + "(new UiSelector().text(\"" + elementText + END_UI_SCROLLABLE);
    }

    /**
     * scroll to description containing
     *
     * @param elementContent
     */
    public void toSpecialScrollToContentContains(String elementContent) {
        driver().findElementByAndroidUIAutomator(UI_SCROLLABLE_SCROLL_INTO_VIEW
                + "(new UiSelector().descriptionContains(\"" + elementContent + END_UI_SCROLLABLE);
    }

    /**
     * press a native key to perform any action
     *
     * @param androidKey
     */
    public void toPressNativeKey(AndroidKey androidKey) {
        driver().pressKey(new KeyEvent(androidKey));
        driver().getBatteryInfo();
    }

    /**
     * get battery information
     *
     * @return
     */
    public AndroidBatteryInfo toGetBatteryInfo() {
        return driver().getBatteryInfo();
    }

    public void openNotifications() {
        driver().openNotifications();
    }

    @SuppressWarnings("unchecked") // If statement ensures unchecked cast is safe
    private AndroidDriver<WebElement> driver() {
        if (!"android".equalsIgnoreCase(EnvironmentFactory.getBrowsingDevice())) {
            throw new RyderFrameworkException("In order to use 'Android' library, System parameter 'device' must be set to 'android'.\n" +
                    "Parameter 'device' was found to be '" + EnvironmentFactory.getBrowsingDevice() + "'");
        }
        return (AndroidDriver<WebElement>) device.getDriver();
    }
}
