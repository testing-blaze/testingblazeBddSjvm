package com.automation.ryder.library.core;

import com.automation.ryder.controller.access.GlobalVariables;
import com.automation.ryder.controller.actionshub.service.FrameManager;
import com.automation.ryder.controller.browsingdevices.BrowsingDeviceBucket;
import com.automation.ryder.controller.reports.LogLevel;
import com.automation.ryder.controller.reports.ReportController;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;


public final class SwitchTo {
    private WebDriver driver;
    private ReportController reportController;

    public SwitchTo(BrowsingDeviceBucket device, ReportController reportController) {
        this.driver = device.getDriver();
        this.reportController=reportController;
    }

    /**
     * switch between different frame
     */
    public <T> void frame(T locator) {
        WebElement element = null;
        if (locator instanceof By) {
            element = driver.findElement((By) locator);
        } else if (locator instanceof WebElement) {
            element = (WebElement) locator;
        }
        driver.switchTo().frame(element);

        try {
            FrameManager.frameSwitchCount = FrameManager.frameSwitchCount + 1;
            FrameManager.lastSuccessInfo = FrameManager.getFrameId(driver.findElement((By) locator), "id");
        } catch (Exception e) {
            reportController.write(LogLevel.ERROR, "Unable to add iframe information");
        }

        reportController.write(LogLevel.INFO, "  Switched to new frame");
    }

    /**
     * switch back to parent frame
     */
    public void parentFrame() {
        driver.switchTo().parentFrame();
        if (FrameManager.frameSwitchCount > 0) FrameManager.frameSwitchCount = FrameManager.frameSwitchCount - 1;
        FrameManager.lastSuccessInfo = "Parent Frame";
        reportController.write(LogLevel.INFO, "Switched to parent frame");
    }

    /**
     * switch back default content
     */
    public WebDriver defaultContent() {
        reportController.write(LogLevel.INFO, "Switched to default content");
        return driver.switchTo().defaultContent();
    }

    /**
     * Switching and accepting alert
     */
    public void acceptAlert() {
        try {
            if (popupPresent()
                    && !"off".equalsIgnoreCase((String) GlobalVariables.getVariable("acceptAlert"))) {
                driver.switchTo().alert().accept();
                reportController.write(LogLevel.INFO, "Alert accepted");
            }
        } catch (Exception e) {

        }
    }

    /**
     * Switching and rejecting alert
     */
    public void rejectAlert() {
        try {
            driver.switchTo().alert().dismiss();
            reportController.write(LogLevel.INFO, "Alert accepted");

        } catch (Exception e) {

        }
    }

    /**
     * Windows handlers
     */
    public ArrayList<String> getWindowsHandlers() {
        return new ArrayList<String>(driver.getWindowHandles());
    }

    /**
     * switch to window handler
     *
     * @param handlerNumber : Usually child number is "1" and parent number is "0". However,
     *                      method is dyanmic to handle any child number
     */
    public void windowHandler(int handlerNumber) {
        reportController.write(LogLevel.INFO, "Ready to Switch to window " + handlerNumber);
        driver.switchTo().window(getWindowsHandlers().get(handlerNumber));
        reportController.write(LogLevel.INFO, "Switch to new tab ");
    }

    /**
     * get text of the alert
     *
     * @return
     */
    public String getAlertText() {
        reportController.write(LogLevel.INFO, "Fectching text from alert ");
        return driver.switchTo().alert().getText();
    }

    /**
     * switch to new context or active frame
     *
     * @return
     */
    public WebElement activeContext() {
        reportController.write(LogLevel.INFO, "Switching to current active element context ");
        return driver.switchTo().activeElement();
    }
    private boolean popupPresent() {
        boolean isPresent = false;
        try {
            Alert alert1 = driver.switchTo().alert();
            if (alert1 != null) {
                isPresent = true;
            }
        } catch (Exception e) {

        }
        return isPresent;
    }


}
