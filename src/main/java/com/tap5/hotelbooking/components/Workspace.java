package com.tap5.hotelbooking.components;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import com.tap5.conversation.ConversationConstants;
import com.tap5.conversation.ConversationalObject;
import com.tap5.conversation.services.ConversationManager;
import com.tap5.hotelbooking.domain.entities.Booking;

/**
 * Display the list of current booking that has not been confirmed yet. You can click on displayed
 * link to continue
 * 
 * @author ccordenier
 */
public class Workspace
{
    @Inject
    private ConversationManager manager;

    @Inject
    private PageRenderLinkSource linkSource;

    @Property
    private List<ConversationalObject<Booking>> bookings;

    @Property
    private ConversationalObject<Booking> current;

    @Property
    private DateFormat format = new SimpleDateFormat("MM/dd/yyyy");

    @SetupRender
    boolean listBookings()
    {
        bookings = new ArrayList<ConversationalObject<Booking>>(manager.list(Booking.class, "book"));
        return bookings.size() > 0 ? true : false;
    }

    public Booking getCurrentBooking()
    {
        return current.getObject();
    }

    public Link getBookLink()
    {
        Link result = linkSource.createPageRenderLinkWithContext("book", current.getObject()
                .getHotel());
        result.addParameter(ConversationConstants.CID, current.getCid().toString());
        return result;
    }

    public boolean getIsCurrent()
    {
        return current.getCid().equals(manager.getActiveConversation());
    }
}
