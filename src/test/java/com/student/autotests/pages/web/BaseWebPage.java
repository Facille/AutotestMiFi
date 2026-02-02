package com.student.autotests.pages.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BaseWebPage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BaseWebPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }
}
