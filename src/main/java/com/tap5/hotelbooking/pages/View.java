package com.tap5.hotelbooking.pages;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.tap5.conversation.services.ConversationManager;
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

    @Inject
    private ConversationManager manager; 
    
    /**
     * Start booking process.
     *
     * @param hotel
     * @return
     */
    @OnEvent(value = EventConstants.SUCCESS, component="startBookingForm")
    Object startBooking(Hotel hotel)
    {
        return manager.createLink("book", hotel);
    }

}
