package com.tap5.conversation.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.services.MetaDataLocator;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Session;

import com.tap5.conversation.ConversationConstants;
import com.tap5.conversation.ConversationalObject;

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

    public void end(String conversationName, Long cid)
    {
        String fullPrefix = ConversationConstants.PREFIX + conversationName + ":" + cid + ":";

        Session session = request.getSession(true);

        for (String name : session.getAttributeNames(fullPrefix))
        {
            session.setAttribute(name, null);
        }

        created.remove(cid);
        ended.add(cid);
    }

    public boolean isValid()
    {
        return created.contains(getActiveConversation())
                && !ended.contains(getActiveConversation());
    }

    public String getConversationName(String pageName)
    {
        return locator.findMeta(ConversationConstants.CONVERSATION_NAME, pageName, String.class);
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

    public boolean isConversational(String pageName)
    {
        return ConversationConstants.CONVERSATION.equals(locator.findMeta(
                SymbolConstants.PERSISTENCE_STRATEGY,
                pageName,
                String.class));
    }

    public Long getActiveConversation()
    {
        try
        {
            String cid = request.getParameter(ConversationConstants.CID);
            return InternalUtils.isBlank(cid) ? null : new Long(cid);
        }
        catch (NumberFormatException nfEx)
        {
            throw new IllegalArgumentException("Conversation id is corrupted");
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<ConversationalObject<T>> list(Class<T> type, String conversationName)
    {
        String fullPrefix = ConversationConstants.PREFIX + conversationName + ":";

        List<ConversationalObject<T>> result = new ArrayList<ConversationalObject<T>>();

        Session session = request.getSession(true);

        for (String name : session.getAttributeNames(fullPrefix))
        {
            String[] chunks = name.split(":");

            Object value = session.getAttribute(name);
            if (type.isInstance(value))
            {
                result.add(new ConversationalObject<T>((T) value, new Long(chunks[2])));
            }
        }

        return result;
    }
}
