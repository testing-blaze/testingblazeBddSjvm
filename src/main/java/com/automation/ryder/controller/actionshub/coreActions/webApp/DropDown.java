package com.automation.ryder.controller.actionshub.coreActions.webApp;

import com.automation.ryder.controller.actionshub.abstracts.Action;
import com.automation.ryder.controller.actionshub.abstracts.Element;
import com.automation.ryder.controller.actionshub.service.ActionsHub;
import com.automation.ryder.controller.actionshub.service.ElementAPI;
import org.openqa.selenium.support.ui.Select;

public class DropDown {
    private Element elementApi;
    private Action executeAction;

    public DropDown(ActionsHub executeAction,ElementAPI elementApi) {
        this.elementApi = elementApi;
        this.executeAction = executeAction;
    }

    /**
     * select on webPage , mobilePage , AngularPage
     *
     * @param locator Mobile , Ng , By :
     *                Mobile.
     *                Angular.
     *                By.
     * @author nauman.shahid
     */
    public <T> Select from(T locator) {
        return from(locator, true);
    }

    /**
     * select on webPage , mobilePage , AngularPage
     *
     * @param locator Mobile , Ng , By :
     *                Mobile.
     *                Angular.
     *                By.
     * @author nauman.shahid
     */
    public <T> Select from(T locator, Boolean processing) {
        return elementApi.selectLocator(locator, processing);
    }

}
