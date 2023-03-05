package com.automation.ryder.controller.actionshub.coreActions.webApp;

import com.automation.ryder.controller.actionshub.abstracts.Element;
import com.automation.ryder.controller.access.ElementAPI;
import com.automation.ryder.controller.browsingdevices.BrowsingDeviceBucket;
import com.automation.ryder.controller.configuration.DeviceDriver;
import org.openqa.selenium.*;

/**
 * @author nauman.shahid

 * checks for presence of certain elements like popup and text
 */

public final class Is {
    private Element elementApi;
    private DeviceDriver device;

    public Is(BrowsingDeviceBucket device, ElementAPI elementAPI) {
        this.device=device;
        this.elementApi = elementAPI;
    }

    /**
     * checks whether alert is present or not
     *
     * @author nauman.shahid
     */
    public boolean popupPresent() {
        boolean isPresent = false;
        try {
            Alert alert1 = device.getDriver().switchTo().alert();
            if (alert1 != null) {
                isPresent = true;
            }
        } catch (Exception e) {

        }
        return isPresent;
    }

    /**
     * checks if specific text is present in complete body of html document
     *
     * @author nauman.shahid
     */
    public boolean textPresent(String expectedText) {
        try {
            return elementApi.locator(By.tagName("body"), true).getText().contains(expectedText);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * return true if specific element is displayed on current page
     *
     * @author nauman.shahid
     */
    public <T> boolean isDisplayed(T locator) {
        return isDisplayed(locator, true);
    }

    /**
     * return true if specific element is displayed on current page
     *
     * @author nauman.shahid
     */
    public <T> boolean isDisplayed(T locator, Boolean processing) {
        return elementApi.locator(locator, processing).isDisplayed();
    }

    /**
     * return true if specific element is displayed on current page
     *
     * @author nauman.shahid
     */
    public <T> boolean isElementDisplayed(T locator) {
        return isElementDisplayed(locator, true);
    }

    /**
     * return true if specific element is displayed on current page
     *
     * @author nauman.shahid
     */
    public <T> boolean isElementDisplayed(T locator, Boolean processing) {
        return elementApi.locator(locator, processing).getRect().getWidth() != 0 && elementApi.locator(locator, processing).getRect().getHeight() != 0;
    }

    /**
     * @param locator
     * @return
     * @author nauman.shahid
     */
    public <T> boolean isEnabled(T locator) {
        return isEnabled(locator, true);
    }

    /**
     * @param locator
     * @return
     * @author nauman.shahid
     */
    public <T> boolean isEnabled(T locator, Boolean processing) {
        return elementApi.locator(locator, processing).isEnabled();
    }

    /**
     * @param locator
     * @return
     * @author nauman.shahid
     */
    public <T> boolean isSelected(T locator) {
        return isSelected(locator, true);
    }

    /**
     * @param locator
     * @return
     * @author nauman.shahid
     */
    public <T> boolean isSelected(T locator, Boolean processing) {
        return elementApi.locator(locator, processing).isSelected();
    }


    /**
     * checks for element presence dynamically
     *
     * @author nauman.shahid
     */
    public <T> boolean IsElementPresentQuick(T locator) {
        try {
            elementApi.locator(locator, false);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param element
     * @return
     * @author john.phillips
     */
    public boolean isStale(WebElement element) {
        try {
            element.isDisplayed();
            return false;
        } catch (StaleElementReferenceException e) {
            return true;
        }
    }

    /**
     * Check if element is not stale but can possibly be interacted with or not
     *
     * @param element
     * @return
     */
    public boolean isInteractable(WebElement element) {
        try {
            element.click();
            return true;
        } catch (ElementNotInteractableException interactableException) {
            return false;
        }
    }

}