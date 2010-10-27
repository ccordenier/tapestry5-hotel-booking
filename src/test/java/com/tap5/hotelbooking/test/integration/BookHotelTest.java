package com.tap5.hotelbooking.test.integration;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Check some hotel search on search page.
 * 
 * @author ccordenier
 */
public class BookHotelTest extends BaseIntegrationTestSuite
{
    @Test(priority = 1, groups="booking")
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
    @Test(priority = 2, groups="booking")
    public void bookProcess()
    {
        open("/view/1");
        waitForPageToLoad();

        assertTrue(isTextPresent("Marriott Courtyard"));

        click("//form[@id='startBookingForm']//input[@type='submit']");
        waitForPageToLoad();
        assertEquals(
                getXpathCount("//ul[@id='workspace']/li"),
                1,
                "There is only one booking in progress");

        // Set user information
        type("id=creditCardNumber", "1234567812345678");
        select("id=creditCardType", "value=MasterCard");
        click("//form[@id='bookingForm']//input[@type='submit']");
        waitForPageToLoad();

        // Confirm reservation
        assertTrue(isTextPresent("129"));
        click("//form[@id='confirmForm']//input[@type='submit']");
        waitForPageToLoad();

        // Cancel confirmation
        assertEquals(getXpathCount("//ul[@id='your-bookings']/li"), 1);
        click("//ul[@id='your-bookings']/li/a");
        waitForPageToLoad();
        assertFalse(isElementPresent("id=your-bookings"));
    }

    @Test(priority = 3, groups="booking")
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
