package com.automation.ryder.controller.actionshub.abstracts;

import com.automation.ryder.controller.actionshub.coreActions.elementfunctions.Waits;
import com.automation.ryder.controller.browsingdevices.BrowsingDeviceBucket;
import com.automation.ryder.controller.configuration.DeviceDriver;
import com.automation.ryder.controller.reports.ReportController;
import com.automation.ryder.library.core.JavaScript;
import com.automation.ryder.controller.reports.LogLevel;
import org.openqa.selenium.JavascriptExecutor;

import static com.automation.ryder.library.misc.ConsoleFormatter.setBoldText;


public class PageLoadProcessing {
    double pageLaodStandardWait=10;
    private DeviceDriver device;
    private JavaScript javaScript;
    private ReportController reportController;
    private Waits waits;

   public PageLoadProcessing(BrowsingDeviceBucket device, JavaScript javaScript, ReportController reportController, Waits waits){
        this.device=device;
        this.javaScript=javaScript;
        this.reportController=reportController;
        this.waits=waits;
    }

    public void windowsLoadStatus(String message){
        JavascriptExecutor js = ((JavascriptExecutor) device.getDriver());
        String windowLoader="return window.performance.timing.loadEventEnd";
        double pageLoadWait = System.currentTimeMillis() / 1000 + pageLaodStandardWait;
        double reportStartTime = System.currentTimeMillis() / 1000.0;
        String pageLoadStatus= "No status retrieved";
        Object result = 0;
        try {
        while (pageLoadWait > System.currentTimeMillis() / 1000.0) {
            result = js.executeScript(windowLoader);
            if (result != null) {
                pageLoadStatus = "complete";
                break;
            }
        }} catch (Exception e) {

        }
        double reportEndTime = (System.currentTimeMillis() / 1000.0) - reportStartTime;
        reportController.write(LogLevel.INFO,  "Level 2 -> page Load status "+pageLoadStatus+" is: " + setBoldText(pageLoadStatus)+" in "+(Math.round(reportEndTime)*10)/10.0+" seconds");
    };

     public void documentLoadStatus(String message){
         JavascriptExecutor js = ((JavascriptExecutor) device.getDriver());
        double pageLoadWait = System.currentTimeMillis() / 1000 + pageLaodStandardWait;
        double reportStartTime = System.currentTimeMillis() / 1000.0;
        String pageLoadStatus = "No status retrieved";
        try {
        while (pageLoadWait > System.currentTimeMillis() / 1000.0) {
            pageLoadStatus = javaScript.getPageLoadStatus();
            if ("complete".equalsIgnoreCase(pageLoadStatus)) {
                break;
            } else if("Failed to get any status".equalsIgnoreCase(pageLoadStatus)) {
                waits.makeThreadSleep(200);
                break;
            }
        } } catch (Exception e) {

        }
        double reportEndTime = (System.currentTimeMillis() / 1000.0) - reportStartTime;
        reportController.write(LogLevel.INFO,  "Level 1 -> page Load status "+pageLoadStatus+" is: " + setBoldText(pageLoadStatus)+" in "+(Math.round(reportEndTime)*10)/10.0+" seconds");
    }
}
