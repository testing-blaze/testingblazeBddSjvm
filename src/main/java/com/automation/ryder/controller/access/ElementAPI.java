package com.automation.ryder.controller.access;

import com.automation.ryder.controller.actionshub.abstracts.Element;
import com.automation.ryder.controller.actionshub.coreActions.elementfunctions.FindMyElements;
import com.automation.ryder.controller.actionshub.coreActions.mobile.Mobile;
import com.automation.ryder.controller.actionshub.coreActions.elementfunctions.Ng;
import com.automation.ryder.controller.actionshub.coreActions.webApp.Click;
import com.automation.ryder.controller.actionshub.coreActions.webApp.Is;
import com.automation.ryder.controller.actionshub.coreActions.webApp.Scroll;
import com.automation.ryder.controller.actionshub.coreActions.webApp.TextInput;
import com.automation.ryder.controller.objects.Elements;
import com.automation.ryder.controller.reports.LogLevel;
import com.automation.ryder.controller.reports.ReportController;
import com.automation.ryder.library.core.SwitchTo;
import com.paulhammant.ngwebdriver.ByAngular;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class ElementAPI implements Element {

    private FindMyElements findMyElements;
    private Mobile findMobileElement;
    private Ng ng;
    private ReportController reportController;
    private TextInput textInput;
    private Click click;
    private Is is;
    private Scroll scroll;
    private SwitchTo switchTo;

    public ElementAPI(FindMyElements findMyElements, Mobile findMobileElement, Ng ng, ReportController reportController) {
        this.findMyElements = findMyElements;
        this.findMobileElement = findMobileElement;
        this.ng = ng;
        this.reportController=reportController;
    }

    @Override
    public <T> WebElement locator(T locator, Boolean processing) {
            return handleLocatorInstanceOf(locator, processing);
    }

    @Override
    public <T> List<Elements> locators(T locator, Boolean processing) {
        reportController.write(LogLevel.INFO, "Getting list of elements located by: " + locator.toString());
        return handleLocatorsInstanceOf(locator, processing);

    }

    /**
     * *********************** Important *********************
     * This method is currently expected to handle nested element for mobile and Ng as well
     *
     * @param parentElement
     * @param locator
     * @param <T>
     * @return
     */

    @Override
    public <T> WebElement nestedElement(WebElement parentElement, T locator) {
        reportController.write(LogLevel.INFO, "Getting nested element located by: " + locator.toString());
            return findMyElements.getNestedElement(parentElement, (By) locator, true);
    }


    /**
     * *********************** Important *********************
     * This method is expected to handle nested elements for mobile and ng as well
     *
     * @param webElement
     * @param locator
     * @param <T>
     * @return
     */
    @Override
    public <T> List<Elements> nestedElementsList(WebElement webElement, T locator) {
        reportController.write(LogLevel.INFO, "Getting nested list of elements located by: " + locator.toString());
        List<WebElement> elementList = new ArrayList<>();
        List<Elements> collectElements = new ArrayList<>();

       if (locator instanceof By) {
            elementList = findMyElements.getNestedElementList(webElement, (By) locator);
        }

        if (elementList.size() > 0) {
            for (WebElement ele : elementList) {
                Elements newElement = new Elements(ele);
                newElement.setRequiredObjects(findMyElements,textInput, click, is, scroll, switchTo);
                collectElements.add(newElement);
            }
        }
        return collectElements;
    }

    @Override
    public <T> Select selectLocator(T locatorParameter, Boolean processing) {
        return new Select(locator(locatorParameter, processing));
    }

    /******************** Private Methods ***********************/

    private <T> WebElement handleLocatorInstanceOf(T locator, Boolean processing) {
        WebElement element = null;
        if (locator instanceof WebElement) {
            element = (WebElement) locator;
        }
        if (locator instanceof MobileBy) {
            element = findMobileElement.getMobileElement((MobileBy) locator, processing);
        } else if (locator instanceof ByAngular.BaseBy) {
            element = ng.getNgElement((ByAngular.BaseBy) locator, processing);
        } else if (locator instanceof By) {
            element = findMyElements.getElement((By) locator, processing);
        }
        return element;
    }

    private <T> List<Elements> handleLocatorsInstanceOf(T locator, Boolean processing) {
        List<WebElement> elementList = null;

        if (locator instanceof MobileBy) {
            elementList = findMobileElement.getMobileElements((MobileBy) locator, processing);
        } else if (locator instanceof ByAngular.BaseBy) {
            elementList = ng.getNgElements((ByAngular.BaseBy) locator, processing);
        } else if (locator instanceof By) {
            elementList = findMyElements.getElements((By) locator, processing);
        }

        List<Elements> frameworkElements = new ArrayList<>();
        if (elementList.size() > 0) {
            for (WebElement element : elementList) {
                Elements newElement = new Elements(element);
                newElement.setRequiredObjects(findMyElements,textInput, click, is, scroll, switchTo);
                frameworkElements.add(newElement);
            }
        }
        return frameworkElements;
    }

    public static By getBy(String type, String expression) {
        By getBy = null;
        switch (type.toLowerCase()) {
            case "by-xpath":
                getBy = By.xpath(expression);
                break;
            case "by-id":
                getBy = By.id(expression);
                break;
            case "by-css":
                getBy = By.cssSelector(expression);
                break;
            case "mobileby-xpath":
                getBy = MobileBy.xpath(expression);
                break;
            case "mobileby-id":
                getBy = MobileBy.id(expression);
                break;
            case "mobileby-css":
                getBy = MobileBy.cssSelector(expression);
            case "byangular.baseby-xpath":
                getBy = ByAngular.BaseBy.xpath(expression);
                break;
            case "byangular.baseby-id":
                getBy = ByAngular.BaseBy.id(expression);
                break;
        }
        return getBy;
    }

    /**
     * To grab dependencies from UserPerforms
     * @param textInput
     * @param click
     * @param is
     * @param scroll
     * @param switchTo
     */
    protected void setDependenciesForElements(TextInput textInput,Click click,Is is,Scroll scroll,SwitchTo switchTo){
        this.textInput=textInput;
        this.click=click;
        this.is=is;
        this.scroll=scroll;
        this.switchTo=switchTo;
    }

}
