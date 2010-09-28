package com.tap5.hotelbooking.app0.services;

import org.apache.tapestry5.hibernate.HibernateSymbols;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.services.LibraryMapping;

/**
 * Test application module.
 * 
 * @author karesti
 */
@SubModule(TestModule.class)
public class AppModule
{

    public static void contributeComponentClassResolver(Configuration<LibraryMapping> configuration)
    {
        configuration.add(new LibraryMapping("app0", "com.tap5.hotelbooking.app0"));
    }

    public static void contributeApplicationDefauls(
            MappedConfiguration<String, String> configuration)
    {
        configuration.add(HibernateSymbols.DEFAULT_CONFIGURATION, "false");
    }
}
