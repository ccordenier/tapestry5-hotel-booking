package com.tap5.hotelbooking.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.util.AbstractSelectModel;

/**
 * Used to display a list of months in card expiracy select list.
 *
 * @author ccordenier
 *
 */
public class Months extends AbstractSelectModel
{

    private List<OptionModel> options = new ArrayList<OptionModel>();

    public Months()
    {
        options.add(new OptionModelImpl("January", 1));
        options.add(new OptionModelImpl("February", 2));
        options.add(new OptionModelImpl("March", 3));
        options.add(new OptionModelImpl("April", 4));
        options.add(new OptionModelImpl("Mai", 5));
        options.add(new OptionModelImpl("June", 6));
        options.add(new OptionModelImpl("July", 7));
        options.add(new OptionModelImpl("August", 8));
        options.add(new OptionModelImpl("September", 9));
        options.add(new OptionModelImpl("October", 10));
        options.add(new OptionModelImpl("November", 11));
        options.add(new OptionModelImpl("December", 12));
    }

    public List<OptionGroupModel> getOptionGroups()
    {
        return null;
    }

    public List<OptionModel> getOptions()
    {
        return options;
    }

}