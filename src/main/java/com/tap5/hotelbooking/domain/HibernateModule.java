package com.tap5.hotelbooking.domain;

import org.apache.tapestry5.beanvalidator.BeanValidatorConfigurer;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.OrderedConfiguration;

/**
 * This class should contain contribution to data stuff (hibernate configuration, beanvalidators...)
 */
public class HibernateModule
{
    public static void contributeHibernateEntityPackageManager(Configuration<String> configuration)
    {
        configuration.add("com.tap5.hotelbooking.domain.entities");
    }

    public static void contributeBeanValidatorSource(
            OrderedConfiguration<BeanValidatorConfigurer> configuration)
    {
        configuration.add("HotelBookingConfigurer", new BeanValidatorConfigurer()
        {
            public void configure(javax.validation.Configuration<?> configuration)
            {
                configuration.ignoreXmlConfiguration();
            }
        });
    }

}
