package com.automation.ryder.controller.actionshub.gherkin;

import io.cucumber.java.en.When;
import jdk.jfr.Description;
import org.openqa.selenium.By;

import javax.swing.*;
import java.awt.*;

public final class BuiltinCucumberSteps {

    @When("^I halt execution$")
    public void pauseExecution() throws Throwable {
        Toolkit.getDefaultToolkit().beep();
        Thread.sleep(1000);
        Toolkit.getDefaultToolkit().beep();
        JOptionPane.showMessageDialog(null, "Execution paused. Click \"OK\" to resume execution.");
    }

    @When("^I pause execution for \"(\\d+)\" seconds$")
    public void pauseExecutionForMinutes(int seconds) throws Throwable {
        Thread.sleep(seconds * 1000);
    }

    @When("^I wait for \"(\\d+)\" seconds$")
    public void waitForMinutes(int seconds) throws Throwable {
        Thread.sleep(seconds * 1000);
    }
}
