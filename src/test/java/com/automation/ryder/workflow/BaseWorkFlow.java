package com.automation.ryder.workflow;

import com.automation.ryder.controller.access.UserPerforms;
import org.openqa.selenium.By;
import org.testng.Assert;

public class BaseWorkFlow {
    private UserPerforms userPerforms;
    public BaseWorkFlow(UserPerforms userPerforms){
        this.userPerforms = userPerforms;
    }
    public void makingSampleApiCalls() {
        userPerforms.textInput().in(By.xpath("//*[@aria-label='Search']"),"ghasfdhgfashdfhasfdhasfhdfashfdhasg");
        userPerforms.click().withMouse().on(By.xpath("//input[2]"));
        Assert.assertTrue(false);
    }
}
