package com.tap5.hotelbooking.services;

import java.io.IOException;

import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.validator.ValidatorMacro;
import org.slf4j.Logger;
import org.tynamo.security.FilterChainDefinition;
import org.tynamo.security.SecuritySymbols;
import org.tynamo.security.services.SecurityModule;

import com.tap5.hotelbooking.domain.HibernateModule;
import com.tap5.hotelbooking.domain.entities.Hotel;
import com.tap5.hotelbooking.domain.entities.User;
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
        configuration.override(SecuritySymbols.SUCCESS_URL, "/index");
        // configuration.add(SecuritySymbols.UNAUTHORIZED_URL, "/unauthorized");
        // configuration.add(SecuritySymbols.DEFAULTSIGNINPAGE, "/defaultSignInP");
    }
    
    public static void contributeApplicationDefaults(
            MappedConfiguration<String, String> configuration)
    {

        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en");
        configuration.add(SymbolConstants.APPLICATION_VERSION, "1.0-SNAPSHOT");
        // configuration.add(SecuritySymbols.SHOULD_LOAD_INI_FROM_CONFIG_PATH, "true");
    }

    /**
     * This is a service definition, the service will be named "TimingFilter". The interface,
     * RequestFilter, is used within the RequestHandler service pipeline, which is built from the
     * RequestHandler service configuration. Tapestry IoC is responsible for passing in an
     * appropriate Logger instance. Requests for static resources are handled at a higher level, so
     * this filter will only be invoked for Tapestry related requests.
     * <p>
     * Service builder methods are useful when the implementation is inline as an inner class (as
     * here) or require some other kind of special initialization. In most cases, use the static
     * bind() method instead.
     * <p>
     * If this method was named "build", then the service id would be taken from the service
     * interface and would be "RequestFilter". Since Tapestry already defines a service named
     * "RequestFilter" we use an explicit service id that we can reference inside the contribution
     * method.
     */
    public RequestFilter buildTimingFilter(final Logger log)
    {
        return new RequestFilter()
        {
            public boolean service(Request request, Response response, RequestHandler handler)
                    throws IOException
            {
                long startTime = System.currentTimeMillis();

                try
                {
                    // The responsibility of a filter is to invoke the corresponding method
                    // in the handler. When you chain multiple filters together, each filter
                    // received a handler that is a bridge to the next filter.

                    return handler.service(request, response);
                }
                finally
                {
                    long elapsed = System.currentTimeMillis() - startTime;

                    log.info(String.format("Request time: %d ms", elapsed));
                }
            }
        };
    }

    /**
     * This is a contribution to the RequestHandler service configuration. This is how we extend
     * Tapestry using the timing filter. A common use for this kind of filter is transaction
     * management or security. The @Local annotation selects the desired service by type, but only
     * from the same module. Without @Local, there would be an error due to the other service(s)
     * that implement RequestFilter (defined in other modules).
     */
    public void contributeRequestHandler(OrderedConfiguration<RequestFilter> configuration,
            @Local RequestFilter filter)
    {
        // Each contribution to an ordered configuration has a name, When necessary, you may
        // set constraints to precisely control the invocation order of the contributed filter
        // within the pipeline.

        configuration.add("Timing", filter);
    }

    @Contribute(ValidatorMacro.class)
    public static void combineValidators(MappedConfiguration<String, String> configuration)
    {
        configuration.add("password", "required,minlength=6,maxlength=12");
    }
    
	public static void contributeWebSecurityManager(Configuration<Realm> configuration, @Inject AuthorizingRealm realm)
    {

        configuration.add(realm);
    }
    public static void contributeSecurityRequestFilter(
            OrderedConfiguration<FilterChainDefinition> configuration)
    {
        configuration.add("booking-user", new FilterChainDefinition("/contact**", "authc"));
        configuration.add("booking-user", new FilterChainDefinition("/booking/**", "authc"));
        configuration.add("user-user", new FilterChainDefinition("/user/**", "authc"));
    }
    
    public static void contributeSeedEntity(
            OrderedConfiguration<Object> configuration) {
        configuration.add("user1", new User("Christophe Cordenier",
                "cordenier", "ccordenier@example.com", "cordenier"));
        configuration.add("user2", new User("Katia Aresti", "karesti",
                "karesti@example.com", "karesti"));

        configuration.add("hotel1", new Hotel(129, 3, "Marriott Courtyard",
                "Tower Place, Buckhead", "Atlanta", "GA", "30305", "USA"));
        configuration.add("hotel2", new Hotel(84, 4,
                "Doubletree Atlanta-Buckhead", "3342 Peachtree Road NE",
                "Atlanta", "GA", "30326", "USA"));
        configuration.add("hotel3", new Hotel(289, 4,
                "W New York - Union Square", "201 Park Avenue South",
                "New York", "NY", "10003", "USA"));
        configuration.add("hotel4", new Hotel(219, 3, "W New York",
                "541 Lexington Avenue", "New York", "NY", "10022", "USA"));
        configuration.add("hotel5", new Hotel(250, 3, "Hotel Rouge",
                "1315 16th Street NW", "Washington", "DC", "20036", "USA"));
        configuration.add("hotel6", new Hotel(159, 4, "70 Park Avenue Hotel",
                "70 Park Avenue, 38th St", "New York", "NY", "10016", "USA"));
        configuration.add("hotel7",
                new Hotel(198, 4, "Parc 55", "55 Cyril Magnin Street",
                        "San Francisco", "CA", "94102", "USA"));
        configuration.add("hotel8", new Hotel(189, 4, "Conrad Miami",
                "1395 Brickell Ave", "Miami", "FL", "33131", "USA"));
        configuration.add("hotel9", new Hotel(111, 4, "Grand Hyatt",
                "345 Stockton Street", "San Francisco", "CA", "94108", "USA"));
        configuration.add("hotel10", new Hotel(54, 1,
                "Super 8 Eau Claire Campus Area", "1151 W MacArthur Ave",
                "Eau Claire", "WI", "54701", "USA"));
        configuration.add("hotel11", new Hotel(199, 4,
                "San Francisco Marriott", "55 Fourth Street", "San Francisco",
                "CA", "94103", "USA"));
        configuration.add("hotel11", new Hotel(543, 4, "Hilton Diagonal Mar",
                "Passeig del Taulat 262-264", "Barcelona", "Catalunya",
                "08019", "ES"));
        configuration.add("hotel12", new Hotel(335, 5, "Hilton Tel Aviv",
                "Independence Park", "Tel Aviv", null, "63405", "IL"));
        configuration.add("hotel13", new Hotel(242, 5,
                "InterContinental Hotel Tokyo Bay", "1-15-2 Kaigan", "Tokyo",
                "Minato", "105", "JP"));
        configuration.add("hotel14",
                new Hotel(130, 4, "Hotel Beaulac",
                        " Esplanade Léopold-Robert 2", "Neuchatel", null,
                        "2000", "CH"));
        configuration.add("hotel15", new Hotel(266, 5, "Conrad Treasury Place",
                "130 William Street", "Brisbane", "QL", "4001", "AU"));
        configuration.add("hotel16",
                new Hotel(170, 4, "Ritz-Carlton Montreal",
                        "1228 Sherbrooke St West", "Montreal", "Quebec",
                        "H3G1H6", "CA"));
        configuration.add("hotel17", new Hotel(179, 4, "Ritz-Carlton Atlanta",
                "181 Peachtree St NE", "Atlanta", "GA", "30303", "USA"));
        configuration.add("hotel18", new Hotel(145, 4, "Swissotel Sydney",
                "68 Market Street", "Sydney", "NSW", "2000", "AU"));
        configuration.add("hotel19", new Hotel(178, 4, "Meliá White House",
                "Albany Street Regents Park", "London", null, "NW13UP", "GB"));
        configuration.add("hotel20", new Hotel(159, 3, "Hotel Allegro",
                "171 W Randolph Street", "Chicago", "IL", "60601", "USA"));
        configuration.add("hotel21", new Hotel(296, 5, "Caesars Palace",
                "3570 Las Vegas Blvd S", "Las Vegas", "NV", "89109", "USA"));
        configuration.add("hotel22", new Hotel(300, 4,
                "Mandalay Bay Resort & Casino", "3950 Las Vegas Blvd S",
                "Las Vegas", "NV", "89119", "USA"));
        configuration.add("hotel23", new Hotel(100, 2, "Hotel Cammerpoorte",
                "Nationalestraat 38-40", "Antwerp", null, "2000", "BE"));
    }
}
