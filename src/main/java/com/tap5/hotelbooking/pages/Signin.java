package com.tap5.hotelbooking.pages;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.tynamo.security.services.PageService;
import org.tynamo.security.services.SecurityService;

/**
 * User can sign up on the
 * 
 * @author karesti
 */
public class Signin
{

    @Property
    @Persist(PersistenceConstants.FLASH)
    private String username;

    @Property
    private String password;

    @Property
    private boolean rememberMe;

    @Inject
    private SecurityService securityService;

    @Inject
    private PageService pageService;

    @Component
    private Form loginForm;

    @Inject
    private Messages messages;

    @Log
    public Object onSubmitFromLoginForm()
    {
        try
        {
            Subject currentUser = securityService.getSubject();

            if (currentUser == null) { throw new IllegalStateException("Subject can't be null"); }

            UsernamePasswordToken token = new UsernamePasswordToken(username, password);

            token.setRememberMe(rememberMe);

            currentUser.login(token);

        }
        catch (AuthenticationException ex)
        {
            loginForm.recordError(messages.get("error.login"));
            return null;
        }

        return pageService.getSuccessPage();
    }
}
