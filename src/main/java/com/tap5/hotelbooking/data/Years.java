package com.tap5.hotelbooking.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.util.AbstractSelectModel;

public class Years extends AbstractSelectModel
{

    private List<OptionModel> options = new ArrayList<OptionModel>();

    public Years()
    {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i < 6; i++)
        {
            options.add(new OptionModelImpl(year + i + ""));
        }
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
