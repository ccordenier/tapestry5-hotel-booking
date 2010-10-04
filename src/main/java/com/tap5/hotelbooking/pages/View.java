package com.tap5.hotelbooking.pages;

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
    private Hotel hotel;

    Object onActivate(Hotel hotel)
    {
        this.hotel = hotel;
        return true;
    }
    
    Object onActivate()
    {
        return Search.class;
    }

}
