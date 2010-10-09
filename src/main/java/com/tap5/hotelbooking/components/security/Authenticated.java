package com.tap5.hotelbooking.components.security;

import org.apache.tapestry5.corelib.base.AbstractConditional;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.tap5.hotelbooking.services.Authenticator;

/**
 * @author karesti
 */
public class Authenticated extends AbstractConditional
{

    @Inject
    private Authenticator authenticator;

    @Override
    protected boolean test()
    {
        return authenticator.isLoggedIn();
    }

}
