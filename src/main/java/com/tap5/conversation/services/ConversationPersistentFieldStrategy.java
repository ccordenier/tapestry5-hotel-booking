package com.tap5.conversation.services;

import static org.apache.tapestry5.ioc.internal.util.CollectionFactory.newList;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.internal.services.PersistentFieldChangeImpl;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.LinkCreationListener2;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.apache.tapestry5.services.PersistentFieldChange;
import org.apache.tapestry5.services.PersistentFieldStrategy;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Session;

import com.tap5.conversation.ConversationConstants;

/**
 * This strategy handles create and add cid parameter on all link generated to the page.
 * 
 * @author ccordenier
 */
public class ConversationPersistentFieldStrategy implements PersistentFieldStrategy,
        LinkCreationListener2
{
    private final Request request;

    private final ConversationManager manager;

    public ConversationPersistentFieldStrategy(Request request, ConversationManager manager)
    {
        this.request = request;
        this.manager = manager;
    }

    public final Collection<PersistentFieldChange> gatherFieldChanges(String pageName)
    {
        Session session = request.getSession(false);

        if (session == null) return Collections.emptyList();

        List<PersistentFieldChange> result = newList();

        String fullPrefix = ConversationConstants.PREFIX;

        boolean forConversation = manager.isConversational(pageName);
        if (forConversation)
        {
            fullPrefix += manager.getConversationName(pageName) + ":"
                    + request.getParameter(ConversationConstants.CID) + ":";
        }
        else
        {
            fullPrefix += pageName + ":";
        }

        for (String name : session.getAttributeNames(fullPrefix))
        {
            Object persistedValue = session.getAttribute(name);

            Object applicationValue = persistedValue == null ? null
                    : convertPersistedToApplicationValue(persistedValue);

            PersistentFieldChange change = buildChange(name, applicationValue, forConversation);

            result.add(change);

            didReadChange(session, name);
        }

        return result;
    }

    public void discardChanges(String pageName)
    {
        Session session = request.getSession(false);

        if (session == null) return;

        String fullPrefix = ConversationConstants.PREFIX;

        if (manager.isConversational(pageName))
        {
            fullPrefix += manager.getConversationName(pageName) + ":"
                    + request.getParameter(ConversationConstants.CID);
        }
        else
        {
            fullPrefix += pageName + ":";
        }

        for (String name : session.getAttributeNames(fullPrefix))
        {
            session.setAttribute(name, null);
        }
    }

    /**
     * Called after each key is read by {@link #gatherFieldChanges(String)}. This implementation
     * does nothing, subclasses may override.
     * 
     * @param session
     *            the session from which a value was just read
     * @param attributeName
     *            the name of the attribute used to read a value
     */
    protected void didReadChange(Session session, String attributeName)
    {
    }

    private PersistentFieldChange buildChange(String name, Object newValue, boolean forConversation)
    {
        String[] chunks = name.split(":");

        // Will be empty string for the root component
        String componentId = chunks[2];
        String fieldName = chunks[3];

        if (forConversation)
        {
            componentId = chunks[3];
            fieldName = chunks[4];
        }

        return new PersistentFieldChangeImpl(componentId, fieldName, newValue);
    }

    public final void postChange(String pageName, String componentId, String fieldName,
            Object newValue)
    {
        assert InternalUtils.isNonBlank(pageName);
        assert InternalUtils.isNonBlank(fieldName);
        Object persistedValue = newValue == null ? null
                : convertApplicationValueToPersisted(newValue);

        StringBuilder builder = new StringBuilder(ConversationConstants.PREFIX);

        if (manager.isConversational(pageName))
        {
            builder.append(manager.getConversationName(pageName));
            builder.append(':');
            builder.append(request.getParameter(ConversationConstants.CID));
            builder.append(':');
        }
        else
        {
            builder.append(pageName);
            builder.append(':');
        }

        if (componentId != null) builder.append(componentId);

        builder.append(':');
        builder.append(fieldName);

        Session session = request.getSession(persistedValue != null);

        // TAPESTRY-2308: The session will be false when newValue is null and the session
        // does not already exist.

        if (session != null)
        {
            session.setAttribute(builder.toString(), persistedValue);
        }
    }

    public void createdComponentEventLink(Link link, ComponentEventRequestParameters parameters)
    {
        // Link should be decorated once the conversation id has been set
        if (manager.isConversational(parameters.getActivePageName())
                && manager.getActiveConversation() != null)
        {
            link.addParameter(ConversationConstants.CID, request
                    .getParameter(ConversationConstants.CID));
        }
    }

    public void createdPageRenderLink(Link link, PageRenderRequestParameters parameters)
    {
        // Link should be decorated once the conversation id has been set
        if (manager.isConversational(parameters.getLogicalPageName())
                && manager.getActiveConversation() != null)
        {
            link.addParameter(ConversationConstants.CID, request
                    .getParameter(ConversationConstants.CID));
        }
    }

    /**
     * Hook that allows a value to be converted as it is written to the session. Passed the new
     * value provided by the application, returns the object to be stored in the session. This
     * implementation simply returns the provided value.
     * 
     * @param newValue
     *            non-null value
     * @return persisted value
     * @see #convertPersistedToApplicationValue(Object)
     */
    protected Object convertApplicationValueToPersisted(Object newValue)
    {
        return newValue;
    }

    /**
     * Converts a persisted value stored in the session back into an application value. This
     * implementation returns the persisted value as is.
     * 
     * @param persistedValue
     *            non-null persisted value
     * @return application value
     * @see #convertPersistedToApplicationValue(Object)
     */
    protected Object convertPersistedToApplicationValue(Object persistedValue)
    {
        return persistedValue;
    }

}
