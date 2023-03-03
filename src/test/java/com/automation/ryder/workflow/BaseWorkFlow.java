package com.automation.ryder.workflow;

import com.automation.ryder.controller.access.ServiceUserPerforms;
import com.automation.ryder.controller.access.UserPerforms;
import com.automation.ryder.controller.configuration.EnvironmentFactory;
import org.openqa.selenium.By;
import org.testng.Assert;

public class BaseWorkFlow {
    private UserPerforms userPerforms;
    ServiceUserPerforms serviceUserPerforms;
    public BaseWorkFlow(UserPerforms userPerforms,ServiceUserPerforms serviceUserPerforms){
        this.userPerforms = userPerforms;
        this.serviceUserPerforms=serviceUserPerforms;
    }
    public void makingSampleApiCalls() {
        userPerforms.textInput().in(By.xpath("//*[@aria-label='Search']"),"ghasfdhgfashdfhasfdhasfhdfashfdhasg");
        userPerforms.click().withMouse().on(By.xpath("//input[2]"));
        serviceUserPerforms.restHttp().getCall(EnvironmentFactory.getEnvironmentUrl()+"/v1/employees",null,null);
        Assert.assertTrue(false);
    }
}
