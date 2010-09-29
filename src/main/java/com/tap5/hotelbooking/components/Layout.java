package com.tap5.hotelbooking.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Layout component for pages of application tapestry5-hotel-booking.
 */
@Import(stylesheet =
{ "context:/static/style.css" })
public class Layout
{
    /** The page title, for the <title> element and the <h1>element. */
    @SuppressWarnings("unused")
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;

    @Property
    private String pageName;

    @SuppressWarnings("unused")
    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String pageTitle;

    @SuppressWarnings("unused")
    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String sidebarTitle;

    @SuppressWarnings("unused")
    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private Block sidebar;

    @Inject
    private ComponentResources resources;

    public String getClassForPageName()
    {
        return resources.getPageName().equalsIgnoreCase(pageName) ? "current_page_item" : null;
    }

    public String[] getPageNames()
    {
        return new String[]
        { "Index", "About", "Contact" };
    }
}
