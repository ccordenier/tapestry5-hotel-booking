package com.tap5.hotelbooking.pages.booking;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.hibernate.HibernateGridDataSource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.tap5.hotelbooking.annotations.RequiresLogin;
import com.tap5.hotelbooking.data.SearchCriteria;
import com.tap5.hotelbooking.domain.entities.Hotel;

/**
 * Lookup for Hotels.
 * 
 * @author ccordenier
 */
@RequiresLogin
public class Search
{

    @Inject
    private Session session;

    @InjectComponent
    private Zone result;

    @SessionState
    @Property
    private SearchCriteria criteria;

    @SuppressWarnings("unused")
    @Property
    private GridDataSource source;

    @SuppressWarnings("unused")
    @Property
    private Hotel currentHotel;

    /**
     * This datasource is used by Tapestry 5 Grid to search and paginate
     * 
     * @author ccordenier
     */
    private final class HotelDataSource extends HibernateGridDataSource
    {
        private HotelDataSource(Session session, @SuppressWarnings("rawtypes") Class entityType)
        {
            super(session, entityType);
        }

        @Override
        protected void applyAdditionalConstraints(Criteria crit)
        {
            crit.add(Restrictions.like("name", criteria.getSearchPattern()));
        }
    }

    @OnEvent(value = EventConstants.ACTIVATE)
    void prepareDataSource()
    {
        if (criteria.getSearchPattern() != null)
        {
            source = new HotelDataSource(session, Hotel.class);
        }
    }

    /**
     * Move this into a hotel query service
     */
    @OnEvent(value = EventConstants.SUCCESS)
    Object searchHotels()
    {
        prepareDataSource();
        return result;
    }

}
