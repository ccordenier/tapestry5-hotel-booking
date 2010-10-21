package com.tap5.conversation.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.services.MetaDataLocator;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Session;

import com.tap5.conversation.ConversationConstants;
import com.tap5.conversation.ConversationalObject;

public class ConversationManagerImpl implements ConversationManager
{
    private static final String ENDED = "conversation_ended";

    private static final String CREATED = "conversation_created";

    private final Request request;

    private final MetaDataLocator locator;

    private final PageRenderLinkSource linkSource;

    public ConversationManagerImpl(Request request, MetaDataLocator locator,
            PageRenderLinkSource linkSource)
    {
        super();
        this.request = request;
        this.locator = locator;
        this.linkSource = linkSource;
    }

    public Long createConversation()
    {
        Long cid = getIncrement().getAndIncrement();
        getList(CREATED).add(cid);
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

        getList(CREATED).remove(cid);
        getList(ENDED).add(cid);
    }

    public boolean isValid()
    {
        return getList(CREATED).contains(getActiveConversation())
                && !getList(ENDED).contains(getActiveConversation());
    }

    public String getConversationName(String pageName)
    {
        return locator.findMeta(ConversationConstants.CONVERSATION_NAME, pageName, String.class);
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

    public Link createLink(String pageName, Object... context)
    {
        Link result = linkSource.createPageRenderLinkWithContext(pageName, context);

        if (getActiveConversation() != null)
        {
            result.addParameter(ConversationConstants.CID, getActiveConversation().toString());
        }
        else
        {
            result.addParameter(ConversationConstants.CID, createConversation().toString());
        }

        return result;
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

    private List<Long> getList(String list)
    {
        Session session = request.getSession(true);
        List<Long> result = (List<Long>) session.getAttribute(list);
        if (result == null)
        {
            result = new ArrayList<Long>();
            session.setAttribute(list, result);
        }
        return result;
    }
}
