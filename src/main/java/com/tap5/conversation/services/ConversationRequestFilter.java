package com.tap5.conversation.services;

import java.io.IOException;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.ComponentRequestFilter;
import org.apache.tapestry5.services.ComponentRequestHandler;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.apache.tapestry5.services.Response;

import com.tap5.conversation.ConversationConstants;

/**
 * Check conversation or prepare it if needed.
 * 
 * @author ccordenier
 */
public class ConversationRequestFilter implements ComponentRequestFilter
{

    private final Response response;

    private final PageRenderLinkSource linkSource;

    private final ConversationManager conversationManager;

    public ConversationRequestFilter(Response response, PageRenderLinkSource linkSource,
            ConversationManager conversationManager)
    {
        super();
        this.response = response;
        this.linkSource = linkSource;
        this.conversationManager = conversationManager;
    }

    /**
     * Send an HTTP error if the action is executed on a conversation page, and does not have a
     * valid CID.
     */
    public void handleComponentEvent(ComponentEventRequestParameters parameters,
            ComponentRequestHandler handler) throws IOException
    {
        boolean conversation = conversationManager.isConversational(parameters.getActivePageName());

        if (conversation && conversationManager.getActiveConversation() == null)
        {
            response.sendError(400, "Conversation id is missing");
            return;
        }

        handler.handleComponentEvent(parameters);
    }

    /**
     * Check if cid exists, if not obtain one and redirect the user to a valid URL.
     */
    public void handlePageRender(PageRenderRequestParameters parameters,
            ComponentRequestHandler handler) throws IOException
    {

        boolean conversation = conversationManager
                .isConversational(parameters.getLogicalPageName());

        // Check if the request is associated to a conversation
        if (conversation && conversationManager.getActiveConversation() == null)
        {
            Link redirect = linkSource.createPageRenderLinkWithContext(parameters
                    .getLogicalPageName(), parameters.getActivationContext());
            redirect.addParameter(ConversationConstants.CID, conversationManager
                    .createConversation().toString());
            response.sendRedirect(redirect);
            return;
        }

        // Check validity
        if (conversation && !conversationManager.isValid())
        {
            response.sendError(400, "Conversation is not valid anymore");
        }

        handler.handlePageRender(parameters);

    }

}
