package com.automation.ryder.controller.objects;

import com.automation.ryder.controller.actionshub.coreActions.elementfunctions.FindMyElements;
import com.automation.ryder.controller.actionshub.coreActions.webApp.Click;
import com.automation.ryder.controller.actionshub.coreActions.webApp.Is;
import com.automation.ryder.controller.actionshub.coreActions.webApp.Scroll;
import com.automation.ryder.controller.actionshub.coreActions.webApp.TextInput;
import com.automation.ryder.library.core.SwitchTo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Elements {
    private WebElement element;
    private Click click;
    private TextInput textInput;
    private Is is;
    private Scroll scroll;
    private FindMyElements findMyElements;
    private SwitchTo switchTo;

    public Elements(WebElement element){
        this.element=element;
    }

    public void setRequiredObjects(FindMyElements findMyElements,TextInput textInput,Click click,Is is,Scroll scroll,SwitchTo switchTo) {
        this.element = element;
        this.click=click;
        this.textInput=textInput;
        this.is=is;
        this.scroll=scroll;
        this.findMyElements=findMyElements;
        this.switchTo=switchTo;
    }

    public void click() {
        click.on(element);
    }

    public void enterText(String text) {
        textInput.in(element, text);
    }

    public void clearText() {
        textInput.toClear(element);
    }

    public String getAttributes(String attributeName) {
        return element.getAttribute(attributeName);
    }

    public String getCssValue(String cssValue) {
        return element.getCssValue(cssValue);
    }

    public String getText() {
        return element.getText();
    }

    public Boolean isStale() {
        return is.isStale(element);
    }

    public Boolean isDisplayed() {
        return is.isDisplayed(element);
    }

    public Boolean isEnabled() {
        return is.isEnabled(element);
    }

    public Boolean isSelected() {
        return is.isSelected(element);
    }

    public void switchFrame() {
        switchTo.frame(element);
    }

    public void scrollWithMouseToElement() {
        scroll.withMouseToElement(element);
    }

    public void scrollToElement() {
        scroll.toMoveToElement(element);
    }

    public WebElement nestedElement(By locator) {
        return findMyElements.getNestedElement(this.element, locator, true);
    }

    public List<WebElement> nestedElements(By locator) {
        return findMyElements.getNestedElementList(this.element, locator);
    }

    public WebElement getElementReference(){
        return element;
    }
}
