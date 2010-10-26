package com.tap5.hotelbooking.components;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import com.tap5.hotelbooking.data.UserWorkspace;
import com.tap5.hotelbooking.domain.entities.Booking;

/**
 * Display the list of current booking that has not been confirmed yet. You can click on displayed
 * link to continue
 * 
 * @author ccordenier
 * @author karesti
 */
public class Workspace
{

    @SessionState
    @Property
    private UserWorkspace userWorkspace;

    @Inject
    private PageRenderLinkSource linkSource;

    @SuppressWarnings("unused")
    @Property
    private DateFormat format = new SimpleDateFormat("MM/dd/yyyy");

    @Property
    private Booking current;

    public List<Booking> getBookings()
    {
        return userWorkspace.getNotConfirmed();
    }

    public Link getBookLink()
    {
        Link result = linkSource.createPageRenderLinkWithContext(
                "book",
                current.getHotel(),
                "restore");
        return result;
    }

    public boolean getIsCurrent()
    {

        return userWorkspace.getCurrent() != null && userWorkspace.getCurrent().equals(current);
    }
}
