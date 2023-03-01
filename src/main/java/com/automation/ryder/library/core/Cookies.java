package com.automation.ryder.library.core;

import com.automation.ryder.controller.browsingdevices.BrowsingDeviceBucket;
import com.automation.ryder.controller.configuration.DeviceDriver;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.util.Set;

public final class Cookies {
    WebDriver driver;

    public Cookies(BrowsingDeviceBucket device) {
        this.driver = device.getDriver();
    }

    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }

    public void deleteCookie(String name) {
        driver.manage().deleteCookieNamed(name);
    }

    public void addCookie(Cookie cookie) {
        driver.manage().addCookie(cookie);
    }

    public Cookie getCookie(String name) {
        return driver.manage().getCookieNamed(name);
    }

    public Set<Cookie> getAllCookies() {
        return driver.manage().getCookies();
    }
}
