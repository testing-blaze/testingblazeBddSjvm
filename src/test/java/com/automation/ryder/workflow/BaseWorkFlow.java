package com.automation.ryder.workflow;

import com.automation.ryder.controller.access.ServiceUserPerforms;
import com.automation.ryder.controller.access.UserPerforms;
import com.automation.ryder.controller.configuration.EnvironmentFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

        //serviceUserPerforms.restHttp().getCall(EnvironmentFactory.getEnvironmentUrl()+"/v1/employees",null,null);
        //WebElement abc = userPerforms.getElementReference().of(By.xpath("//input[2]"));
        //System.out.println(userPerforms.getElementReference().forListOfElements().of(By.xpath("//input[2]")).size());
        //userPerforms.click().on(userPerforms.getElementReference().ofNested(abc,""));
       // serviceUserPerforms.azureServiceBusActionsTo().sendMessageToTopic("","","");
        //Assert.assertTrue(false);
    }

    public void sample2() {
        userPerforms.click().withMouse().on(By.xpath("//input[2]"));
    }
}
