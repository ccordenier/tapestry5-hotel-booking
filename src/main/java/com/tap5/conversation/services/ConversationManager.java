package com.tap5.conversation.services;

import java.util.List;

import org.apache.tapestry5.Link;

import com.tap5.conversation.ConversationalObject;

/**
 * This service is in charge of handling conversational state for the current user session.
 * 
 * @author ccordenier
 */
public interface ConversationManager
{
    /**
     * Return the name of the conversation used by the page.
     * 
     * @param pageName
     * @return
     */
    String getConversationName(String pageName);

    /**
     * Returns true if the page has been declared as conversational.
     * 
     * @param pageName
     * @return
     */
    boolean isConversational(String pageName);

    /**
     * Create a link on the page, and create a new conversation if it does not already exist.
     * 
     * @param pageName
     * @param context
     * @return
     */
    Link createLink(String pageName, Object... context);

    /**
     * Returs false if the conversation does not exist or has ended.
     * 
     * @return
     */
    boolean isValid();

    /**
     * Terminate the conversation
     * 
     * @param converationName
     *            TODO
     * @param cid
     */
    void end(String converationName, Long cid);

    /**
     * Get a conversation identifier
     * 
     * @return
     */
    Long createConversation();

    /**
     * Get the current active conversation if, returns null if the current request is associated to
     * any conversation.
     * 
     * @return
     */
    Long getActiveConversation();

    /**
     * List object of type T for a given conversation.
     * 
     * @param <T>
     * @param type
     * @return
     */
    <T> List<ConversationalObject<T>> list(Class<T> type, String name);
}
