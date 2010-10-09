package com.tap5.hotelbooking.pages;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.tynamo.security.services.SecurityService;

import com.tap5.conversation.Conversation;
import com.tap5.conversation.End;
import com.tap5.hotelbooking.domain.CrudServiceDAO;
import com.tap5.hotelbooking.domain.entities.Booking;
import com.tap5.hotelbooking.domain.entities.Hotel;
import com.tap5.hotelbooking.domain.entities.User;

/**
 * This page implements booking process for a give hotel.
 * 
 * @author ccordenier
 */
@Conversation("book")
public class Book
{
    @Property
    @PageActivationContext
    private Hotel hotel;

    @Inject
    private Block bookBlock;

    @Inject
    private Block confirmBlock;

    @Inject
    private Messages messages;

    @Inject
    private CrudServiceDAO dao;

    @Inject
    private SecurityService securityService;

    @InjectComponent
    private BeanEditForm bookingForm;

    @Property
    @Persist
    private Booking booking;

    @Persist
    private boolean confirm;

    /**
     * Get the current step
     * 
     * @return
     */
    public Block getStep()
    {
        return confirm ? confirmBlock : bookBlock;
    }

    @OnEvent(value = EventConstants.ACTIVATE)
    public void setupBooking()
    {
        if (booking == null)
        {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("username", securityService.getSubject().getPrincipal());
            params.put("email", null);
            User user = (User) dao.findUniqueWithNamedQuery(User.BY_USERNAME_OR_EMAIL, params);
            booking = new Booking(hotel, user, 1, 1);
        }

    }

    @OnEvent(value = EventConstants.VALIDATE, component = "bookingForm")
    public void validateBooking()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        if (booking.getCheckinDate().before(calendar.getTime()))
        {
            bookingForm.recordError(messages.get("booking_checkInNotFutureDate"));
            return;
        }
        else if (!booking.getCheckinDate().before(booking.getCheckoutDate()))
        {
            bookingForm.recordError(messages.get("booking_checkOutBeforeCheckIn"));
            return;
        }

        confirm = true;
    }

    @OnEvent(value = "confirm")
    @End
    public Object confirm()
    {
        // Create
        dao.create(booking);

        // Clean
        confirm = false;
        booking = null;

        // Return to search
        return Search.class;
    }

    @OnEvent(value = "cancel")
    public void cancel()
    {
        confirm = false;
    }

}
