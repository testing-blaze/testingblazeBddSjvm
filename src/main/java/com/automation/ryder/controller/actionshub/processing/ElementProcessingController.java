package com.automation.ryder.controller.actionshub.processing;

import com.automation.ryder.controller.access.GlobalVariables;
import com.automation.ryder.controller.actionshub.abstracts.ElementProcessing;
import com.automation.ryder.controller.actionshub.abstracts.PageLoadProcessing;
import com.automation.ryder.controller.actionshub.service.FrameManager;
import com.automation.ryder.controller.browsingdevices.BrowsingDeviceBucket;
import com.automation.ryder.controller.configuration.DeviceDriver;
import com.automation.ryder.controller.reports.ReportController;
import com.automation.ryder.library.core.JavaScript;
import com.automation.ryder.controller.actionshub.coreActions.elementfunctions.Waits;
import com.automation.ryder.controller.configuration.EnvironmentFactory;
import com.automation.ryder.controller.exception.RyderFrameworkException;
import com.automation.ryder.controller.reports.LogLevel;
import com.automation.ryder.library.core.SwitchTo;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * This class manages an intermediate state of web element to ensure its creation and availability in it's best available context.
 */
public class ElementProcessingController implements ElementProcessing {
    private JavaScript javaScript;
    private int magicWaitRetry = 0;
    private DeviceDriver device;
    private FrameManager iframeAnalyzer;
    private PageLoadProcessing pageLoadProcessing;
    private static By processingHoldOnScreen = null;
    private static Boolean turnOnProcessingHoldOnScreen = null;
    private ReportController reportController;
    private Waits waits;
    private SwitchTo switchTo;

    public ElementProcessingController(BrowsingDeviceBucket device, JavaScript js, FrameManager iframeAnalyzer, PageLoadProcessing pageLoadProcessing, ReportController reportController, Waits waits, SwitchTo switchTo) {
        this.javaScript = js;
        this.device=device;
        this.iframeAnalyzer=iframeAnalyzer;
        this.pageLoadProcessing=pageLoadProcessing;
        this.reportController=reportController;
        this.waits=waits;
        this.switchTo=switchTo;
    }

    @Override
    public WebElement forSingleElement(By locator) {
        switchTo.acceptAlert();
        pageLoadProcessing.documentLoadStatus("for DOM ");
        pageLoadProcessing.documentLoadStatus("for script loading ");
        WebElement element = elementWaitProcessing(locator);
        GlobalVariables.setVariable("locatorInProgress", locator);
        if (!isViewPort(element)) {
            javaScript.scrollElementToPageDetailCenter(element);
        }
        reportController.write(LogLevel.INFO, "Element Processing Ends");
        CompletableFuture.supplyAsync(() -> {
            try {
                if (GlobalVariables.getVariable("highlightElements") != null && ((String) GlobalVariables.getVariable("highlightElements")).equalsIgnoreCase("off")) {
                    return element;
                } else {
                    javaScript.executeJSCommand().executeScript("arguments[0].setAttribute('style', 'background-color: #e6ffff; border: 2px solid black;');", element);
                }

            } catch (Exception e) {

            }
            return true;
        });
        return element;
    }

    @Override
    public List<WebElement> forListOfElements(By locator) {
        switchTo.acceptAlert();
        pageLoadProcessing.documentLoadStatus("for DOM ");
        pageLoadProcessing.documentLoadStatus("for script loading ");
        List<WebElement> listOfElements = listOfElementsWaitProcessing(locator);
        GlobalVariables.setVariable("locatorInProgress", "ignore");
        javaScript.scrollElementToPageDetailCenter(locator);
        if (listOfElements != null) isViewPort(listOfElements.get(0));
        reportController.write(LogLevel.INFO, "List of Elements Processing Ends");
        if (listOfElements == null)
            return new ArrayList<>();
        return listOfElements;
    }

