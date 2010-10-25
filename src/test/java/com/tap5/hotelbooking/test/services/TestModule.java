package com.tap5.hotelbooking.test.services;

import org.apache.tapestry5.hibernate.HibernateSymbols;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.services.LibraryMapping;

/**
 * Test application module.
 * 
 * @author karesti
 */
public class TestModule
{

    public static void contributeComponentClassResolver(Configuration<LibraryMapping> configuration)
    {
        configuration.add(new LibraryMapping("test", "com.tap5.hotelbooking.test"));
    }

    public static void contributeApplicationDefauls(
            MappedConfiguration<String, String> configuration)
    {
        configuration.add(HibernateSymbols.DEFAULT_CONFIGURATION, "false");
    }
}
