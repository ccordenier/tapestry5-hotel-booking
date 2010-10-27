package com.tap5.hotelbooking.security;

import java.io.IOException;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.runtime.Component;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.ComponentRequestFilter;
import org.apache.tapestry5.services.ComponentRequestHandler;
import org.apache.tapestry5.services.ComponentSource;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.apache.tapestry5.services.Response;

import com.tap5.hotelbooking.annotations.AnonymousAccess;
import com.tap5.hotelbooking.pages.Search;
import com.tap5.hotelbooking.pages.Signin;
import com.tap5.hotelbooking.pages.Signup;
import com.tap5.hotelbooking.services.Authenticator;

/**
 * Intercepts the current page to redirect through the requested page or to the authentication page
 * if login is required. For more understanding read the following tutorial <a
 * href="http://tapestryjava.blogspot.com/2009/12/securing-tapestry-pages-with.html"> Securing
 * Tapestry Pages with annotations </a>
 * 
 * @author karesti
 * @version 1.0
 */
public class AuthenticationFilter implements ComponentRequestFilter
{

    private final PageRenderLinkSource renderLinkSource;

    private final ComponentSource componentSource;

    private final Response response;

    private final Authenticator authenticator;

    private String defaultPage = Search.class.getSimpleName();

    private String signinPage = Signin.class.getSimpleName();

    private String signupPage = Signup.class.getSimpleName();

    public AuthenticationFilter(PageRenderLinkSource renderLinkSource,
            ComponentSource componentSource, Response response, Authenticator authenticator)
    {
        this.renderLinkSource = renderLinkSource;
        this.componentSource = componentSource;
        this.response = response;
        this.authenticator = authenticator;
    }

    public void handleComponentEvent(ComponentEventRequestParameters parameters,
            ComponentRequestHandler handler) throws IOException
    {

        if (dispatchedToLoginPage(parameters.getActivePageName())) { return; }

        handler.handleComponentEvent(parameters);

    }

    public void handlePageRender(PageRenderRequestParameters parameters,
            ComponentRequestHandler handler) throws IOException
    {

        if (dispatchedToLoginPage(parameters.getLogicalPageName())) { return; }

        handler.handlePageRender(parameters);
    }

    private boolean dispatchedToLoginPage(String pageName) throws IOException
    {

        if (authenticator.isLoggedIn())
        {
            // Logged user should not go back to Signin or Signup
            if (signinPage.equalsIgnoreCase(pageName) || signupPage.equalsIgnoreCase(pageName))
            {
                Link link = renderLinkSource.createPageRenderLink(defaultPage);
                response.sendRedirect(link);
                return true;
            }
            return false;
        }

        Component page = componentSource.getPage(pageName);

        if (page.getClass().isAnnotationPresent(AnonymousAccess.class)) { return false; }

        Link link = renderLinkSource.createPageRenderLink("Signin");

        response.sendRedirect(link);

        return true;
    }
}
