package com.automation.ryder.controller.actionshub.coreActions.webApp;

import com.automation.ryder.controller.actionshub.abstracts.Action;
import com.automation.ryder.controller.actionshub.abstracts.Element;
import com.automation.ryder.controller.actionshub.service.ActionsHub;
import com.automation.ryder.controller.access.ElementAPI;
import com.automation.ryder.library.core.JavaScript;
import com.automation.ryder.controller.actionshub.coreActions.mobile.Mobile;
import com.automation.ryder.controller.actionshub.coreActions.elementfunctions.MouseActions;

public class Click {
    private MouseClicks mouseClicks;
    private JavaScript javaScript;
    private Element elementApi;
    private Action executeAction;
    private Mobile mobile;
    protected MouseActions mouseActions;

    public Click(ElementAPI elementAPI, ActionsHub executeAction, Mobile mobile, JavaScript javaScript,MouseActions mouseActions) {
        this.elementApi = elementAPI;
        this.executeAction = executeAction;
        this.javaScript = javaScript;
        this.mobile = mobile;
        this.mouseActions=mouseActions;
        this.mouseClicks=new MouseClicks();
    }

    /**
     * click on webPage , mobilePage , AngularPage
     * @param locator Mobile , Ng , By :
     *                Mobile.
     *                Angular.
     *                By.
     * @author nauman.shahid
     */
    public <T> void on(T locator) {
        on(locator, true);
    }

    /**
     * click on webPage , mobilePage , AngularPage
     * @param locator Mobile , Ng , By :
     *                Mobile.
     *                Angular.
     *                By.
     * @author nauman.shahid
     */
    public <T> void on(T locator, Boolean processing) {
        executeAction.doIt(elementApi.locator(locator,processing),false);
    }


    /**
     * clicks on element using java script
     * @param locator
     * @author nauman.shahid
     */
    public <T> void withJavaScript(T locator) {
        withJavaScript(locator, true);
    }

    /**
     * clicks on element using java script
     * @param locator
     * @author nauman.shahid
     */
    public <T> void withJavaScript(T locator, Boolean processing) {
        javaScript.clickByJSWebElement(elementApi.locator(locator, processing));
    }

    /**
     * perform click using mouse actions
     *
     * @return mouse clicks
     * @author nauman.shahid
     */
    public MouseClicks withMouse() {
        return mouseClicks;
    }

    public Mobile.Tap withTapOnScreen(){
        return mobile.tap();
    }

    /**
     * Inner class for web based mouse actions handling
     *
     * @author nauman.shahid
     */
    public class MouseClicks {

        /**
         * clicks on desired location based on dimension
         *
         * @author nauman.shahid
         */
        public void at(int x, int y) {
            mouseActions.mouseClick(x, y);
        }

        /**
         * clicks on desired location on web page using By
         *
         * @author nauman.shahid
         */
        public <T> void on(T locator) {
            on(locator, true);
        }

        /**
         * clicks on desired location on web page using By
         *
         * @author nauman.shahid
         */
        public <T>  void on(T locator, Boolean processing) {
            mouseActions.mouseClick(elementApi.locator(locator, processing));
        }

        /**
         * click and hold on desired location on web paget
         *
         * @param holdTimeSeconds
         * @author nauman.shahid
         */
        public <T>  void onAndHold(T locator, int holdTimeSeconds) {
            onAndHold(locator, holdTimeSeconds, true);
        }

        /**
         * click and hold on desired location on web paget
         *
         * @param holdTimeSeconds
         * @author nauman.shahid
         */
        public <T>  void onAndHold(T locator, int holdTimeSeconds, Boolean processing) {
            mouseActions.mouseClickAndHold(elementApi.locator(locator, processing), holdTimeSeconds);
        }

        /**
         * Double clicks on desired location on web page
         *
         * @author nauman.shahid
         */
        public <T> void doubleClickOn(T locator) {
            doubleClickOn(locator, true);
        }

        /**
         * Double clicks on desired location on web page
         *
         * @author nauman.shahid
         */
        public <T> void doubleClickOn(T locator, Boolean processing) {
            mouseActions.mouseDoubleClick(elementApi.locator(locator, processing));
        }

        /**
         * Drag and drop on desired location on web page
         *
         * @author nauman.shahid
         */
        public <T> void dragAndDrop(T source, T target) {
            dragAndDrop(source,target, true);
        }

        /**
         * Drag and drop on desired location on web page
         *
         * @author nauman.shahid
         */
        public <T> void dragAndDrop(T source, T target, Boolean processing) {
            mouseActions.mouseDragAndDrop(elementApi.locator(source, processing), elementApi.locator(target, processing));
        }

        /**
         * Drag and Drop specific for HTML 5.
         * @author nauman.shahid
         */
        public <T> void dragAndDropInHtml5(T source, T target) {
            dragAndDropInHtml5(source,target, true);
        }

        /**
         * Drag and Drop specific for HTML 5.
         * @author nauman.shahid
         */
        public <T> void dragAndDropInHtml5(T source, T target, Boolean processing) {
            mouseActions.dragAndDropInHtml5(elementApi.locator(source, processing), elementApi.locator(target, processing));
        }

        /**
         * Mouse right click on desired location on web page
         *
         * @author nauman.shahid
         */
        public <T> void rightClickOn(T context) {
            rightClickOn(context, true);
        }


        /**
         * Mouse right click on desired location on web page
         *
         * @author nauman.shahid
         */
        public <T> void rightClickOn(T context, Boolean processing) {
            mouseActions.mouseRightClick(elementApi.locator(context, processing));
        }
    }

}
