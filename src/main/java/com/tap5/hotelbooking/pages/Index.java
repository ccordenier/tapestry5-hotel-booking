package com.tap5.hotelbooking.pages;

import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.tynamo.security.services.SecurityService;

/**
 * Start page of application tapestry5-hotel-booking.
 */
public class Index
{
    @Inject
    private SecurityService securityService;

    public Object onActivate()
    {

        Subject subject = securityService.getSubject();
        if (subject.isAuthenticated())
        {
            return Search.class;
        }
        else
        {
            return Signin.class;
        }
    }
}
