package com.tap5.conversation.services;

import java.util.List;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.func.Predicate;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.services.ClassTransformation;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.ComponentMethodAdvice;
import org.apache.tapestry5.services.ComponentMethodInvocation;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.TransformMethod;

import com.tap5.conversation.Conversation;
import com.tap5.conversation.ConversationConstants;
import com.tap5.conversation.End;

/**
 * Apply conversation logic to page annotated with {@link Conversation} This worker will set page
 * meta for conversation name and identify end method to clean conversation context after method
 * execution
 * 
 * @author ccordenier
 */
public class ConversationWorker implements ComponentClassTransformWorker
{
    private final ConversationManager manager;

    private final Request request;

    public ConversationWorker(ConversationManager manager, Request request)
    {
        super();
        this.manager = manager;
        this.request = request;
    }

    public void transform(ClassTransformation transformation, MutableComponentModel model)
    {
        final Conversation conversationAnnotation = transformation
                .getAnnotation(Conversation.class);

        if (conversationAnnotation != null)
        {
            if (InternalUtils.isBlank(conversationAnnotation.value())) { throw new IllegalArgumentException(
                    "Converation cannot be blank"); }

            model.setMeta(SymbolConstants.PERSISTENCE_STRATEGY, ConversationConstants.CONVERSATION);
            model.setMeta(ConversationConstants.CONVERSATION_NAME, conversationAnnotation.value());

            // Detect end methods
            List<TransformMethod> methods = transformation
                    .matchMethods(new Predicate<TransformMethod>()
                    {
                        @Override
                        public boolean accept(TransformMethod object)
                        {
                            return object.getAnnotation(End.class) != null;
                        }
                    });

            for (TransformMethod method : methods)
            {
                method.addAdvice(new ComponentMethodAdvice()
                {
                    public void advise(ComponentMethodInvocation invocation)
                    {
                        try
                        {
                            invocation.proceed();
                        }
                        finally
                        {
                            manager.end(conversationAnnotation.value(), new Long(request
                                    .getParameter(ConversationConstants.CID)));
                        }

                    }
                });
            }

        }

    }

}
