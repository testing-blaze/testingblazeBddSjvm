package com.automation.ryder.controller.actionshub.coreActions.webApp;

import com.automation.ryder.controller.actionshub.abstracts.Element;
import com.automation.ryder.controller.actionshub.coreActions.elementfunctions.MouseActions;
import com.automation.ryder.controller.actionshub.coreActions.mobile.Mobile;
import com.automation.ryder.controller.actionshub.service.ElementAPI;
import com.automation.ryder.library.core.JavaScript;
import org.openqa.selenium.WebElement;

public class Scroll {
    private Element elementApi;
    private JavaScript javaScript;
    private MouseActions actions;
    private Mobile mobile;

    public Scroll(ElementAPI elementApi, JavaScript javaScript,MouseActions actions, Mobile mobile) {
        this.elementApi = elementApi;
        this.javaScript = javaScript;
        this.actions = actions;
        this.mobile = mobile;
    }

    /**
     * Move to any specific locator
     *
     * @param locator
     * @author monu.kumar
     */
    public <T> void toMoveToElement(T locator) {
        toMoveToElement(locator, true);
    }

    /**
     * Move to any specific locator
     *
     * @param locator
     * @author monu.kumar
     */
    public <T> void toMoveToElement(T locator, Boolean processing) {
        javaScript.moveToElement(elementApi.locator(locator, processing));
    }

    /**
     * scroll page window up,down or to any specific x-axis and y-axis
     *
     * @author nauman.shahid
     */
    public void onPageWindow(String scrolltype, int xAxis, int Yaxis) {
        javaScript.scrollPageWindowJS(scrolltype, xAxis, Yaxis);
    }

    /**
     * scroll page up,down or to any specific x-axis and y-axis
     */
    public void onPageDocument(String scrolltype, int xAxis, int Yaxis) {
        javaScript.scrollPageDocumentJS(scrolltype, xAxis, Yaxis);
    }

    /**
     * scroll to a specific element location
     *
     * @author nauman.shahid
     */
    public <T> void onPageToSpecificElement(T locator) {
        javaScript.scrollpageToSpecificElement(elementApi.locator(locator, false));
    }

    /**
     * scroll a specific element to the center of the page
     * Concept for function derived from:
     * https://github.com/litera/jquery-scrollintoview
     * Copyright (c) 2011 Robert Koritnik
     * Licensed under the terms of the MIT license
     * http://www.opensource.org/licenses/mit-license.php:
     * ----------
     * Permission is hereby granted, free of charge, to any person obtaining a copy
     * of this software and associated documentation files (the "Software"), to deal
     * in the Software without restriction, including without limitation the rights
     * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
     * copies of the Software, and to permit persons to whom the Software is
     * furnished to do so, subject to the following conditions:
     * <p>
     * The above copyright notice and this permission notice shall be included in all
     * copies or substantial portions of the Software.
     * <p>
     * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
     * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
     * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
     * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
     * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
     * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
     * SOFTWARE.
     * ----------
     * <p>
     * Modified version devised by John Phillips (12/27/2018)
     */
    public <T> void toElementCenter(T locator) {
        javaScript.scrollElementToPageDetailCenter(elementApi.locator(locator, false));
    }

    /**
     * slides any slider towards right to 150 points
     *
     * @author nauman.shahid
     */
    public <T> void toMoveSlider(T sliderLocator) {
        WebElement slider = elementApi.locator(sliderLocator, true);
        actions.moveSliderByOffset(slider, slider.getSize().getWidth() - 150, 0);
    }

    /**
     * slides any slider by a given x and y value
     *
     * @param sliderLocator the slider which is to be moved
     * @param xOffset       the amount to move the slider in the horizontal direction
     * @param yOffset       the amount to move the slider in the vertical direction
     * @author nauman.shahid
     */
    public <T> void toMoveSliderByOffset(T sliderLocator, int xOffset, int yOffset) {
        toMoveSliderByOffset(sliderLocator, xOffset, yOffset, true);
    }

    /**
     * slides any slider by a given x and y value
     *
     * @param sliderLocator the slider which is to be moved
     * @param xOffset       the amount to move the slider in the horizontal direction
     * @param yOffset       the amount to move the slider in the vertical direction
     * @author nauman.shahid
     */
    public <T> void toMoveSliderByOffset(T sliderLocator, int xOffset, int yOffset, Boolean processing) {
        actions.moveSliderByOffset(elementApi.locator(sliderLocator, processing), xOffset, yOffset);
    }

    /**
     * drag and drop with offset
     *
     * @param locator
     * @param xOffset
     * @param yOffset
     * @param processing
     * @param <T>
     */
    public <T> void toDragAndDropByOffset(T locator, int xOffset, int yOffset, Boolean processing) {
        actions.mouseDragAndDrop(elementApi.locator(locator, processing), xOffset, yOffset);
    }

    /**
     * drag and drop with offset
     *
     * @param locator
     * @param xOffset
     * @param yOffset
     * @param <T>
     */
    public <T> void toDragAndDropByOffset(T locator, int xOffset, int yOffset) {
        toDragAndDropByOffset(locator, xOffset, yOffset, true);
    }

    /**
     * Scroll to desired location on web page using mouse scroller
     *
     * @author nauman.shahid
     */
    public <T> void withKeyBoardToElement(T locatorScrollTo) {
        actions.scrollTo(elementApi.locator(locatorScrollTo, false));
    }

    /**
     * move to desired element on web page using mouse action
     *
     * @author nauman.shahid
     */
    public <T> void withMouseToElement(T locatorScrollTo) {
        actions.moveMouseToSpecificLocation(elementApi.locator(locatorScrollTo, false));
    }

    /**
     * move to desired element on web page using mouse action
     *
     * @author nauman.shahid
     */
    public <T> void withMouseSlowlyToElement(T locatorScrollTo) {
        withMouseSlowlyToElement(locatorScrollTo, true);
    }

    /**
     * move to desired element on web page using mouse action
     *
     * @author nauman.shahid
     */
    public <T> void withMouseSlowlyToElement(T locatorScrollTo,Boolean processing) {
        actions.moveMouseToSpecificLocationSlowly(elementApi.locator(locatorScrollTo, processing));
    }

    /**
     * move element to desired location on web page and off set using mouse action
     *
     * @author nauman.shahid
     */
    public <T> void withMouseToElementAndOffset(T locator, int xOffset, int yOffset) {
        actions.moveMouseToTargetAndOffset(elementApi.locator(locator, false), xOffset, yOffset);
    }

    /**
     * Zoom in or out
     *
     * @author nauman.shahid
     */
    public void toZoomInOut(int zoom) {
        javaScript.ZoomInOutJS(zoom);
    }

    public Mobile.MobileScrolls onGadgets() {
        return mobile.mobileScrolls();
    }

}


