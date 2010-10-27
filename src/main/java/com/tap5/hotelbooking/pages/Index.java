package com.tap5.hotelbooking.pages;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.tap5.hotelbooking.annotations.AnonymousAccess;
import com.tap5.hotelbooking.services.Authenticator;

/**
 * Start page of application tapestry5-hotel-booking.
 */
@AnonymousAccess
public class Index
{
    @Inject
    private Authenticator authenticator;

    public Object onActivate()
    {
        return authenticator.isLoggedIn() ? Search.class : Signin.class;
    }
}