    @Override
    public WebElement forNestedElement(WebElement element, By locator) {
        switchTo.acceptAlert();
        pageLoadProcessing.documentLoadStatus("for DOM ");
        pageLoadProcessing.documentLoadStatus("for script loading ");
        WebElement finalElement = elementWaitProcessing(element.findElement(locator));
        GlobalVariables.setVariable("locatorInProgress", "ignore");
        if (!isViewPort(element)) {
            javaScript.scrollElementToPageDetailCenter(element);
        }
        reportController.write(LogLevel.INFO, "Element Processing Ends");
        CompletableFuture.supplyAsync(() -> {
            try {
                if (GlobalVariables.getVariable("highlightElements") != null && ((String) GlobalVariables.getVariable("highlightElements")).equalsIgnoreCase("off")) {
                    return element;
                } else {
                    javaScript.executeJSCommand().executeScript("arguments[0].setAttribute('style', 'background-color: #e6ffff; border: 2px solid black;');", element);
                }

            } catch (Exception e) {

            }
            return true;
        });
        return finalElement;
    }

    private <T> List<WebElement> listOfElementsWaitProcessing(T locatorOrElement) {
        double reportStartTime = (System.currentTimeMillis() / 1000.0);
        List<WebElement> listOfElements = null;
        long elementWaitTime = waits.getWaitTime();
        while (elementWaitTime > waits.getCurrentTimeInSecs() && listOfElements == null) {
            if (getElementsForMagicWait((By) locatorOrElement).size() > 0) {
                listOfElements = getElementsForMagicWait((By) locatorOrElement);
                break;
            }
        }
        double reportEndTime = (System.currentTimeMillis() / 1000.0) - reportStartTime;
        reportController.write(LogLevel.INFO, String.format("List of Elements Presence Check Completed in %.1f seconds", reportEndTime));
        return listOfElements;
    }

    /**
     * A smart wait which will check for page load status , handles matching nodes and try to return valid node, is element is on page , stale rrtrty
     *
     * @param locatorOrElement : it can receive any type argument but is designed to handle By and WebElement types only
     * @return refined element - WebElement
     * @alert Handles matching nodes and stale retry for By type only
     * @author nauman.shahid
     */
    private <T> WebElement elementWaitProcessing(T locatorOrElement) {
        double reportStartTime = (System.currentTimeMillis() / 1000.0);
        boolean displayedFlag = true;
        boolean isElementDrawnValidated = false;

        WebElement element = null;
        if (locatorOrElement instanceof By) {
            long elementWaitTime = waits.getWaitTime();
            while (elementWaitTime > waits.getCurrentTimeInSecs() && element == null && displayedFlag) {
                for (WebElement ele : getElementsForMagicWait((By) locatorOrElement)) {
                    try {
                        if (isElementDrawn(ele)) {
                            if (ele.isDisplayed()) {
                                element = ele;
                                isElementDrawnValidated = true;
                                reportController.write(LogLevel.IMPORTANT, "Element is Displayed & Enabled on page");
                            }
                            break;
                        }
                    } catch (StaleElementReferenceException e) {
                        if (magicWaitRetry == 0) {
                            magicWaitRetry++;
                            reportController.write(LogLevel.CRITICAL, "Element is stale so re-trying one more time");
                            elementWaitProcessing(locatorOrElement);
                        } else {
                            displayedFlag = false;
                        }
                    } catch (WebDriverException noSuchWindowAndTypeError) {
                        if (magicWaitRetry == 0) {
                            magicWaitRetry++;
                            reportController.write(LogLevel.CRITICAL, "No context is available, so re-trying one more time");
                            waits.makeThreadSleep(4000);
                            elementWaitProcessing(locatorOrElement);
                        } else {
                            displayedFlag = false;
                        }
                    }
                }
            }
            displayedFlag = true;
            countMatchingNodesOnPage((By) locatorOrElement);
            if (element == null) {
                try {
                    element = getElementForMagicWait((By) locatorOrElement);
                } catch (Exception e) {
                    reportController.write(LogLevel.ERROR, "Element does not exist in DOM");
                    throw e;
                }
            }
        } else if (locatorOrElement instanceof WebElement) {
            element = (WebElement) locatorOrElement;
        }

        long elementVisibilityWaitTime = waits.getWaitTime();

        while ((elementVisibilityWaitTime > waits.getCurrentTimeInSecs() && displayedFlag) && !isElementDrawnValidated) {
            try {
                if (isElementDrawn(element)) {

                    if (element.isDisplayed()) {
                        isElementDrawnValidated = true;
                       reportController.write(LogLevel.IMPORTANT, "Element is Displayed & Enabled on page");
                    }
                    break;
                }
            } catch (StaleElementReferenceException e) {
                if (magicWaitRetry == 0) {
                    magicWaitRetry++;
                    reportController.write(LogLevel.CRITICAL, "Element is stale so re-trying one more time");
                    elementWaitProcessing(locatorOrElement);
                } else {
                    displayedFlag = false;
                }
            }
        }
        javaScript.scrollElementToPageDetailCenter(element);
        magicWaitRetry = 0;
        double reportEndTime = (System.currentTimeMillis() / 1000.0) - reportStartTime;
        reportController.write(LogLevel.INFO, String.format("Element Presence/Creation Completed in %.1f seconds", reportEndTime));
        return element;
    }

