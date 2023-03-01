package com.automation.ryder.controller.configuration;

import com.automation.ryder.controller.access.UserPerforms;
import com.automation.ryder.controller.actionshub.coreActions.elementfunctions.FindMyElements;
import com.automation.ryder.controller.actionshub.coreActions.elementfunctions.MouseActions;
import com.automation.ryder.controller.actionshub.coreActions.elementfunctions.Ng;
import com.automation.ryder.controller.actionshub.coreActions.elementfunctions.Waits;
import com.automation.ryder.controller.actionshub.coreActions.mobile.Mobile;
import com.automation.ryder.controller.actionshub.coreActions.webApp.*;
import com.automation.ryder.controller.actionshub.service.ElementAPI;
import com.automation.ryder.controller.actionshub.service.ElementReference;
import com.automation.ryder.controller.actionshub.service.FrameManager;
import com.automation.ryder.controller.objects.Elements;
import com.automation.ryder.controller.reports.ReportController;
import com.automation.ryder.library.core.JavaScript;
import com.automation.ryder.library.misc.AddOns;
import io.cucumber.java.Before;

public class DependentLibInit {
    public DependentLibInit(FrameManager frameManager, JavaScript javaScript, Waits waits, AddOns addOns,
                            Mobile mobile, Ng ng, MouseActions mouseActions,
                            //Elements elements,
                            DropDown dropDown, ElementReference elementReference, FindMyElements findMyElements,ElementAPI elementApi, Fetch fetch, ReportController reportController, UserPerforms userPerforms){
    }



    public void dependentLibHook(){
        //No implementation is needed as the sole purpose is to trigger the hook
        System.out.println("hook 2");
    }
}
