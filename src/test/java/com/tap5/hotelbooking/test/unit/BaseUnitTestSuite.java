package com.tap5.hotelbooking.test.unit;

import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.test.PageTester;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Base test case class that provides the necessary to load tapestry services
 * 
 * @author karesti
 */
@Test
public abstract class BaseUnitTestSuite
{

    /**
     * Use this page tester to unit test pages.
     */
    protected PageTester pageTester;

    /**
     * Use this registry to get access to Tapestry services
     */
    protected Registry registry;

    @BeforeClass
    public void setup()
    {
        pageTester = new PageTester("com.tap5.hotelbooking", "hotel-booking", "src/main/webapp");

        registry = pageTester.getRegistry();
    }

    @AfterClass
    public void cleanup()
    {
        if (registry != null)
        {
            registry.shutdown();
        }
    }
}
