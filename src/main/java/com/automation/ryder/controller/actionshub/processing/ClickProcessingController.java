package com.automation.ryder.controller.actionshub.processing;


import com.automation.ryder.controller.actionshub.abstracts.ActionProcessing;
import com.automation.ryder.controller.actionshub.coreActions.webApp.Is;
import com.automation.ryder.controller.browsingdevices.BrowsingDeviceBucket;
import com.automation.ryder.controller.configuration.DeviceDriver;
import com.automation.ryder.controller.reports.LogLevel;
import com.automation.ryder.controller.reports.ReportController;

import java.util.ArrayList;
import java.util.List;

import static com.automation.ryder.library.misc.ConsoleFormatter.COLOR.GREEN;
import static com.automation.ryder.library.misc.ConsoleFormatter.setBoldText;
import static com.automation.ryder.library.misc.ConsoleFormatter.setTextColor;

/**
 * This class is designed to managed pre and post processing once click action is performed
 */
public class ClickProcessingController {
    public Is is;
    private final DeviceDriver device;
    List<String> windowHandles = new ArrayList<>();
    private ReportController reportController;

    // Change it to windows handler and use LocatorProcessing updated abstract
    public ClickProcessingController(BrowsingDeviceBucket device, Is is, ReportController reportController){
        this.is=is;
        this.device=device;
        this.reportController=reportController;
    }

    public ActionProcessing PreProcessAction = () -> {
        windowHandles.clear();
        windowHandles.addAll(getWindowsHandlers());
    };

    public ActionProcessing postProcessingAction = () -> {
        //PageLoadProcessing.documentLoad.status("before action processing");
        List<String> newWindowHandles = getWindowsHandlers();
        if (newWindowHandles.size() != windowHandles.size()) {
            for (int i = 0; i < newWindowHandles.size(); i++) {
                if (!windowHandles.contains(newWindowHandles.get(i))) {
                    switchToWindowHandler(i);
                   reportController.write(LogLevel.INFO,  setBoldText(setTextColor(GREEN, "Switched to a new Window ")));
                    break;
                }
            }
        }
    };

    /**
     * switch to window handler
     *
     * @param handlerNumber : Usually child number is "1" and parent number is "0". However,
     *                      method is dyanmic to handle any child number
     */
    private void switchToWindowHandler(int handlerNumber) {
        device.getDriver().switchTo().window(getWindowsHandlers().get(handlerNumber));
    }

    /**
     * Windows handlers
     */
    public ArrayList<String> getWindowsHandlers() {
        return new ArrayList<String>(device.getDriver().getWindowHandles());
    }
}
