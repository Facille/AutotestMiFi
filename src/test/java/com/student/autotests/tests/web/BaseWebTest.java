package com.student.autotests.tests.web;

import com.student.autotests.config.TestConfig;
import com.student.autotests.driver.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public abstract class BaseWebTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = WebDriverFactory.createDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get(TestConfig.webBaseUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
