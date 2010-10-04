package com.tap5.hotelbooking.services;

import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.validator.ValidatorMacro;
import org.tynamo.security.FilterChainDefinition;
import org.tynamo.security.SecuritySymbols;
import org.tynamo.security.services.SecurityModule;

import com.tap5.hotelbooking.domain.HibernateModule;
import com.tap5.hotelbooking.security.BasicSecurityRealm;

/**
 * This module is automatically included as part of the Tapestry IoC Registry, it's a good place to
 * configure and extend Tapestry, or to place your own service definitions.
 */
@SubModule(
{ HibernateModule.class, SecurityModule.class })
public class HotelBookingModule
{
    public static void bind(ServiceBinder binder)
    {

        binder.bind(AuthorizingRealm.class, BasicSecurityRealm.class);
    }

    public static void contributeFactoryDefaults(MappedConfiguration<String, String> configuration)
    {

        configuration.override(SecuritySymbols.LOGIN_URL, "/signin");
        configuration.override(SecuritySymbols.SUCCESS_URL, "/search");
        configuration.override(SecuritySymbols.DEFAULTSIGNINPAGE, "/signin");
        configuration.override(SecuritySymbols.SHOULD_LOAD_INI_FROM_CONFIG_PATH, "false");
    }

    public static void contributeApplicationDefaults(
            MappedConfiguration<String, String> configuration)
    {

        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en");
        configuration.add(SymbolConstants.APPLICATION_VERSION, "1.0-SNAPSHOT");

    }

    @Contribute(ValidatorMacro.class)
    public static void combineValidators(MappedConfiguration<String, String> configuration)
    {
        configuration.add("username", "required, minlength=3, maxlength=15");
        configuration.add("password", "required, minlength=6, maxlength=12");
    }

    public static void contributeWebSecurityManager(Configuration<Realm> configuration,
            @Inject AuthorizingRealm realm)
    {
        configuration.add(realm);
    }

    public static void contributeSecurityRequestFilter(
            OrderedConfiguration<FilterChainDefinition> configuration)
    {
        configuration.add("assets-anon", new FilterChainDefinition("/assets**", "anon"));
        configuration.add("index-anon", new FilterChainDefinition("/index**", "anon"));
        configuration.add("signup-anon", new FilterChainDefinition("/signup**", "anon"));
        configuration.add("signin-anon", new FilterChainDefinition("/signin**", "anon"));
        configuration.add("user-booking", new FilterChainDefinition("/**", "authc"));
    }
}
