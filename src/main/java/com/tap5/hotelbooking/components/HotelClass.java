package com.tap5.hotelbooking.components;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.AssetSource;

/**
 * Use this component with beandisplay to display an images for hotel's class. It will display the
 * image that corresponds to the number of stars of the hotel.
 * 
 * @author ccordenier
 */
public class HotelClass
{
    @Parameter(required = true)
    @Property
    private long stars;

    @Inject
    private AssetSource assetSource;

    /**
     * Get path to star image in function of the number of stars
     */
    public Asset getHotelClass()
    {
        return assetSource.getContextAsset(String.format("/static/%d-star.gif", stars), null);
    }
}
