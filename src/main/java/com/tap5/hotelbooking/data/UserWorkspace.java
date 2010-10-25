package com.tap5.hotelbooking.data;

import java.util.ArrayList;
import java.util.List;

import com.tap5.hotelbooking.domain.entities.Booking;
import com.tap5.hotelbooking.domain.entities.Hotel;
import com.tap5.hotelbooking.domain.entities.User;

/**
 * Use this object to store user's booking
 * 
 * @author karesti
 */
public class UserWorkspace
{
    private Booking current;

    private List<Booking> notConfirmed;

    public UserWorkspace()
    {
        this.notConfirmed = new ArrayList<Booking>();
    }

    public List<Booking> getNotConfirmed()
    {
        return notConfirmed;
    }

    public void setNotConfirmed(List<Booking> confirmed)
    {
        this.notConfirmed = confirmed;
    }

    public Booking getCurrent()
    {
        return current;
    }

    public void setCurrent(Booking current)
    {
        this.current = current;
    }

    public void startBooking(Hotel hotel, User user)
    {
        Booking booking = new Booking(hotel, user, 1, 1);
        this.setCurrent(booking);
        notConfirmed.add(booking);
    }

    public void confirmCurrentBooking(Booking booking)
    {
        notConfirmed.remove(booking);
        this.current = null;
    }
}
