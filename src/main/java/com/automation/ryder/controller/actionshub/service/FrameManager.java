package com.automation.ryder.controller.actionshub.service;

import com.automation.ryder.controller.actionshub.coreActions.elementfunctions.Waits;
import com.automation.ryder.controller.browsingdevices.BrowsingDeviceBucket;
import com.automation.ryder.controller.configuration.DeviceDriver;
import com.automation.ryder.controller.reports.LogLevel;
import com.automation.ryder.controller.reports.ReportController;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class FrameManager {
    private final By IFRAME = By.xpath("//iframe[@src or @ng-src]");
    private By locator;
    private String switchedFrameInfo = "No information available";
    public static String lastSuccessInfo = "Parent Frame";
    public static Boolean isFrameSwitchStatusSuccess = false;
    public static int frameSwitchCount = 0;
    private DeviceDriver device;
    private ReportController reportController;

    public FrameManager(BrowsingDeviceBucket device,ReportController reportController) {
        this.device=device;
        this.reportController=reportController;
    }

    public void setUpLocator(By locator) {
        this.locator = locator;
    }

    public void evaluatePossibleIFrameToSwitch() {
        makeThreadSleep(1000);
        if (device.getDriver().findElements(IFRAME).size() > 0) {
            manageSwitching();
        }
        if (!isFrameSwitchStatusSuccess && (frameSwitchCount > 0)) {
            reverseFrameSwitching();
        }
    }

    private void manageSwitching() {
        for (WebElement iframes : device.getDriver().findElements(IFRAME)) {
            switchedFrameInfo = getFrameId(iframes, "id");
            switchToFrame(iframes);
            frameSwitchCount = frameSwitchCount + 1;
            if (device.getDriver().findElements(locator).size() > 0) {
                lastSuccessInfo = switchedFrameInfo;
                isFrameSwitchStatusSuccess = true;
                reportController.write(LogLevel.INFO, String.format("Auto-Switching to iframe with id '%s'", lastSuccessInfo));
                break;
            } else if (device.getDriver().findElements(IFRAME).size() > 0) {
                if (manageInternalSwitching(switchedFrameInfo)) break;
            } else {
                switchToParentFrame();
            }
        }
    }

    private void reverseFrameSwitching() {
        int localFrameSwitchCount = frameSwitchCount;
        for (int i = 0; i < frameSwitchCount; i++) {
            switchToParentFrame();
            localFrameSwitchCount = localFrameSwitchCount - 1;
            if (device.getDriver().findElements(locator).size() > 0) {
                break;
            }
        }
        frameSwitchCount = localFrameSwitchCount;
    }

    private Boolean manageInternalSwitching(String switchFrame) {
        Boolean flag = false;
        for (WebElement iframes : device.getDriver().findElements(IFRAME)) {
            switchedFrameInfo = getFrameId(iframes, "id");
            switchToFrame(iframes);
            frameSwitchCount = frameSwitchCount + 1;
            if (device.getDriver().findElements(locator).size() > 0) {
                lastSuccessInfo = switchedFrameInfo;
                isFrameSwitchStatusSuccess = true;
                flag = true;
                reportController.write(LogLevel.INFO, String.format("Auto-Switching to nested iframe with id '%s'", lastSuccessInfo));
                break;
            } else if (device.getDriver().findElements(IFRAME).size() > 0) {
                manageInternalSwitching(switchFrame);
            } else {
                switchToParentFrame();
            }
        }
        if (!flag) {
            switchToParentFrame();
        }
        return flag;
    }

    /**
     * switch between different frame
     */
    private void switchToFrame(WebElement element) {
        try {
            device.getDriver().switchTo().frame(element);
        } catch (Exception e) {

        }
    }

    /**
     * switch between different frame
     */
    private void switchToFrameId(String id) {
        try {
            device.getDriver().switchTo().frame(id);
        } catch (Exception e) {

        }
    }

    /**
     * switch back default content
     */
    private void switchToParentFrame() {
        try {
            device.getDriver().switchTo().parentFrame();
            if (frameSwitchCount > 0) frameSwitchCount = frameSwitchCount - 1;
            lastSuccessInfo = "Parent Frame";
            reportController.write(LogLevel.INFO, "Parent Context Enabled");
        } catch (Exception e) {
        }
    }

    public static String getFrameId(WebElement element, String attribute) {
        String getAttribute = "No frame info";
        try {
            getAttribute = element.getAttribute(attribute);
        } catch (Exception e) {

        }
        return getAttribute;
    }

    private void makeThreadSleep(int threadSleep) {
        try {
            //Console log  Making thread sleep for " + threadSleep + " ms");
            Thread.sleep(threadSleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
