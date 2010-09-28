package com.tap5.hotelbooking.pages;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.hibernate.HibernateGridDataSource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.tap5.hotelbooking.domain.entities.Hotel;

/**
 * Lookup for Hotels.
 * 
 * @author ccordenier
 */
public class Search
{
    /**
     * This datasource is used by Tapestry 5 Grid to search and paginate
     * 
     * @author ccordenier
     */
    private final class HotelDataSource extends HibernateGridDataSource
    {
        private HotelDataSource(Session session, Class entityType)
        {
            super(session, entityType);
        }

        @Override
        protected void applyAdditionalConstraints(Criteria crit)
        {
            crit.add(Restrictions.like("name", query));
        }
    }

    @Inject
    private Session session;

    @InjectComponent
    private Zone result;

    @Property
    private String query;

    @Property
    private int rowsPerPage;

    @Property
    private GridDataSource source;

    @OnEvent(value = EventConstants.ACTIVATE)
    void onActivate(String query, int rowsPerPage)
    {
        this.query = query;
        this.rowsPerPage = rowsPerPage;
        this.source = new HotelDataSource(session, Hotel.class);
    }

    /**
     * Move this into a hotel query service
     */
    @OnEvent(value = EventConstants.SUCCESS)
    Object searchHotels()
    {
        source = new HotelDataSource(session, Hotel.class);
        return result;
    }

    @OnEvent(value = EventConstants.PASSIVATE)
    Object[] onPassivate()
    {
        return new Object[]
        { this.query, this.rowsPerPage };
    }

}
