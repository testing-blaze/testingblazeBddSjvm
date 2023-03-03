package com.automation.ryder.controller.access;

import com.automation.ryder.controller.actionshub.coreActions.elementfunctions.FindMyElements;
import com.automation.ryder.controller.actionshub.coreActions.elementfunctions.Ng;
import com.automation.ryder.controller.actionshub.coreActions.elementfunctions.Waits;
import com.automation.ryder.controller.actionshub.coreActions.mobile.Mobile;
import com.automation.ryder.controller.actionshub.coreActions.webApp.*;
import com.automation.ryder.controller.actionshub.service.ElementAPI;
import com.automation.ryder.controller.actionshub.service.ElementReference;
import com.automation.ryder.controller.actionshub.service.FrameManager;
import com.automation.ryder.controller.reports.ReportController;
import com.automation.ryder.library.accessibility.Accessibility;
import com.automation.ryder.library.core.*;
import com.automation.ryder.library.misc.AddOns;
import com.automation.ryder.library.misc.ScreenCapture;
import com.automation.ryder.library.visualTesting.EyesManager;
import org.assertj.core.api.SoftAssertions;

public class UserPerforms {
        private Properties_Logs pl;
        private RobotActions ra;
        private ScreenCapture sc;
        private Sanitize sanitize;
        private Waits wait;
        private RestfulWebServices rWebServices;
        private SwitchTo switchTo;
        private Cookies cookies;
        private Convert convert;
        private FileHandler fileHandler;
        private ReportController reportController;
        private Emails email;
        private Fetch get;
        private Click click;
        private TextInput enterText;
        private Scroll scroll;
        private DropDown select;
        private AddOns miscellaneous;
        private Is is;
        private ElementReference elementRef;
        private SoftAssertions softAssertions;
        private Mobile mobile;
        private Browser browser;
        private Dates dates;

        private Accessibility accessibility;

        private EyesManager visualTesting;
        private AzureServiceBus azureServiceBus;
        private DataBases dataBases;

        public UserPerforms(FrameManager frameManager, JavaScript javaScript, Ng ng, ElementAPI elementAPI, FindMyElements findMyElements, Properties_Logs pl, RobotActions ra, ScreenCapture sc, Sanitize sanitize, Waits wait,
                            SwitchTo switchTo, Cookies cookies, Convert convert, FileHandler fileHandler, ReportController reportController,
                            Emails email, Fetch get, Click click, TextInput enterText, Scroll scroll, DropDown select, AddOns miscellaneous, Is is,
                            ElementReference elementRef, SoftAssertions softAssertions, Mobile mobile, Browser browser, Dates dates) {
            this.pl=pl;
            this.ra=ra;
            this.sc=sc;
            this.sanitize=sanitize;
            this.wait=wait;
            this.rWebServices=rWebServices;
            this.switchTo=switchTo;
            this.cookies=cookies;
            this.convert=convert;
            this.fileHandler=fileHandler;
            this.reportController=reportController;
            this.email=email;
            this.get=get;
            this.click=click;
            this.enterText=enterText;
            this.scroll=scroll;
            this.select=select;
            this.miscellaneous=miscellaneous;
            this.is=is;
            this.elementRef=elementRef;
            this.softAssertions=softAssertions;
            this.mobile=mobile;
            this.browser=browser;
            this.dates=dates;
            this.azureServiceBus=azureServiceBus;
            this.dataBases=dataBases;

        }

        public Click click() {
            return click;
        }

        public TextInput textInput() {
            return enterText;
        }

        public Scroll scroll() {
            return scroll;
        }

        public DropDown dropDownSelection() {
            return select;
        }

        public SoftAssertions assertionsTo() {
            return softAssertions;
        }

        public Mobile.MobileAccessories mobileOperations() {
            return mobile.MobileAccessories();
        }

        public AddOns addOnsTo() {
            return miscellaneous;
        }

        public Is checkToSee() {
            return is;
        }

        public ElementReference getElementReference() {
            return elementRef;
        }

        /**
         * returns a clean string and xpath
         *
         * @return string
         * @author nauman.shahid
         */
        public Sanitize sanityOfXpathTo() {
            return sanitize == null ? sanitize = new Sanitize() : sanitize;
        }

        /**
         * Handle different explicit and implicit waits
         *
         * @return differennt waits
         * @author nauman.shahid
         */
        public Waits waitFor() {
            return wait;
        }

        /**
         * Access various libraries inluding webpage and elements
         *
         * @return
         * @author nauman.shahid
         */
        public Fetch actionToGet() {
            return get;
        }

        /**
         * write report. Only allowed to add addition reporting logs in special circumstances
         *
         * @return report
         * @author nauman.shahid
         */
        public ReportController updatingOfReportWith() {
            return reportController;
        }

        /**
         * access properties file : Place properties files in src/test/resources
         *
         * @return
         * @author nauman.shahid
         */
        public Properties_Logs propertiesFileOperationsTo() {
            return pl;
        }

        /**
         * java robot class to simulate hardware actions - Keyboard , mouse
         *
         * @return
         * @author nauman.shahid
         */
        public RobotActions robotActionsTo() {
            return ra == null ? ra = new RobotActions() : ra;
        }

        /**
         * take screen shots
         *
         * @return
         */
        public ScreenCapture snapShotTo() {
            return sc;
        }

        /**
         * switch to frames and alerts
         *
         * @return
         * @author nauman.shahid
         */
        public SwitchTo switchTo() {
            return switchTo;
        }

        /**
         * add,delete cookies
         *
         * @return
         * @author nauman.shahid
         */
        public Cookies cookiesOperationsTo() {
            return cookies;
        }

        public Convert conversionOf() {
            return convert;
        }

        /**
         * Handles all type of file related ops. excel, adobe, etc
         *
         * @return different file handling including excel,adobe ,json, image.
         * @author nauman.shahid
         */
        public FileHandler fileHandling() {
            return fileHandler;
        }

        /**
         * access emails
         *
         * @return email library
         * @author nauman.shahid
         */
        public Emails emailOperationsTo() {
            return email;
        }

        /**
         * access browser
         *
         * @return brwoser library
         * @author nauman.shahid
         */
        public Browser browserOperationsTo() {
            return browser;
        }

        /**
         * access browser
         *
         * @return brwoser library
         * @author nauman.shahid
         */
        public Dates dateOperationsToGet() {
            return dates;
        }

        public Accessibility accessibilityTesting() {
            return new Accessibility();
        }

        public EyesManager visualTesting(){
            return visualTesting;
        }


    }
