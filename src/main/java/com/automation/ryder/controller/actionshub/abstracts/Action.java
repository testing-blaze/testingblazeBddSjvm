package com.automation.ryder.controller.actionshub.abstracts;

import org.openqa.selenium.WebElement;

public interface Action {
    void doIt(WebElement element,Boolean processing);
    void doIt(WebElement element, String input);

}
