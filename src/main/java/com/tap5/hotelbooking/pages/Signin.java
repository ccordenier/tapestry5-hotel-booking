package com.tap5.hotelbooking.pages;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.tap5.hotelbooking.security.AuthenticationException;
import com.tap5.hotelbooking.services.Authenticator;

/**
 * User can sign up on the
 * 
 * @author karesti
 * @version 1.0
 */
public class Signin
{

    @Property
    @Persist(PersistenceConstants.FLASH)
    private String username;

    @Property
    private String password;

    @Inject
    private Authenticator authenticator;

    @Component
    private Form loginForm;

    @Inject
    private Messages messages;

    @Log
    public Object onSubmitFromLoginForm()
    {
        try
        {
            authenticator.login(username, password);

        }
        catch (AuthenticationException ex)
        {
            loginForm.recordError(messages.get("error.login"));
            return null;
        }

        return Index.class;
    }
}
