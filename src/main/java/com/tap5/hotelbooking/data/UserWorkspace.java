package com.tap5.hotelbooking.data;

import java.util.ArrayList;
import java.util.List;

import com.tap5.hotelbooking.entities.Booking;
import com.tap5.hotelbooking.entities.Hotel;
import com.tap5.hotelbooking.entities.User;

/**
 * Use this object to store user's booking. It also provides all the method to handle booking in
 * progress for the current user. This object is stored in session when the user starts to book an
 * hotel.
 * 
 * @author karesti
 */
public class UserWorkspace
{
    private Booking current;

    private List<Booking> notConfirmed = new ArrayList<Booking>();

    public List<Booking> getNotConfirmed()
    {
        return notConfirmed;
    }

    public Booking getCurrent()
    {
        return current;
    }

    public void startBooking(Hotel hotel, User user)
    {
        Booking booking = new Booking(hotel, user, 1, 1);
        this.current = booking;
        notConfirmed.add(booking);
    }

    private void removeCurrentBooking(Booking booking)
    {
        notConfirmed.remove(booking);
        this.current = null;
    }

    public Booking restoreBooking(Long bookId)
    {
        Booking restoredBooking = null;

        for (Booking booking : notConfirmed)
        {
            if (bookId.equals(booking.getHotel().getId()))
            {
                restoredBooking = booking;
                break;
            }
        }

        this.current = restoredBooking;

        return restoredBooking;
    }

    public void cancelCurrentBooking(Booking booking)
    {
        removeCurrentBooking(booking);
    }

    public void confirmCurrentBooking(Booking booking)
    {
        removeCurrentBooking(booking);
    }

}
