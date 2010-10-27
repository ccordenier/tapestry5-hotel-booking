package com.tap5.hotelbooking.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.tap5.hotelbooking.entities.User;
import com.tap5.hotelbooking.pages.Index;
import com.tap5.hotelbooking.services.Authenticator;

/**
 * Layout component for pages of application tapestry5-hotel-booking.
 */
@Import(stylesheet =
{ "context:/static/style.css" }, library =
{ "context:/static/hotel-booking.js" })
public class Layout
{
    @Property
    private String pageName;

    @SuppressWarnings("unused")
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String pageTitle;

    @SuppressWarnings("unused")
    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private Block sidebar;

    @Inject
    private ComponentResources resources;

    @Inject
    private Authenticator authenticator;

    public String getClassForPageName()
    {
        return resources.getPageName().equalsIgnoreCase(pageName) ? "current_page_item" : null;
    }

    public User getUser()
    {
        return authenticator.isLoggedIn() ? authenticator.getLoggedUser() : null;
    }

    @Log
    public Object onActionFromLogout()
    {
        authenticator.logout();
        return Index.class;
    }
}
