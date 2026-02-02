package com.student.autotests.tests.mobile;

import com.student.autotests.pages.mobile.ArticlePage;
import com.student.autotests.pages.mobile.OnboardingPage;
import com.student.autotests.pages.mobile.SearchPage;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class WikipediaMobileTests extends BaseMobileTest {

    private SearchPage searchPage;
    private OnboardingPage onboardingPage;

    @BeforeMethod(alwaysRun = true)
    public void initPages() {
        if (driver == null || wait == null) {
            throw new SkipException("Skipping mobile tests because Appium driver is not initialized");
        }
        onboardingPage = new OnboardingPage(driver, wait);
        searchPage = new SearchPage(driver, wait);
        onboardingPage.skipIfPresent();
    }

    @Test(groups = "mobile")
    public void searchShowsTitlesAndDescriptions() {
        searchPage.openSearch();
        searchPage.typeQuery("Appium");

        List<String> titles = searchPage.resultTitleTexts();
        Assert.assertFalse(titles.isEmpty(), "Search results should not be empty");

        Assert.assertFalse(
                searchPage.resultDescriptions().isEmpty(),
                "Descriptions should be present for results"
        );
    }


    @Test(groups = "mobile")
    public void openArticleFromSearch() {
        searchPage.openSearch();
        searchPage.typeQuery("Software testing");

        String selectedTitle = searchPage.openResultWithText("Software testing");
        ArticlePage articlePage = new ArticlePage(driver, wait);
        String openedTitle = articlePage.waitForTitleContaining(selectedTitle);
        Assert.assertTrue(
                openedTitle.toLowerCase().contains(selectedTitle.toLowerCase())
                        || selectedTitle.toLowerCase().contains(openedTitle.toLowerCase()),
                "Opened article should match selected search result");
    }

    @Test(groups = "mobile")
    public void clearSearchResetsResults() {
        searchPage.openSearch();
        searchPage.typeQuery("Appium");
        Assert.assertFalse(searchPage.resultTitles().isEmpty(), "Results should appear after typing query");

        searchPage.clearSearch();
        searchPage.waitResultsDisappear();
        Assert.assertEquals(searchPage.resultTitlesCount(), 0, "Results should disappear after clearing search");
    }
}
