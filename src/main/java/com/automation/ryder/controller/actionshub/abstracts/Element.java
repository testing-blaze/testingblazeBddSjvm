package com.automation.ryder.controller.actionshub.abstracts;

import com.automation.ryder.controller.objects.Elements;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public interface Element {

    <T> WebElement locator(T locator, Boolean processing) ;

    <T> WebElement nestedElement(WebElement element, T locator);

    <T> List<Elements> locators(T locator, Boolean processing);

    <T> List<Elements> nestedElementsList(WebElement element, T locator);

    <T> Select selectLocator(T locator, Boolean processing);
}
