package com.tap5.hotelbooking.pages;

import java.io.IOException;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.services.Response;
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
    private String username;

    @Property
    private String password;

    @Property
    private boolean rememberMe;

    @Inject
    private Response response;

    @Inject
    private RequestGlobals requestGlobals;

    @Inject
    private SecurityService securityService;

    @Inject
    private PageService pageService;

    @Component
    private Form loginForm;

    @Log
    public Object onActionFromLogin()
    {

        Subject currentUser = securityService.getSubject();

        if (currentUser == null) { throw new IllegalStateException("Subject can`t be null"); }

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(rememberMe);

        try
        {
            currentUser.login(token);
        }
        catch (AuthenticationException e)
        {
            loginForm.recordError("Authentication Error");
            return null;
        }

        SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(requestGlobals
                .getHTTPServletRequest());

        if (savedRequest != null && savedRequest.getMethod().equalsIgnoreCase("GET"))
        {
            try
            {
                response.sendRedirect(savedRequest.getRequestUrl());
                return null;
            }
            catch (IOException e)
            {
                return pageService.getSuccessPage();
            }
        }
        else
        {
            return pageService.getSuccessPage();
        }

    }
}
