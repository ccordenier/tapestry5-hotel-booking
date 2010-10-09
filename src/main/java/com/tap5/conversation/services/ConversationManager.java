package com.tap5.conversation.services;

/**
 * This service is in charge of handling conversational state for the current user session.
 * 
 * @author ccordenier
 */
public interface ConversationManager
{
    boolean isConversational(String pageName);

    boolean isValid(Long cid);

    void end(Long cid);

    Long createConversation();
}
