package com.tap5.hotelbooking.test.integration;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

/**
 * Base test case class that provides the necessary to execute the integration tests
 * 
 * @author karesti
 */
@Test
public abstract class BaseIntegrationTestSuite extends SeleniumTestCase
{

    /**
     * Default browser used to launch tests.
     */
    @SuppressWarnings("unused")
    private static String defaultBrowser;

    // Set a default browser in function of the OS.
    static
    {
        String os = System.getProperty("os.name");
        if (os.contains("Mac OS"))
        {
            defaultBrowser = "*safari";
        }
        else
        {
            if (os.contains("Windows"))
            {
                defaultBrowser = "*googlechrome";
            }
            else
            {
                defaultBrowser = "*firefox";
            }
        }
    }

}
