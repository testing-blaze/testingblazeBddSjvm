package com.automation.ryder.controller.actionshub.service;

import com.automation.ryder.controller.access.ElementAPI;
import com.automation.ryder.controller.actionshub.abstracts.Element;
import org.openqa.selenium.WebElement;

public class ElementReference {
    private Element elementApi;
    private ElementsList elementsList;

    // Accessed from Fetch class
    public ElementReference(ElementAPI elementApi, ElementsList elementsList) {
        this.elementApi = elementApi;
        this.elementsList = elementsList;
    }

    /**
     * Only to grasp element on webPage , mobilePage , AngularPage: Not allowed to use any function like click or send keys from here.
     * @param locator Mobile , Ng , By :
     *                Mobile.
     *                Angular.
     *                By.
     *                @return webElement reference
     * @author nauman.shahid
     */
    public <T> WebElement of(T locator) {
        return of(locator, true);
    }

    /**
     * Only to grasp element on webPage , mobilePage , AngularPage: Not allowed to use any function like click or send keys from here.
     * @param locator Mobile , Ng , By :
     *                Mobile.
     *                Angular.
     *                By.
     * @param processing true/false
     * @return webElement reference
     * @author nauman.shahid
     */
    public <T> WebElement of(T locator, Boolean processing) {
        return elementApi.locator(locator,processing);
    }

    /**
     * Only to grasp nested element on webPage , mobilePage , AngularPage: Not allowed to use any function like click or send keys from here.
     * @param locator Mobile , Ng , By :
     *                Mobile.
     *                Angular.
     *                By.
     * @param element the main element
     * @return webElement
     * @author nauman.shahid
     */
    public <T> WebElement ofNested(WebElement element, T locator) {
        return elementApi.nestedElement(element, locator);
    }

    /**
     * Only to grasp element list on webPage , mobilePage , AngularPage: Not allowed to use any function like click or send keys from here.
     * Works for       Mobile , Ng , By :
     *                @return list of elements
     * @author nauman.shahid
     */
    public ElementsList forListOfElements() {
        return elementsList;
    }


}
