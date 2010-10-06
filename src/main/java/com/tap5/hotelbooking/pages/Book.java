package com.tap5.hotelbooking.pages;

import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Property;

import com.tap5.hotelbooking.domain.entities.Hotel;

/**
 * This page implements booking process for a give hotel.
 * 
 * @author ccordenier
 */
public class Book
{

    @Property
    @PageActivationContext
    private Hotel hotel;
}