    private boolean isElementDrawn(WebElement element) {
        boolean flag = false;
        try {
            if (element.getRect().getWidth() > 2 && element.getRect().getHeight() > 2) {
                completeElementCreationOnUi(element);
                if (element.isEnabled()) {
                    flag = true;
                }
            }
        } catch (StaleElementReferenceException stale) {
            throw stale;
        } catch (Exception e) {
            //Do nothing
        }
        return flag;
    }

    private void completeElementCreationOnUi(WebElement element) {
        double waitTime = waits.getCurrentTimeInSecs() + (Waits.STANDARD_WAIT_TIME * 0.5);
        int iSize = element.getRect().getHeight() + element.getRect().getWidth();
        while (waits.getCurrentTimeInSecs() < waitTime) {
            int newSize = element.getRect().getHeight() + element.getRect().getWidth();
            if (newSize > iSize) iSize = newSize;
            else {
                reportController.write(LogLevel.IMPORTANT, "Element Creation on UI completed");
                break;
            }
        }
    }

    private void countMatchingNodesOnPage(By locator) {
        int nodes = device.getDriver().findElements(locator).size();
        if (nodes != 1) {
            reportController.write(LogLevel.CRITICAL, "Total element matching nodes in DOM --> " + nodes);
        } else {
            reportController.write(LogLevel.INFO, "Total element matching nodes in DOM --> " + nodes);
        }
    }

    private List<WebElement> getElementsForMagicWait(By locator) {
        try {
            if (device.getDriver().findElements(locator).size() > 0) {
                projectProcessingWrapper();
                return device.getDriver().findElements(locator);
            } else if (device.getDriver().findElements(locator).size() == 0) {
                iframeAnalyzer.evaluatePossibleIFrameToSwitch();
                if (FrameManager.isFrameSwitchStatusSuccess) {
                    waits.makeThreadSleep(200);
                    if (EnvironmentFactory.getSlowDownExecutionTime() > 0) {
                        waits.makeThreadSleep(1000 * EnvironmentFactory.getSlowDownExecutionTime());
                    }
                    projectProcessingWrapper();
                    try {
                        completeElementCreationOnUi(device.getDriver().findElement(By.xpath("//body")));
                    } catch (Exception e) {
                        // Handles unexpected exception for //body
                    }
                    FrameManager.isFrameSwitchStatusSuccess = false;
                }
                projectProcessingWrapper();
            }

        } catch (WebDriverException noSuchWindowAndTypeError) {
            reportController.write(LogLevel.CRITICAL, "No initial context is available to switch, so re-trying one more time");
            waits.makeThreadSleep(5000);
            device.getDriver().switchTo().defaultContent();
            reportController.write(LogLevel.INFO, "Default Context Enabled");
        } catch (StackOverflowError stackOverflowError) {
            reportController.write(LogLevel.CRITICAL, "Unable to handle frame switch");
            throw new RyderFrameworkException("Unable to handle frame switch");
        }
        return device.getDriver().findElements(locator);
    }

