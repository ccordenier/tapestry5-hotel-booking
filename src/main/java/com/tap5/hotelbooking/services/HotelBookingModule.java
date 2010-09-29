package com.tap5.hotelbooking.services;

import java.io.IOException;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.validator.ValidatorMacro;
import org.slf4j.Logger;

import com.tap5.hotelbooking.domain.HibernateModule;
import com.tap5.hotelbooking.security.SecurityModule;

/**
 * This module is automatically included as part of the Tapestry IoC Registry, it's a good place to
 * configure and extend Tapestry, or to place your own service definitions.
 */
@SubModule(
{ HibernateModule.class, SecurityModule.class, DataModule.class })
public class HotelBookingModule
{

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

}
