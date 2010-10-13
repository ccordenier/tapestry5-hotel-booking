package com.tap5.hotelbooking.components;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.base.AbstractConditional;

/**
 * Display a flash message once.
 * 
 * @author ccordenier
 */
public class IfNotNull extends AbstractConditional
{

    @Parameter(required = true)
    private Object object;

    @Override
    protected boolean test()
    {
        return object != null;
    }
}