    private void projectProcessingWrapper() {
        if (GlobalVariables.hasVariable("processingHoldOnScreen")) {
            if (turnOnProcessingHoldOnScreen == null && processingHoldOnScreen == null) {
                processingHoldOnScreen = (By) GlobalVariables.getVariable("processingHoldOnScreen");
                turnOnProcessingHoldOnScreen = processingHoldOnScreen != null;
            }
            if (device.getDriver().findElements(processingHoldOnScreen).size() > 0) {
                waits.makeThreadSleep(500);
            } else return;
            try {
                if (turnOnProcessingHoldOnScreen && (device.getDriver().findElements(processingHoldOnScreen).get(0).getRect().getDimension().getWidth() > 0 || device.getDriver().findElement(processingHoldOnScreen).isEnabled())) {
                    long startTime = System.currentTimeMillis() / 1000;
                   waits.disappearForProcessingONLY(processingHoldOnScreen, 120);
                    waits.makeThreadSleep(400);
                    long endTime = (System.currentTimeMillis() / 1000) - startTime;
                    reportController.write(LogLevel.INFO, String.format("Waited for hold on screen to fade away for %s seconds", endTime));
                }
            } catch (Exception e) {
                /* Ignore Exception */
            }
        }
    }

    private WebElement getElementForMagicWait(By locator) {
        return device.getDriver().findElement(locator);
    }

    /**
     * confirms valid view port
     */
    private int isViewPortCounter = 0;

    private Boolean isViewPort(WebElement element) {
        Boolean status = false;
        double top = 0.0, left = 0.0, right = 0.0, bottom = 0.0;
        try {
            Object windowWidthOpt1 = javaScript.executeJSCommand().executeScript("return window.innerWidth");
            Object windowWidthOpt2 = javaScript.executeJSCommand().executeScript("return document.documentElement.clientWidth");
            Object windowHeightOpt1 = javaScript.executeJSCommand().executeScript("return window.innerHeight");
            Object windowHeightOpt2 = javaScript.executeJSCommand().executeScript("return document.documentElement.clientHeight");
            List<String> fourBounds = Arrays.asList(javaScript.executeJSCommand().executeScript("return arguments[0].getBoundingClientRect()", element).toString().split(",")).
                    stream().filter(bounds -> bounds.contains("top") || bounds.contains("left") || bounds.contains("right") || bounds.contains("bottom")).collect(Collectors.toList());
            for (String fourBound : fourBounds) {
                if (StringUtils.containsIgnoreCase(fourBound, "top")) top = Double.valueOf(fourBound.split("=")[1]);
                else if (StringUtils.containsIgnoreCase(fourBound, "left"))
                    left = Double.valueOf(fourBound.split("=")[1]);
                else if (StringUtils.containsIgnoreCase(fourBound, "right"))
                    right = Double.valueOf(fourBound.split("=")[1]);
                else if (StringUtils.containsIgnoreCase(fourBound, "bottom"))
                    left = Double.valueOf(fourBound.split("=")[1]);
            }
            if (
                    top >= 0 &&
                            left >= 0 &&
                            (right <= Double.parseDouble(windowWidthOpt1.toString()) || right <= Double.parseDouble(windowWidthOpt2.toString()) &&
                                    (bottom <= Double.parseDouble(windowHeightOpt1.toString()) || bottom <= Double.parseDouble(windowHeightOpt2.toString())))
            ) {
                isViewPortCounter = 0;
                reportController.write(LogLevel.INFO, "Element ViewPort confirmed");
                status = true;
            } else {
                if (isViewPortCounter < 5) {
                    isViewPortCounter++;
                    isViewPort((element));
                }
                reportController.write(LogLevel.IMPORTANT, "Element ViewPort not confirmed");
                status = false;
            }
        } catch (Exception e) {
            //Only to handle unexpected error of JS
        }
        return status;
    }

}
