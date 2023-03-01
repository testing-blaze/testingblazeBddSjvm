package com.automation.ryder.controller.actionshub.abstracts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public interface ElementProcessing {
    List<WebElement> forListOfElements(By value);
    WebElement forSingleElement(By value);
    WebElement forNestedElement(WebElement element, By value);
}
