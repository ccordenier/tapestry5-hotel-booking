package com.tap5.hotelbooking.data;

import java.util.ArrayList;
import java.util.List;

import com.tap5.hotelbooking.domain.entities.Booking;

/**
 * Use this object to store user's booking
 * 
 * @author ccordenier
 */
public class Workspace
{
    private List<Booking> confirmed;

    public Workspace()
    {
        this.confirmed = new ArrayList<Booking>();
    }

    public List<Booking> getConfirmed()
    {
        return confirmed;
    }

    public void setConfirmed(List<Booking> confirmed)
    {
        this.confirmed = confirmed;
    }

}
