package com.tap5.hotelbooking.components;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.tynamo.security.services.SecurityService;

import com.tap5.hotelbooking.pages.Index;

/**
 * Layout component for pages of application tapestry5-hotel-booking.
 */
@Import(stylesheet =
{ "context:/static/style.css" }, library =
{ "context:/static/hotel-booking.js" })
public class Layout
{
    /** The page title, for the <title> element and the <h1>element. */
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;

    @Property
    private String pageName;

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String pageTitle;

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String sidebarTitle;

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private Block sidebar;

    @Inject
    private ComponentResources resources;

    @Inject
    private SecurityService securityService;

    public String getClassForPageName()
    {
        return resources.getPageName().equalsIgnoreCase(pageName) ? "current_page_item" : null;
    }

    public Object getUsername()
    {
        Subject subject = securityService.getSubject();
        return subject.getPrincipal();
    }

    @Log
    public Object onActionFromLogout()
    {
        Subject currentUser = securityService.getSubject();

        if (currentUser == null) { throw new IllegalStateException("Subject can`t be null"); }

        currentUser.logout();

        return Index.class;
    }
}
