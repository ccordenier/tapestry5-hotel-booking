package com.tap5.conversation.services;

import java.io.IOException;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.ComponentRequestFilter;
import org.apache.tapestry5.services.ComponentRequestHandler;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;

import com.tap5.conversation.ConversationConstants;

/**
 * Check conversation or prepare it if needed
 * 
 * @author ccordenier
 */
public class ConversationRequestFilter implements ComponentRequestFilter
{

    private final Request request;

    private final Response response;

    private final PageRenderLinkSource linkSource;

    private final ConversationManager conversationManager;

    public ConversationRequestFilter(Request request, Response response,
            PageRenderLinkSource linkSource, ConversationManager conversationManager)
    {
        super();
        this.request = request;
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

        if (conversation && request.getParameter(ConversationConstants.CID) == null)
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

        if (conversation && request.getParameter(ConversationConstants.CID) == null)
        {
            Link redirect = linkSource.createPageRenderLinkWithContext(parameters
                    .getLogicalPageName(), parameters.getActivationContext());
            redirect.addParameter(ConversationConstants.CID, conversationManager
                    .createConversation().toString());
            response.sendRedirect(redirect);
            return;
        }

        handler.handlePageRender(parameters);

    }

}
