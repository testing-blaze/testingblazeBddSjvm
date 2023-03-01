package com.automation.ryder.controller.actionshub.coreActions.elementfunctions;

import com.automation.ryder.controller.actionshub.abstracts.ElementProcessing;
import com.automation.ryder.controller.actionshub.processing.ElementProcessingController;
import com.automation.ryder.controller.actionshub.service.FrameManager;
import com.automation.ryder.controller.browsingdevices.BrowsingDeviceBucket;
import com.automation.ryder.controller.configuration.DeviceDriver;
import com.automation.ryder.controller.reports.LogLevel;
import com.automation.ryder.controller.reports.ReportController;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * @author nauman.shahid
 * Handles finding elements and returning
 */

public final class FindMyElements {
    private FrameManager iframeAnalyzer;
    private ElementProcessing elementProcessing;
    private DeviceDriver device;

    private ReportController reportController;

    public FindMyElements(BrowsingDeviceBucket device, FrameManager iframeAnalyzer, ElementProcessingController elementProcessing, ReportController reportController) {
        this.iframeAnalyzer = iframeAnalyzer;
        this.elementProcessing = elementProcessing;
        this.device = device;
        this.reportController=reportController;
    }

    /**
     * A smart getelement method which evaluated the provided locator to add up any value from properties file in the locator if the parameter is passed in locator
     * pass any parameter to be read from property file as "---filename.properties::NameOfParameter---"
     *
     * @param locator
     * @param enablePreProcessing :true if preprocessing is required else false
     * @return webElement
     */
    public WebElement getElement(By locator, Boolean enablePreProcessing) {
        iframeAnalyzer.setUpLocator(locator);
        if (enablePreProcessing) {
            try {
                return elementProcessing.forSingleElement(locator);
            } catch (Exception e) {
                reportController.write(LogLevel.ERROR, "Element is not present or locator is not correct | " + locator);
                throw e;
            }
        } else {
            try {
                return device.getDriver().findElement(locator);
            } catch (Exception e) {
                reportController.write(LogLevel.ERROR, "Element is not present or locator is not correct | " + locator);
                throw e;
            }
        }
    }

    /**
     * A smart getelement method which evaluated the provided locator to add up any value from properties file in the locator if the parameter is passed in locator
     * pass any parameter to be read from property file as "---filename.properties::NameOfParameter---"
     *
     * @param locator
     * @return webElement
     */
    public WebElement getNestedElement(WebElement element, By locator, Boolean enablePreProcessing) {
        iframeAnalyzer.setUpLocator(locator);
        if (enablePreProcessing) {
            try {
                return elementProcessing.forNestedElement(element, locator);
            } catch (Exception e) {
                reportController.write(LogLevel.ERROR, "Nested Element is not present or locator is not correct | " + locator);
                throw e;
            }
        } else {
            try {
                return element.findElement(locator);
            } catch (Exception e) {
                reportController.write(LogLevel.ERROR, "Nested Element is not present or locator is not correct | " + locator);
                throw e;
            }
        }
    }

    /**
     * A smart getelements List method which evaluated the provided locator to add up any value from properties file in the locator if the parameter is passed in locator
     * pass any parameter to be read from property file as "---filename.properties::NameOfParameter---"
     *
     * @param locator
     * @param enablePreProcessing :true if preprocessing is required else false
     * @return list of webelements
     * @author nauman.shahid
     */
    public List<WebElement> getElements(By locator, Boolean enablePreProcessing) {
        iframeAnalyzer.setUpLocator(locator);
        if (enablePreProcessing) {
            try {
                return elementProcessing.forListOfElements(locator);
            } catch (Exception e) {
                reportController.write(LogLevel.ERROR, "List of Elements is not present or locator is not correct | " + locator);
                throw e;
            }
        } else {
            try {
                return device.getDriver().findElements(locator);
            } catch (Exception e) {
                reportController.write(LogLevel.ERROR, "List of Elementss is not present or locator is not correct | " + locator);
                throw e;
            }
        }
    }

    /**
     * get a nested element : No Pre-Processing
     *
     * @param element
     * @param locator
     * @author nauman.shahid
     */
    public List<WebElement> getNestedElementList(WebElement element, By locator) {
        iframeAnalyzer.setUpLocator(locator);
        try {
            return element.findElements(locator);
        } catch (Exception e) {
            reportController.write(LogLevel.ERROR, "List of Elementss is not present or locator is not correct | " + locator);
            throw e;
        }
    }

    /**
     * A smart getDropDown method which evaluated the provided locator to add up any value from properties file in the locator if the parameter is passed in locator
     * pass any parameter to be read from property file as "---filename.properties::NameOfParameter---"
     *
     * @param locator
     * @param enablePreProcessing:true if preprocessing is required else false
     * @return dropdown
     * @author nauman.shahid
     */
    public Select getDropDown(By locator, Boolean enablePreProcessing) {
        iframeAnalyzer.setUpLocator(locator);
        if (enablePreProcessing) {
            try {
                return new Select(elementProcessing.forSingleElement(locator));
            } catch (Exception e) {
                reportController.write(LogLevel.ERROR, "Element is not present or locator is not correct | " + locator);
                throw e;
            }
        } else {
            try {
                return new Select(device.getDriver().findElement(locator));
            } catch (Exception e) {
                reportController.write(LogLevel.ERROR, "Element is not present or locator is not correct | " + locator);
                throw e;
            }
        }
    }

    /**
     * @param element
     * @return dropdown
     * @author nauman.shahid
     */
    public Select getDropDown(WebElement element) {
        try {
            return new Select(element);
        } catch (Exception e) {
            reportController.write(LogLevel.ERROR, "Element is not present or locator is not correct | " + element);
            throw e;
        }
    }
}
