package com.tap5.hotelbooking.pages;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.tap5.hotelbooking.dal.CrudServiceDAO;
import com.tap5.hotelbooking.data.UserWorkspace;
import com.tap5.hotelbooking.entities.Hotel;
import com.tap5.hotelbooking.entities.User;
import com.tap5.hotelbooking.services.Authenticator;

/**
 * This page displays hotel details, and provide access to booking.
 * 
 * @author ccordenier
 */
public class View
{
    @SessionState
    @Property
    private UserWorkspace userWorkspace;

    @SuppressWarnings("unused")
    @Property
    @PageActivationContext
    private Hotel hotel;

    @Inject
    private Authenticator authenticator;

    @Inject
    private CrudServiceDAO dao;

    /**
     * Start booking process.
     * 
     * @param hotel
     * @return link to the current hotel booking
     */
    @OnEvent(value = EventConstants.SUCCESS, component = "startBookingForm")
    Object startBooking(Hotel hotel)
    {
        User user = (User) dao.find(User.class, authenticator.getLoggedUser().getId());
        userWorkspace.startBooking(hotel, user);
        return Book.class;
    }
}
