package com.automation.ryder.library.core;

import com.automation.ryder.controller.actionshub.service.FrameManager;
import com.automation.ryder.controller.browsingdevices.BrowsingDeviceBucket;
import com.automation.ryder.controller.configuration.DeviceDriver;
import com.automation.ryder.controller.configuration.EnvironmentFactory;
import com.automation.ryder.controller.reports.LogLevel;
import com.automation.ryder.controller.reports.ReportController;
import com.automation.ryder.library.misc.ConsoleFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import javax.media.rtp.rtcp.Report;


/**
 * @author nauman.shahid

 * javascript functions library
 */

public final class JavaScript {
    private JavascriptExecutor js;
    private FrameManager iframeAnalyzer;
    private DeviceDriver device;
    private ReportController reportController;

    public JavaScript(BrowsingDeviceBucket device, FrameManager iframeAnalyzer, ReportController reportController) {
        this.device=device;
        this.iframeAnalyzer=iframeAnalyzer;
        js = (JavascriptExecutor) device.getDriver();
        this.reportController=reportController;
    }

    /**
     * clicks on specific webElement using java script
     */
    public void clickByJSWebElement(WebElement element) {
        reportController.write(LogLevel.INFO, "Clicking with java script");
        try {
            scrollElementToPageDetailCenter(element);
            js.executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            reportController.write(LogLevel.INFO, ConsoleFormatter.setTextColor(ConsoleFormatter.COLOR.RED, "X: ") + "Unable to use JavaScript due to : " + e.getMessage());
        }
    }

    /**
     * To blink color on specifc element using java script
     */
    public void flashColor(WebElement element, String color, int BlinkNumber) {
        String oldColor = element.getCssValue("color");
        for (int i = 0; i < BlinkNumber; i++) {
            js.executeScript("arguments[0].style.color='green'", element);
            js.executeScript("arguments[0].style.color='" + color + "'", element);
        }
        js.executeScript("arguments[0].style.color='" + oldColor + "'", element);
    }

    /**
     * scroll page up,down or to any specific x-axis and y-axis using java script
     */
    public void scrollPageWindowJS(String scrolltype, int xAxis, int Yaxis) {
        reportController.write(LogLevel.INFO, "Scrolling " + scrolltype + " on page");
        switch (scrolltype.toUpperCase()) {
            case "PAGEDOWN":
                js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
                break;
            case "PAGEUP":
                js.executeScript("window.scrollTo(0,-document.body.scrollHeight)");
                break;
            case "MYLOCATION":
                js.executeScript("window.scrollBy(\"" + xAxis + "\"", "" + Yaxis + "\")");
                break;
        }
    }

    /**
     * scroll page up,down or to any specific x-axis and y-axis using java script
     */
    public void scrollPageDocumentJS(String scrolltype, int xAxis, int Yaxis) {
        reportController.write(LogLevel.INFO, "Scrolling " + scrolltype + " on page");
        try {
            switch (scrolltype.toUpperCase()) {
                case "PAGEDOWN":
                    js.executeScript("document.body.scrollTo(0,document.body.scrollHeight)");
                    break;
                case "PAGEUP":
                    js.executeScript("document.body.scrollTo(0,-document.body.scrollHeight)");
                    break;
                case "MYLOCATION":
                    js.executeScript("document.body.scrollBy(\"" + xAxis + "\"", "" + Yaxis + "\")");
                    break;
                default:
            }
        } catch (Exception e) {
        }
    }

    /**
     * scroll to a specific element location using java script
     */
    public void scrollToMiddleViewOfElement(WebElement locator) {
        String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                + "var elementTop = arguments[0].getBoundingClientRect().top;"
                + "window.scrollBy(0, elementTop-(viewPortHeight/2));";

        js.executeScript(scrollElementIntoMiddle, locator);
    }

