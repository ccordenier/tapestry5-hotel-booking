package com.tap5.hotelbooking.components;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.tap5.conversation.ConversationalObject;
import com.tap5.conversation.services.ConversationManager;
import com.tap5.hotelbooking.domain.entities.Booking;

/**
 * Display the list of current booking that has not been confirmed.
 * 
 * @author ccordenier
 */
public class Workspace
{
    @Inject
    private ConversationManager manager;

    @Property
    private List<ConversationalObject<Booking>> bookings;

    @Property
    private ConversationalObject<Booking> current;

    @SetupRender
    void listBookings()
    {
        bookings = new ArrayList<ConversationalObject<Booking>>(manager.list(Booking.class, "book"));
    }

    public Booking getCurrentBooking()
    {
        return current.getObject();
    }
}
