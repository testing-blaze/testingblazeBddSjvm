package com.automation.ryder.controller.actionshub.coreActions.webApp;

import com.automation.ryder.controller.actionshub.abstracts.Element;
import com.automation.ryder.controller.actionshub.service.ElementAPI;

/**
 * get various functionalities for dates , urls , browser handling , navigations etc.
 *
 * @author nauman.shahid
 */
public final class Fetch {
    private Element elementApi;

    public Fetch(ElementAPI elementApi) {
        this.elementApi = elementApi;
    }

    /**
     * get inner text on webPage , mobilePage , AngularPage
     *
     * @param locator Mobile , Ng , By :
     *                Mobile.
     *                Angular.
     *                By.
     * @return inner text
     * @author nauman.shahid
     */
    public <T> String text(T locator) {
        return text(locator, true);
    }

    /**
     * get inner text on webPage , mobilePage , AngularPage
     *
     * @param locator Mobile , Ng , By :
     *                Mobile.
     *                Angular.
     *                By.
     * @return inner text
     * @author nauman.shahid
     */
    public <T> String text(T locator, Boolean processing) {
        return elementApi.locator(locator, processing).getText();
    }

    /**
     * get attribute value on webPage , mobilePage , AngularPage
     *
     * @param locator Mobile , Ng , By :
     *                Mobile.
     *                Angular.
     *                By.
     * @return attribute value
     * @author nauman.shahid
     */
    public <T> String attribute(T locator, String attribute) {
        return attribute(locator, attribute, true);
    }

    /**
     * get attribute value on webPage , mobilePage , AngularPage
     *
     * @param locator    Mobile , Ng , By :
     *                   Mobile.
     *                   Angular.
     *                   By.
     * @param processing
     * @return attribute value
     * @author nauman.shahid
     */
    public <T> String attribute(T locator, String attribute, Boolean processing) {
        return elementApi.locator(locator, processing).getAttribute(attribute);
    }

    /**
     * get css property on webPage , mobilePage , AngularPage
     *
     * @param locator  Mobile , Ng , By :
     *                 Mobile.
     *                 Angular.
     *                 By.
     * @param property
     * @return css value
     * @author nauman.shahid
     */
    public <T> String cssProperty(T locator, String property) {
        return cssProperty(locator, property, true);
    }

    /**
     * get css property on webPage , mobilePage , AngularPage
     *
     * @param locator    Mobile , Ng , By :
     *                   Mobile.
     *                   Angular.
     *                   By.
     * @param property
     * @param processing
     * @return css value
     * @author nauman.shahid
     */
    public <T> String cssProperty(T locator, String property, Boolean processing) {
        return elementApi.locator(locator, processing).getCssValue(property);
    }

}
