package com.tap5.hotelbooking.test.integration;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Check some hotel search on search page.
 * 
 * @author ccordenier
 */
public class SearchPageTest extends BaseIntegrationTestSuite
{
    @Test(priority = 10, groups="search")
    public void signin()
    {
        open("/signin");
        waitForPageToLoad();

        type("id=username", "cordenier");
        type("id=password", "cordenier");
        click("//form[@id='loginForm']//input[@type='submit']");
        waitForPageToLoad();

        // Verify if search form is present
        assertTrue(isElementPresent("//form[@id='searchForm']"));
    }

    /**
     * Check wildcards
     */
    @Test(priority = 11, groups="search")
    public void wildcardSearch()
    {
        open("/search");
        waitForPageToLoad();

        type("query", "*");
        select("id=rowsPerPage", "value=10");

        click("//form[@id='searchForm']//input[@type='submit']");
        waitForCondition(
                "selenium.getXpathCount(\"//div[@class='t-data-grid-pager']/a\") == 2",
                "15000");
    }

    /**
     * Check wildcards with 20 results per page
     */
    @Test(priority = 12, groups="search")
    public void changePagePerResult()
    {
        open("/search");
        waitForPageToLoad();

        type("query", "*");
        select("id=rowsPerPage", "value=20");
        click("//form[@id='searchForm']//input[@type='submit']");
        waitForCondition(
                "selenium.getXpathCount(\"//div[@class='t-data-grid-pager']/a\") == 1",
                "15000");
    }

    @Test(priority = 13, groups="search")
    public void logout()
    {
        open("/search");
        waitForPageToLoad();

        Assert.assertTrue(
                isElementPresent("id=logout"),
                "Authenticated user should be able to logout");
        click("id=logout");
        waitForPageToLoad();
    }

}
