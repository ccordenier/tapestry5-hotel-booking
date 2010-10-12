package com.tap5.hotelbooking.test.integration;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

/**
 * Check some hotel search on search page.
 * 
 * @author ccordenier
 */
public class SearchPageTest extends BaseIntegrationTestSuite
{
    @Test(testName = "signin")
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
    @Test(testName = "wildcardSearch", dependsOnMethods = "signin")
    public void wildcardSearch()
    {
        open("/search");
        waitForPageToLoad();

        type("query", "*");
        click("//form[@id='searchForm']//input[@type='submit']");
        waitForCondition("selenium.getXpathCount(\"//div[@class='t-data-grid-pager']/a\") == 2", "15000");
    }

    @AfterClass(alwaysRun = true)
    @Override
    public void cleanup()
    {
        open("/search");
        waitForPageToLoad();
        
        Assert.assertTrue(
                isElementPresent("id=logout"),
                "Authenticated user should be able to logout");
        click("id=logout");
        super.cleanup();
    }

}
