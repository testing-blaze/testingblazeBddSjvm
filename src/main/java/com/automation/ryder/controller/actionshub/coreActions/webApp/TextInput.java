package com.automation.ryder.controller.actionshub.coreActions.webApp;

import com.automation.ryder.controller.actionshub.abstracts.Action;
import com.automation.ryder.controller.actionshub.abstracts.Element;
import com.automation.ryder.controller.actionshub.service.ActionsHub;
import com.automation.ryder.controller.access.ElementAPI;
import com.automation.ryder.library.core.JavaScript;

public class TextInput {
    private JavaScript javaScript;
    private Element elementApi;
    private Action executeAction;

    public TextInput(ElementAPI elementApi, ActionsHub executeAction,JavaScript javaScript) {
        this.elementApi = elementApi;
        this.executeAction = executeAction;
        this.javaScript = javaScript;
    }

    /**
     * input on webPage , mobilePage , AngularPage
     * @param locator Mobile , Ng , By :
     *                Mobile.
     *                Angular.
     *                By.
     * @author nauman.shahid
     */
    public <T> void in(T locator,String input) {
        in(locator,input, true);
    }

    /**
     * input on webPage , mobilePage , AngularPage
     * @param locator Mobile , Ng , By :
     *                Mobile.
     *                Angular.
     *                By.
     * @author nauman.shahid
     */
    public <T> void in(T locator,String input, Boolean processing) {
        executeAction.doIt(elementApi.locator(locator,processing),input);
    }

    /**
     * clear on webPage , mobilePage , AngularPage
     * @param locator Mobile , Ng , By :
     *                Mobile.
     *                Angular.
     *                By.
     * @author nauman.shahid
     */
    public <T> void toClear(T locator) {
        toClear(locator, true);
    }

    /**
     * clear on webPage , mobilePage , AngularPage
     * @param locator Mobile , Ng , By :
     *                Mobile.
     *                Angular.
     *                By.
     * @author nauman.shahid
     */
    public <T> void toClear(T locator, Boolean processing) {
        executeAction.doIt(elementApi.locator(locator,processing),"--clear--");
    }

    /**
     * Enter text with Java Script
     *
     * @param locator
     * @param input
     */
    public <T> void withJavaScript(T locator, String input) {
        withJavaScript(locator, input, true);
    }

    /**
     * Enter text with Java Script
     *
     * @param locator
     * @param input
     */
    public <T> void withJavaScript(T locator, String input, Boolean processing) {
        javaScript.InputJSByWebElement(elementApi.locator(locator,processing), input);
    }

}
