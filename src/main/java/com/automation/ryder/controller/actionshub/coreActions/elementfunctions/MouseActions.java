package com.automation.ryder.controller.actionshub.coreActions.elementfunctions;

import com.automation.ryder.controller.browsingdevices.BrowsingDeviceBucket;
import com.automation.ryder.controller.configuration.DeviceDriver;
import com.automation.ryder.controller.reports.LogLevel;
import com.automation.ryder.controller.reports.ReportController;
import com.automation.ryder.library.core.JavaScript;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;

/**
 * @author nauman.shahid

 * Handles different click options
 */

public final class MouseActions {
    private Actions actions;
    private DeviceDriver device;
    private JavaScript javascript;

    private ReportController reportController;

    public MouseActions(BrowsingDeviceBucket device, JavaScript javaScript, ReportController reportController){
        actions = new Actions(device.getDriver());
        this.javascript=javaScript;
        this.reportController=reportController;
    }

    /**
     * slides any slider by a given x and y value
     *
     * @param sliderElement the slider which is to be moved
     * @param xOffset       the amount to move the slider in the horizontal direction
     * @param yOffset       the amount to move the slider in the vertical direction
     * @author nauman.shahid
     */
    public void moveSliderByOffset(WebElement sliderElement, int xOffset, int yOffset) {
        actions.clickAndHold(sliderElement).moveByOffset(xOffset, yOffset).release().build().perform();
    }

    /**
     * clicks on desired location based on dimension
     *
     * @author nauman.shahid
     */
    public void mouseClick(int x, int y) {
        actions.moveByOffset(x, y).click().build().perform();
        reportController.write(LogLevel.INFO, "Scrolled using mouse");

    }

    /**
     * clicks on desired location on web page using webElement
     *
     * @author nauman.shahid
     */
    public void mouseClick(WebElement element) {
        actions.click(element).build().perform();
    }

    /**
     * click and hold on desired location on web page
     *
     * @param element
     * @param holdTimeSeconds
     * @author nauman.shahid
     */
    public void mouseClickAndHold(WebElement element, int holdTimeSeconds) {
        actions.clickAndHold(element).pause(holdTimeSeconds * 1000).release().build().perform();
    }

    /**
     * Double clicks on desired location on web page
     *
     * @author nauman.shahid
     */
    public void mouseDoubleClick(WebElement element) {
        actions.doubleClick(element).build().perform();
    }

    /**
     * Drag and drop on desired location on web page
     *
     * @author nauman.shahid
     */
    public void mouseDragAndDrop(WebElement elementSource, WebElement elementTarget) {
        actions.dragAndDrop(elementSource, elementTarget).build().perform();
    }

    /**
     * Drag and drop an HTML5 element on desired location on web page
     *
     * @author nauman.shahid
     */
    public void dragAndDropInHtml5(WebElement elementSource, WebElement elementTarget) {
        javascript.dragAndDropInHtml5(elementSource, elementTarget);
    }

    /**
     * Drag and drop on desired location on web page
     *
     * @author nauman.shahid
     */
    public void mouseDragAndDrop(WebElement elementSource, int xOffset,int yOffset) {
        actions.dragAndDropBy(elementSource,xOffset,yOffset).build().perform();
    }

    /**
     * Mouse right click on desired location on web page
     *
     * @author nauman.shahid
     */
    public void mouseRightClick(WebElement context) {
        actions.contextClick(context).build().perform();
    }

    /**
     * Scroll to desired location on web page
     *
     * @author nauman.shahid
     */
    public void scrollTo(WebElement elementScrollTo) {
        WebDriverWait wait = new WebDriverWait(device.getDriver(), 10);
        while (wait.pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.visibilityOf(elementScrollTo)) != null) {
            actions.keyDown(Keys.PAGE_DOWN).build().perform();
        }
        actions.keyUp(Keys.PAGE_DOWN).build().perform();
    }

    /**
     * move to desired location on web page
     *
     * @author nauman.shahid
     */
    public void moveMouseToSpecificLocation(WebElement elementScrollTo) {
        actions.moveToElement(elementScrollTo).build().perform();
    }

    /**
     * move to desired location on web page
     *
     * @author nauman.shahid
     */
    public void moveMouseToSpecificLocationSlowly(WebElement elementScrollTo) {
        actions.moveToElement(elementScrollTo).pause(Duration.ofSeconds(5)).build().perform();
    }

    /**
     * move element to desired location on web page
     *
     * @author nauman.shahid
     */
    public void moveMouseToTargetAndOffset(WebElement elementScrollTo, int xOffset, int yOffset) {
        actions.moveToElement(elementScrollTo, xOffset, yOffset).build().perform();
    }

}
