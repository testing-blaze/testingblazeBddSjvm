package com.automation.ryder.controller.actionshub.coreActions.elementfunctions;

import com.automation.ryder.controller.browsingdevices.BrowsingDeviceBucket;
import com.automation.ryder.controller.configuration.DeviceDriver;
import com.automation.ryder.controller.configuration.EnvironmentFactory;
import com.automation.ryder.controller.exception.RyderFrameworkException;
import com.automation.ryder.controller.reports.LogLevel;
import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.ByAngular.Factory;
import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Handles angular specific locators
 *
 * @author nauman.shahid
 */
public final class Ng {
    private NgWebDriver ngDriver;
    private WebDriver driver;
    private DeviceDriver device;

    public Ng(BrowsingDeviceBucket device) {
        this.device=device;
        driver = device.getDriver();
        if (!(EnvironmentFactory.getBrowsingDevice().equalsIgnoreCase("android") || EnvironmentFactory.getBrowsingDevice().equalsIgnoreCase("ios"))) {
            this.ngDriver = new NgWebDriver((JavascriptExecutor) driver);
            this.ngDriver.waitForAngularRequestsToFinish();
        }
    }

    /**
     * @param rootSelector
     * @return Angular control to receive a locator
     */
    public Factory rootSelector(String rootSelector) {
        return ByAngular.withRootSelector(rootSelector);
    }

    /**
     * Access angular based attributes and element
     *
     * @param locator MODEL,BINDING,BUTTON TEXT,
     * @return
     */
    public WebElement getNgElement(ByAngular.BaseBy locator,Boolean processing) {
        return driver.findElement(locator);
    }

    /**
     * Access angular based attributes and element
     *
     * @param locator MODEL,BINDING,BUTTON TEXT,
     * @return
     */
    public List<WebElement> getNgElements(ByAngular.BaseBy locator,Boolean processing) {
        List<WebElement> fetchElement = driver.findElements(locator);
        if(fetchElement == null) {
            throw new RyderFrameworkException("Angular element is not present or incorrect: " + locator);
        }
        return fetchElement;
    }

}
