package com.tap5.conversation.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.services.MetaDataLocator;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Session;

import com.tap5.conversation.ConversationConstants;

public class ConversationManagerImpl implements ConversationManager
{
    private final Request request;

    private final MetaDataLocator locator;

    private final List<Long> created = new ArrayList<Long>();

    private final List<Long> ended = new ArrayList<Long>();

    public ConversationManagerImpl(Request request, MetaDataLocator locator)
    {
        super();
        this.request = request;
        this.locator = locator;
    }

    public Long createConversation()
    {
        Long cid = getIncrement().getAndIncrement();
        created.add(cid);
        return cid;
    }

    public void end(Long cid)
    {
        String fullPrefix = ConversationConstants.PREFIX + cid + ":";

        Session session = request.getSession(true);

        for (String name : session.getAttributeNames(fullPrefix))
        {
            session.setAttribute(name, null);
        }

        ended.add(cid);
    }

    public boolean isValid(Long cid)
    {
        return created.contains(cid);
    }

    public boolean isConversational(String pageName)
    {
        return ConversationConstants.CONVERSATION.equals(locator.findMeta(
                SymbolConstants.PERSISTENCE_STRATEGY,
                pageName,
                String.class));
    }

    private AtomicLong getIncrement()
    {
        Session session = request.getSession(true);
        AtomicLong increment = (AtomicLong) session.getAttribute("conversation_increment");
        if (increment == null)
        {
            increment = new AtomicLong();
            session.setAttribute("conversation_increment", increment);
        }
        return increment;
    }

}
