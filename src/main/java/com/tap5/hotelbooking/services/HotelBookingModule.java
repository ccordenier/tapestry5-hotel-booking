package com.tap5.hotelbooking.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.services.ComponentRequestFilter;
import org.apache.tapestry5.services.ComponentRequestHandler;
import org.apache.tapestry5.validator.ValidatorMacro;

import com.tap5.hotelbooking.dal.DataModule;
import com.tap5.hotelbooking.dal.HibernateModule;
import com.tap5.hotelbooking.security.AuthenticationFilter;

/**
 * This module is automatically included as part of the Tapestry IoC Registry, it's a good place to
 * configure and extend Tapestry, or to place your own service definitions.
 */
@SubModule(
{ HibernateModule.class, DataModule.class })
public class HotelBookingModule
{
    public static void bind(ServiceBinder binder)
    {
        binder.bind(Authenticator.class, BasicAuthenticator.class);
    }

    @ApplicationDefaults
    @Contribute(SymbolProvider.class)
    public static void configureTapestryHotelBooking(
            MappedConfiguration<String, String> configuration)
    {

        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en");
        configuration.add(SymbolConstants.APPLICATION_VERSION, "1.2-SNAPSHOT");
    }

    @Contribute(ValidatorMacro.class)
    public static void combineValidators(MappedConfiguration<String, String> configuration)
    {
        configuration.add("username", "required, minlength=3, maxlength=15");
        configuration.add("password", "required, minlength=6, maxlength=12");
    }

    @Contribute(ComponentRequestHandler.class)
    public static void contributeComponentRequestHandler(
            OrderedConfiguration<ComponentRequestFilter> configuration)
    {
        configuration.addInstance("RequiresLogin", AuthenticationFilter.class);
    }
}
