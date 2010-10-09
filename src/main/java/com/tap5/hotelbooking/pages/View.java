package com.tap5.hotelbooking.pages;

import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Property;

import com.tap5.hotelbooking.domain.entities.Hotel;

/**
 * This page displays hotel details, and provide access to booking.
 * 
 * @author ccordenier
 */
public class View
{
    @Property
    @PageActivationContext
    private Hotel hotel;

}
