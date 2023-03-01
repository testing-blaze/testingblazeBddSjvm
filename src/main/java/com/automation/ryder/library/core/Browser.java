package com.automation.ryder.library.core;

import com.automation.ryder.controller.browsingdevices.BrowsingDeviceBucket;
import com.automation.ryder.controller.reports.LogLevel;
import com.automation.ryder.controller.reports.ReportController;
import org.openqa.selenium.WebDriver;

public final class Browser {

    private WebDriver driver;
    private JavaScript javaScript;

    private ReportController reportController;

    public Browser(BrowsingDeviceBucket device, JavaScript javaScript, ReportController reportController) {
        this.driver = device.getDriver();
        this.javaScript = javaScript;
        this.reportController=reportController;
    }

    /**
     * close the current tab
     *
     * @author nauman.shahid
     */
    public void closeBrowserOrTab() {
        reportController.write(LogLevel.INFO, "Closed Tab " + driver.getTitle());
        driver.close();
    }

    /**
     * navigate back from current page
     *
     * @author nauman.shahid
     */
    public void navigateBack() {
        driver.navigate().back();
        reportController.write(LogLevel.INFO, "Navigated Back");
    }

    /**
     * navigate forward from current page
     *
     * @author nauman.shahid
     */
    public void navigateForward() {
        driver.navigate().forward();
        reportController.write(LogLevel.INFO, "Navigated Forward");
    }

    /**
     * navigate to a specific url
     *
     * @param url
     * @author nauman.shahid
     */
    public void navigateToUrl(String url) {
        driver.navigate().to(url);
        reportController.write(LogLevel.INFO, "Navigated to URL " + url);
    }

    /**
     * refresh page
     *
     * @author nauman.shahid
     */
    public void refreshPage() {
        driver.navigate().refresh();
        reportController.write(LogLevel.INFO, "Refreshed page");
    }

    /**
     * get the current url of web page
     *
     * @author nauman.shahid
     */
    public String getCurrentUrl() {
        reportController.write(LogLevel.INFO, "Current url is " + driver.getCurrentUrl());
        return driver.getCurrentUrl();
    }

    /**
     * @return status of the page load like Completed , loading , Interactive
     */
    public String getJQueryStatus() {
        return javaScript.getJQueryStatus();
    }

    /**
     * @return status of the page load like Completed , loading , Interactive
     */
    public String getPageLoadStatus() {
        return javaScript.getPageLoadStatus();
    }

    /**
     * get complete current page source
     *
     * @author nauman.shahid
     */
    public String getPageSource() {
        reportController.write(LogLevel.INFO, "Fetching complete page source");
        return driver.getPageSource();
    }

    /**
     * get title of current web page
     *
     * @author nauman.shahid
     */
    public String getPageTitle() {
        reportController.write(LogLevel.INFO, "Fetched title " + driver.getTitle());
        return driver.getTitle();
    }

    /**
     * Get current double value of current off set or movement of the page from original position
     *
     * @return double x-axis
     * @author nauman.shahid
     */
    public double getPageXOffSet() {
        return javaScript.getPageOffSetXAxis();
    }

    /**
     * Get current double value of current off set or movement of the page from original position
     *
     * @return double y-axis
     * @author nauman.shahid
     */
    public double getPageYOffSet() {
        return javaScript.getPageOffSetYAxis();
    }

}
