package com.student.autotests.tests.web;

import com.student.autotests.pages.web.WikipediaArticlePage;
import com.student.autotests.pages.web.WikipediaMainPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.By;


public class WikipediaWebTests extends BaseWebTest {

    @Test(groups = "web")
    public void openEnglishMainPageShowsWelcomeBlock() {
        WikipediaMainPage mainPage = new WikipediaMainPage(driver, wait);
        Assert.assertTrue(mainPage.isWelcomeMessageVisible(), "English main page welcome block should be visible");
    }

    @Test(groups = "web")
    public void searchOpensExactArticle() {
        WikipediaMainPage mainPage = new WikipediaMainPage(driver, wait);
        WikipediaArticlePage articlePage = mainPage.searchFor("Appium");

        Assert.assertTrue(articlePage.title().contains("Appium"),
                "Article title should contain query");
    }



    @Test(groups = "web")
    public void featuredArticleSectionHasTitle() {
        WikipediaMainPage mainPage = new WikipediaMainPage(driver, wait);
        String featuredTitle = mainPage.featuredArticleTitle();
        Assert.assertFalse(featuredTitle.isBlank(), "Featured article title should not be empty");
    }

    @Test(groups = "web")
    public void randomArticleShowsHeadingAndContents() {
        WikipediaMainPage mainPage = new WikipediaMainPage(driver, wait);
        WikipediaArticlePage articlePage = mainPage.openRandomArticle();

        Assert.assertFalse(articlePage.title().isBlank(), "Random article should have a heading");
        Assert.assertTrue(driver.getCurrentUrl().contains("/wiki/"), "Random article URL should contain /wiki/");
    }
}