    /**
     * scroll to a specific element location using java script
     */
    public void scrollpageToSpecificElement(WebElement locator) {
        js.executeScript("arguments[0].scrollIntoView(true);", locator);
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
     * Modified version devised by Nauman Shahid (02/15/2023)
     */
    public void scrollElementToPageDetailCenter(WebElement element) {
        //Console log  Attempting to scroll element to page center with JavaScript");
        boolean isIeBased = EnvironmentFactory.getBrowsingDevice().toLowerCase().contains("ie");
        String scrollElementIntoMiddle = String.format(""
                + "function isScrollable(element) {"
                + "    var styles = ("
                + "        document.defaultView && document.defaultView.getComputedStyle ? "
                + "        document.defaultView.getComputedStyle(element, null) : element.currentStyle"
                + "    );"
                + "    var scrollValue = {"
                + "        auto: true,"
                + "        scroll: true,"
                + "        visible: false,"
                + "        hidden: false"
                + "    };"
                + "    var overflow = {"
                + "        y: scrollValue[styles.overflowY.toLowerCase()] || false,"
                + "    };"
                + "    if (!overflow.y) {"
                + "        return false;"
                + "    }"
                + "    var size = {"
                + "        height: {"
                + "            scroll: element.scrollHeight,"
                + "            client: element.clientHeight"
                + "        },"
                + "        scrollableY: function () {"
                + "            return overflow.y && this.height.scroll > this.height.client;"
                + "        }"
                + "    };"
                + "    return size.scrollableY();"
                + "}"
                + "var el = arguments[0];"
                + "if (el.tagName.toUpperCase() !== 'HTML') {"
                + "    var total = el.offsetTop;"
                + "    var parentEl = el.parentElement;"
                + "    var offsetParentEl = el.offsetParent;"
                + "    while(parentEl.tagName.toUpperCase() !== 'HTML') {"
                + "        if (isScrollable(parentEl)) {"
                + "            if (parentEl === offsetParentEl) {"
                + "                parentEl.scrollTop = total - (parentEl.clientHeight/2.0);"
                + "                offsetParentEl = parentEl.offsetParent;"
                + "            } else {"
                + "                parentEl.scrollTop = total - parentEl.offsetTop - (parentEl.clientHeight/2.0);"
                + "            }"
                + "            total = parentEl.offsetTop + (parentEl.clientHeight/2.0);"
                + "        } else if (parentEl === offsetParentEl) {"
                + "            total = total + parentEl.offsetTop;"
                + "            offsetParentEl = parentEl.offsetParent;"
                + "        }"
                + "        el = parentEl;"
                + "        parentEl = parentEl.parentElement;"
                + "    }"
                + "    %s.scrollTop = total-(parentEl.clientHeight/2.0);"
                + "}", isIeBased ? "el" : "parentEl");

        try {
            js.executeScript(scrollElementIntoMiddle, element);
        } catch (Exception e) {
            /* Ignore Exception */
        }
    }

    // only for super Element use
    public void scrollElementToPageDetailCenter(By locator) {
        iframeAnalyzer.setUpLocator(locator);
        try {
            WebElement element = device.getDriver().findElement(locator);
            this.scrollElementToPageDetailCenter(element);
        } catch (Exception e) {
        }
    }

    /**
     * Input to any field using java script
     */
    public void InputJSByWebElement(WebElement element, String input) {
        reportController.write(LogLevel.INFO, "Entering text with java script");
        try {
            js.executeScript("arguments[0].value=\"" + input + "\"", element);
        } catch (Exception e) {
            reportController.write(LogLevel.INFO, ConsoleFormatter.setTextColor(ConsoleFormatter.COLOR.RED, "X: ") + "Unable to use JavaScript due to : " + e.getMessage());
        }
    }

    /**
     * Zoom in or out using java script
     */
    public void ZoomInOutJS(int zoom) {
        reportController.write(LogLevel.INFO, "Console log Zooming in");
        js.executeScript("document.body.style.zoom='" + zoom + "'");
    }

    /**
     * @return status of the page load like Completed , loading , Interactive or No Status / FAILED TO GET STATUS
     * @author nauman.shahid
     */
    public String getPageLoadStatus() {
        String myPageStatus = "No status";
        try {
            myPageStatus = js.executeScript("return document.readyState").toString();
        } catch (Exception e) {
            myPageStatus = "Failed to get any status";
        }
        return myPageStatus;
    }

    /**
     * @return status of the page load like Completed , loading , Interactive
     */
    public String getJQueryStatus() {
        reportController.write(LogLevel.INFO, "fetching jquery status");
        String myPageStatus = "Not available";
        try {
            myPageStatus = js.executeScript("return jQuery.active").toString();
        } catch (Exception e) {
            reportController.write(LogLevel.ERROR, "Unable to get page status");
        }
        reportController.write(LogLevel.INFO, "Page load status is " + myPageStatus.toUpperCase());
        return myPageStatus;
    }

    /**
     * move to any specific webelement
     *
     * @param element
     * @author monu.kumar
     */
    public void moveToElement(WebElement element) {
        js.executeScript("if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover',true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover')};", element);

    }

    /**
     * Get current double value of current off set or movement of the page from original position
     *
     * @return double y-axis
     * @author nauman.shahid
     */
    public Long getPageOffSetXAxis() {
        return  (Long)js.executeScript("return window.pageXOffset");
    }

    /**
     * Get current double value of current off set or movement of the page from original position
     *
     * @return double y-axis
     * @author nauman.shahid
     */
    public Long getPageOffSetYAxis() {
        return (Long) js.executeScript("return window.pageYOffset");
    }

    /**
     * Drag an HTML5 element to another HTML5 element
     * Sourced with minor modifications from: https://gist.github.com/rcorreia/2362544
     *
     * @author john.phillips
     */
    public void dragAndDropInHtml5(WebElement source, WebElement target) {
        executeJSCommand().executeScript(""
                        + "(function( $ ) {\n"
                        + "        $.fn.simulateDragDrop = function(options) {\n"
                        + "                return this.each(function() {\n"
                        + "                        new $.simulateDragDrop(this, options);\n"
                        + "                });\n"
                        + "        };\n"
                        + "        $.simulateDragDrop = function(elem, options) {\n"
                        + "                this.options = options;\n"
                        + "                this.simulateEvent(elem, options);\n"
                        + "        };\n"
                        + "        $.extend($.simulateDragDrop.prototype, {\n"
                        + "                simulateEvent: function(elem, options) {\n"
                        + "                        /*Simulating drag start*/\n"
                        + "                        var type = 'dragstart';\n"
                        + "                        var event = this.createEvent(type);\n"
                        + "                        this.dispatchEvent(elem, type, event);\n"
                        + "\n"
                        + "                        /*Simulating drop*/\n"
                        + "                        type = 'drop';\n"
                        + "                        var dropEvent = this.createEvent(type, {});\n"
                        + "                        dropEvent.dataTransfer = event.dataTransfer;\n"
                        + "                        this.dispatchEvent($(options.dropTarget)[0], type, dropEvent);\n"
                        + "\n"
                        + "                        /*Simulating drag end*/\n"
                        + "                        type = 'dragend';\n"
                        + "                        var dragEndEvent = this.createEvent(type, {});\n"
                        + "                        dragEndEvent.dataTransfer = event.dataTransfer;\n"
                        + "                        this.dispatchEvent(elem, type, dragEndEvent);\n"
                        + "                },\n"
                        + "                createEvent: function(type) {\n"
                        + "                        var event = document.createEvent(\"CustomEvent\");\n"
                        + "                        event.initCustomEvent(type, true, true, null);\n"
                        + "                        event.dataTransfer = {\n"
                        + "                                data: {\n"
                        + "                                },\n"
                        + "                                setData: function(type, val){\n"
                        + "                                        this.data[type] = val;\n"
                        + "                                },\n"
                        + "                                getData: function(type){\n"
                        + "                                        return this.data[type];\n"
                        + "                                }\n"
                        + "                        };\n"
                        + "                        return event;\n"
                        + "                },\n"
                        + "                dispatchEvent: function(elem, type, event) {\n"
                        + "                        if(elem.dispatchEvent) {\n"
                        + "                                elem.dispatchEvent(event);\n"
                        + "                        }else if( elem.fireEvent ) {\n"
                        + "                                elem.fireEvent(\"on\"+type, event);\n"
                        + "                        }\n"
                        + "                }\n"
                        + "        });\n"
                        + "})(jQuery);"
                        + "$(arguments[0]).simulateDragDrop({ dropTarget: arguments[1]});",
                source, target
        );
    }

    /**
     * execute any javascript command
     *
     * @return
     */
    public JavascriptExecutor executeJSCommand() {
        return js;
    }

}
