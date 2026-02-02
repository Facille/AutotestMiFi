package com.student.autotests.pages.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WikipediaArticlePage extends BaseWebPage {

    private static final By ARTICLE_TITLE = By.id("firstHeading");
    private static final By CONTENTS_TABLE = By.id("vector-toc");

    public WikipediaArticlePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public String title() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(ARTICLE_TITLE)).getText();
    }

    public boolean hasContentsTable() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(CONTENTS_TABLE)).isDisplayed();
    }
}
