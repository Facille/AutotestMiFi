package com.student.autotests.pages.mobile;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ArticlePage {

    private static final By ARTICLE_TITLE = AppiumBy.id("org.wikipedia:id/view_page_title_text");
    private static final By ARTICLE_TITLE_ALT = AppiumBy.id("org.wikipedia:id/page_title_text");
    private static final By ARTICLE_TOOLBAR_TITLE = AppiumBy.id("org.wikipedia:id/page_toolbar_title");
    private static final By ARTICLE_TITLE_HEADER = AppiumBy.id("org.wikipedia:id/page_header_title");
    private static final By ARTICLE_TITLE_HEADER_ALT = AppiumBy.id("org.wikipedia:id/view_page_header_title");
    private static final By ARTICLE_PROGRESS = AppiumBy.id("org.wikipedia:id/page_load_progress");
    private static final List<String> REFERENCES_TEXTS = List.of(
            "References",
            "Reference",
            "Источники",
            "Примечания",
            "Sources"
    );

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    public ArticlePage(AndroidDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public String title() {
        return waitForTitleContaining(null);
    }

    public String waitForTitleContaining(String expectedText) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(ARTICLE_PROGRESS));
        List<By> candidates = new ArrayList<>(List.of(
                ARTICLE_TITLE,
                ARTICLE_TITLE_ALT,
                ARTICLE_TOOLBAR_TITLE,
                ARTICLE_TITLE_HEADER,
                ARTICLE_TITLE_HEADER_ALT,
                AppiumBy.androidUIAutomator("new UiSelector().resourceIdMatches(\".*title\")")
        ));

        if (expectedText != null && !expectedText.isBlank()) {
            candidates.add(AppiumBy.androidUIAutomator("new UiSelector().textContains(\"" + expectedText + "\")"));
            candidates.add(AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"" + expectedText + "\")"));
        }

        WebElement title = wait.until(driver -> firstDisplayedElement(driver, candidates));
        return title.getText();
    }

    public void scrollToText(String text) {
        scrollToAnyText(() -> List.of(text), "Текст '" + text + "' не найден после скролла");
    }

    public void scrollToReferencesSection() {
        scrollToAnyText(() -> REFERENCES_TEXTS,
                "Текст '" + String.join("' или '", REFERENCES_TEXTS) + "' не найден после скролла");
    }

    public boolean isReferencesVisible() {
        return isAnyTextVisible(REFERENCES_TEXTS);
    }

    public boolean isTextVisible(String text) {
        List<WebElement> elements = driver.findElements(AppiumBy.androidUIAutomator("new UiSelector().textContains(\"" + text + "\")"));
        return elements.stream().anyMatch(WebElement::isDisplayed);
    }

    private boolean isAnyTextVisible(List<String> texts) {
        return texts.stream().anyMatch(this::isTextVisible);
    }

    private void performScrollGesture() {
        var size = driver.manage().window().getSize();
        driver.executeScript("mobile: scrollGesture", Map.of(
                "left", 0,
                "top", (int) (size.height * 0.1),
                "width", size.width,
                "height", (int) (size.height * 0.8),
                "direction", "up",
                "percent", 0.85
        ));
    }

    private WebElement firstDisplayedElement(org.openqa.selenium.WebDriver driver, List<By> candidates) {
        for (By candidate : candidates) {
            List<WebElement> elements = driver.findElements(candidate);
            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    return element;
                }
            }
        }
        return null;
    }

    private void scrollToAnyText(Supplier<List<String>> textsSupplier, String errorMessage) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(ARTICLE_PROGRESS));
        List<String> texts = textsSupplier.get();
        for (int attempt = 0; attempt < 12; attempt++) {
            if (isAnyTextVisible(texts)) {
                return;
            }
            performScrollGesture();
        }

        if (!scrollWithUiScrollable(texts) && !isAnyTextVisible(texts)) {
            throw new IllegalStateException(errorMessage);
        }
    }

    private boolean scrollWithUiScrollable(List<String> texts) {
        for (String text : texts) {
            try {
                driver.findElement(AppiumBy.androidUIAutomator(
                        "new UiScrollable(new UiSelector().scrollable(true)).setMaxSearchSwipes(25).scrollIntoView("
                                + "new UiSelector().textContains(\"" + text + "\"))"));
                if (isTextVisible(text)) {
                    return true;
                }
            } catch (Exception ignored) {
            }
        }
        return false;
    }
}
