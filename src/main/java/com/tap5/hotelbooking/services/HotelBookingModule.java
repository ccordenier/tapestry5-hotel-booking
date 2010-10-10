package com.tap5.hotelbooking.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.services.ComponentRequestHandler;
import org.apache.tapestry5.validator.ValidatorMacro;

import com.tap5.conversation.ConversationConstants;
import com.tap5.conversation.services.ConversationModule;
import com.tap5.hotelbooking.domain.HibernateModule;
import com.tap5.hotelbooking.security.RequiresLoginFilter;

/**
 * This module is automatically included as part of the Tapestry IoC Registry, it's a good place to
 * configure and extend Tapestry, or to place your own service definitions.
 */
@SubModule(
{ HibernateModule.class, DemoDataModule.class, ConversationModule.class })
public class HotelBookingModule
{
    public static void bind(ServiceBinder binder)
    {
        binder.bind(Authenticator.class, BasicAuthenticator.class);
    }

    public static void contributeApplicationDefaults(
            MappedConfiguration<String, String> configuration)
    {

        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en");
        configuration.add(SymbolConstants.APPLICATION_VERSION, "1.0-SNAPSHOT");
        configuration.add(ConversationConstants.DEFAULT_REDIRECT, "Search");
    }

    @Contribute(ValidatorMacro.class)
    public static void combineValidators(MappedConfiguration<String, String> configuration)
    {
        configuration.add("username", "required, minlength=3, maxlength=15");
        configuration.add("password", "required, minlength=6, maxlength=12");
    }

    @SuppressWarnings(
    { "rawtypes", "unchecked" })
    @Contribute(ComponentRequestHandler.class)
    public static void contributeComponentRequestHandler(OrderedConfiguration configuration)
    {
        configuration.addInstance("RequiresLogin", RequiresLoginFilter.class);
    }
}
