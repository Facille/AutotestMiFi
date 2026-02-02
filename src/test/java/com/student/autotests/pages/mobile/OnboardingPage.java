package com.student.autotests.pages.mobile;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OnboardingPage {

    private static final By SKIP_BUTTON = AppiumBy.id("org.wikipedia:id/fragment_onboarding_skip_button");

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    public OnboardingPage(AndroidDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void skipIfPresent() {
        if (driver == null || wait == null) return;
        try {
            if (!driver.findElements(SKIP_BUTTON).isEmpty()) {
                wait.until(ExpectedConditions.elementToBeClickable(SKIP_BUTTON)).click();
            }
        } catch (TimeoutException ignored) {
        }
        try {
            if (!driver.findElements(GOT_IT_BUTTON).isEmpty()) {
                wait.until(ExpectedConditions.elementToBeClickable(GOT_IT_BUTTON)).click();
            }
        } catch (TimeoutException ignored) {}

    }

    private static final By GOT_IT_BUTTON = AppiumBy.androidUIAutomator(
            "new UiSelector().textContains(\"Got it\")"
    );
}
