package com.tap5.conversation.services;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Autobuild;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.ComponentRequestFilter;
import org.apache.tapestry5.services.LinkCreationHub;
import org.apache.tapestry5.services.PersistentFieldStrategy;
import org.apache.tapestry5.services.Request;

import com.tap5.conversation.ConversationConstants;

public class ConversationModule
{

    public static void bind(ServiceBinder binder)
    {
        binder.bind(ConversationManager.class, ConversationManagerImpl.class);
    }

    public PersistentFieldStrategy buildConversationPersistentFieldStrategy(
            LinkCreationHub linkCreationHub, @Autobuild ConversationPersistentFieldStrategy strategy)
    {
        linkCreationHub.addListener(strategy);
        return strategy;
    }

    /**
     * Add our conversation stratefy to the existing list.
     * 
     * @param configuration
     * @param request
     * @param conversationStrategy
     */
    public void contributePersistentFieldManager(
            MappedConfiguration<String, PersistentFieldStrategy> configuration,
            Request request,
            @InjectService("ConversationPersistentFieldStrategy") PersistentFieldStrategy conversationStrategy)
    {
        configuration.add(ConversationConstants.CONVERSATION, conversationStrategy);
    }

    /**
     * Add a filter that check conversational state
     * 
     * @param configuration
     */
    public void contributeComponentRequestHandler(
            OrderedConfiguration<ComponentRequestFilter> configuration)
    {
        configuration.addInstance(
                "ConversationRequestFilter",
                ConversationRequestFilter.class,
                "after:InitializeActivePageName");
    }

    /**
     * Add conversation related workers.
     * 
     * @param configuration
     */
    public static void contributeComponentClassTransformWorker(
            OrderedConfiguration<ComponentClassTransformWorker> configuration)
    {
        configuration.addInstance("conversation", ConversationWorker.class);
    }

}
