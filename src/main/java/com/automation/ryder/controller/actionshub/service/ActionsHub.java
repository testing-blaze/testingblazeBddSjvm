package com.automation.ryder.controller.actionshub.service;

import com.automation.ryder.controller.access.GlobalVariables;
import com.automation.ryder.controller.actionshub.abstracts.Action;
import com.automation.ryder.controller.actionshub.coreActions.elementfunctions.FindMyElements;
import com.automation.ryder.controller.actionshub.coreActions.elementfunctions.Waits;
import com.automation.ryder.controller.actionshub.processing.ClickProcessingController;
import com.automation.ryder.controller.browsingdevices.BrowsingDeviceBucket;
import com.automation.ryder.controller.configuration.DeviceDriver;
import com.automation.ryder.controller.configuration.EnvironmentFactory;
import com.automation.ryder.controller.reports.LogLevel;
import com.automation.ryder.controller.reports.ReportController;
import com.automation.ryder.library.core.SwitchTo;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ActionsHub implements Action {
    private ClickProcessingController clickProcessingController;
    private static long timerLimit;
    private Waits wait;
    private DeviceDriver device;
    private FindMyElements findMyElements;
    private FluentWait<WebDriver> localWait = null;
    private ReportController reportController;
    private SwitchTo switchTo;

    public ActionsHub(BrowsingDeviceBucket device, Waits wait, FindMyElements findMyElements, ClickProcessingController clickProcessingController, ReportController reportController, SwitchTo switchTo) {
        this.wait=wait;
        this.device=device;
        this.findMyElements=findMyElements;
        this.clickProcessingController = clickProcessingController;
        this.reportController=reportController;
        this.switchTo=switchTo;
        if (localWait == null) {
            localWait = new WebDriverWait(device.getDriver(), Waits.STANDARD_WAIT_TIME).pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class, NullPointerException.class);
        }
    }

    @Override
    public void doIt(WebElement element,Boolean processing) {
        if (mobileRun()) clickProcessingController.PreProcessAction.perform();
        try {
            localWait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            acceptAlert(processing);
            reportController.write(LogLevel.INFO, "Click action performed successfully");

        } catch (StaleElementReferenceException staleException) {
            retryExecutor(element, processing,"click", "Stale element Exception Caught");
        } catch (ElementClickInterceptedException interceptException) {
            retryExecutor(element, processing,"click", "Intercept Exception Caught");
        } catch (ElementNotInteractableException interactableException) {
            retryExecutor(element, processing,"click", "Not Interactable Exception Caught");
        } catch (WebDriverException notClickableOrNoFocusException) {
            if (notClickableOrNoFocusException.getMessage().contains("is not clickable at point") | notClickableOrNoFocusException.getMessage().contains("cannot focus element")) {
                retryExecutor(element, processing,"click", "Element is not clickable or in focus");
            } else {
                throw notClickableOrNoFocusException;
            }
        }
        if (mobileRun()) clickProcessingController.postProcessingAction.perform();
    }

    @Override
    public void doIt(WebElement element, String input) {
        try {
            localWait.until(ExpectedConditions.elementToBeClickable(element));
            if (input.equalsIgnoreCase("--clear--")) {
                element.clear();
                acceptAlert(true);
            } else {
                element.sendKeys(input);
                acceptAlert(true);
            }
            reportController.write(LogLevel.INFO, "Input action performed successfully");
        } catch (StaleElementReferenceException staleException) {
            retryExecutor(element, true,"input", "Stale element Exception Caught", input);
        } catch (ElementClickInterceptedException interceptException) {
            retryExecutor(element,true, "input", "Intercept Exception Caught", input);
        } catch (ElementNotInteractableException interactableException) {
            retryExecutor(element, true,"input", "Not Interactable Exception Caught", input);
        } catch (WebDriverException notClickableOrNoFocusException) {
            if (notClickableOrNoFocusException.getMessage().contains("is not clickable at point") | notClickableOrNoFocusException.getMessage().contains("cannot focus element")) {
                retryExecutor(element, true,"input", "Element is not entering text or in focus", input);
            } else {
                throw notClickableOrNoFocusException;
            }
        }
    }

    /**
     * handle retry
     *
     * @param element
     * @param args    actionType,exception,inputText(for input only)
     */
    private void retryExecutor(WebElement element,Boolean processing, String... args) {
        if (mobileRun()) {
            timerLimit = wait.getWaitTime();
            reportController.write(LogLevel.CRITICAL, args[1] + " : Retrying to " + args[0] + " for maximum " + Waits.STANDARD_WAIT_TIME + " seconds");
            while (timerLimit > System.currentTimeMillis() / 1000) {
                try {
                    if (args[0].equalsIgnoreCase("click")) {
                        element.click();
                        acceptAlert(processing);
                        reportController.write(LogLevel.CRITICAL, args[1] + " : Retrying is Successful ");
                        break;
                    } else if (args[0].equalsIgnoreCase("input")) {
                        if (args[2].equalsIgnoreCase("--clear--")) {
                            element.clear();
                            acceptAlert(processing);
                        } else {
                            element.sendKeys(args[2]);
                            acceptAlert(processing);
                        }
                        reportController.write(LogLevel.CRITICAL, args[1] + " : Retrying is Successful ");
                        break;
                    }
                } catch (ElementClickInterceptedException interceptException) {
                    if (timerLimit <= System.currentTimeMillis() / 1000) {
                        throw interceptException;
                    }
                } catch (ElementNotInteractableException interactableException) {
                    if (timerLimit <= System.currentTimeMillis() / 1000) {
                        throw interactableException;
                    }
                } catch (StaleElementReferenceException staleException) {

                    if (GlobalVariables.getVariable("locatorInProgress") instanceof By) {
                        reportController.write(LogLevel.CRITICAL, "Stale Element Exception : Retrying once again");
                        WebElement freshElement = findMyElements.getElement((By) GlobalVariables.getVariable("locatorInProgress"), true);
                        wait.makeThreadSleep(2000);
                        if (args[0].equalsIgnoreCase("click")) {
                            freshElement.click();
                            reportController.write(LogLevel.CRITICAL, args[1] + " : Retrying is Successful ");
                            acceptAlert(processing);
                        } else if (args[0].equalsIgnoreCase("input")) {
                            if (args[2].equalsIgnoreCase("--clear--")) {
                                freshElement.clear();
                                reportController.write(LogLevel.CRITICAL, args[1] + " : Retrying is Successful ");
                                acceptAlert(processing);
                            } else {
                                freshElement.sendKeys(args[2]);
                                reportController.write(LogLevel.CRITICAL, args[1] + " : Retrying is Successful ");
                                acceptAlert(processing);
                            }
                        }
                    } else {
                        reportController.write(LogLevel.CRITICAL, "Stale Element Exception : Retry cancelled");
                        reportController.write(LogLevel.CRITICAL, "Next Step might fail");
                    }
                    break;
                } catch (WebDriverException notClickableException) {
                    if ((timerLimit <= System.currentTimeMillis() / 1000) || !notClickableException.getMessage().contains("is not clickable at point")) {
                        throw notClickableException;
                    }
                }
            }
        }
    }

    private Boolean mobileRun() {
        return !(EnvironmentFactory.getBrowsingDevice().equalsIgnoreCase("android") || EnvironmentFactory.getBrowsingDevice().equalsIgnoreCase("ios"));
    }

    private void acceptAlert(Boolean processing) {
        if(processing) switchTo.acceptAlert();
    }
}
