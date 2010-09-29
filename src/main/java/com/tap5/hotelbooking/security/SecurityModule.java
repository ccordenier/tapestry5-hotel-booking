package com.tap5.hotelbooking.security;

import org.apache.shiro.realm.Realm;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.tynamo.security.FilterChainDefinition;
import org.tynamo.security.SecuritySymbols;

/**
 * Security configuration sub-module. Add all security relation configuration in this class
 * 
 * @author karesti
 * @version 1.0
 */
public class SecurityModule
{

    public static void bind(ServiceBinder binder)
    {

        binder.bind(SecurityRealm.class, BasicSecurityRealm.class);
    }

    public static void contributeSecurityRequestFilter(
            OrderedConfiguration<FilterChainDefinition> configuration)
    {
        configuration.add("booking-user", new FilterChainDefinition("/contact**", "authc"));
        configuration.add("booking-user", new FilterChainDefinition("/booking/**", "authc"));
        configuration.add("user-user", new FilterChainDefinition("/user/**", "authc"));
    }

    public static void contributeFactoryDefaults(MappedConfiguration<String, String> configuration)
    {

        configuration.override(SecuritySymbols.LOGIN_URL, "/signin");
        configuration.override(SecuritySymbols.SUCCESS_URL, "/index");
        // configuration.add(SecuritySymbols.UNAUTHORIZED_URL, "/unauthorized");
        // configuration.add(SecuritySymbols.DEFAULTSIGNINPAGE, "/defaultSignInP");
    }

    public static void contributeWebSecurityManager(Configuration<Realm> configuration,
            @Inject SecurityRealm realm)
    {

        configuration.add(realm);
    }

}
