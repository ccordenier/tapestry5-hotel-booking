package com.tap5.hotelbooking.pages;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Persist;
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
    @Inject
    private Session session;

    @InjectComponent
    private Zone result;

    @PageActivationContext
    @Property
    private String query;

    @Property
    private int rowsPerPage = 10;
    
    @Property
    @Persist
    private GridDataSource source;

    /**
     * Move this into a hotel query service
     */
    @OnEvent(value = EventConstants.SUCCESS)
    Object searchHotels()
    {
        source = new HibernateGridDataSource(session, Hotel.class)
        {
            @Override
            protected void applyAdditionalConstraints(Criteria crit)
            {
                crit.add(Restrictions.like("name", query));
            }
        };

        return result;
    }

}
