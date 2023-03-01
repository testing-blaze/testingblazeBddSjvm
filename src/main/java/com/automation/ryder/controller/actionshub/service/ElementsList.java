package com.automation.ryder.controller.actionshub.service;

import com.automation.ryder.controller.actionshub.abstracts.Element;
import com.automation.ryder.controller.objects.Elements;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ElementsList {
    private Element elementApi;

    public ElementsList(ElementAPI elementApi) {
        this.elementApi = elementApi;
    }

    /**
     * Only to grasp element list on webPage , mobilePage , AngularPage: Not allowed to use any function like click or send keys from here.
     * @param locator Mobile , Ng , By :
     *                Mobile.
     *                Angular.
     *                By.
     *                @return List<Elements> reference
     * @author nauman.shahid
     */
    public <T> List<Elements> of(T locator) {
        return of(locator, true);
    }

    /**
     * Only to grasp element list on webPage , mobilePage , AngularPage: Not allowed to use any function like click or send keys from here.
     * @param processing true/false
     * @param locator Mobile , Ng , By :
     *                Mobile.
     *                Angular.
     *                By.
     *                @return List<Elements>reference
     * @author nauman.shahid
     */
    public <T> List<Elements> of(T locator, Boolean processing) {
        return elementApi.locators(locator, processing);
    }

    /**
     * Only to grasp element list on webPage , mobilePage , AngularPage: Not allowed to use any function like click or send keys from here.
     * @param element the main element
     * @param locator Mobile , Ng , By :
     *                Mobile.
     *                Angular.
     *                By.
     *                @return List<Elements> reference
     * @author nauman.shahid
     */
    public <T> List<Elements> ofNested(WebElement element, T locator) {
        return elementApi.nestedElementsList(element, locator);
    }


}
