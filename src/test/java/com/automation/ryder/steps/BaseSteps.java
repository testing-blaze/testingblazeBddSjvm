package com.automation.ryder.steps;

import com.automation.ryder.workflow.BaseWorkFlow;
import io.cucumber.java.en.Given;

public class BaseSteps {
    private BaseWorkFlow baseWorkFlow;

    public BaseSteps(BaseWorkFlow baseWorkFlow) {

        this.baseWorkFlow = baseWorkFlow;
    }

    @Given("I make different api calls")
    public void makingApiCalls() {
        this.baseWorkFlow.makingSampleApiCalls();
    }
}
