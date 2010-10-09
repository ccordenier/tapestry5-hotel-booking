package com.tap5.conversation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.apache.tapestry5.ioc.annotations.AnnotationUseContext.PAGE;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.apache.tapestry5.ioc.annotations.UseWith;

/**
 * Mark your pages with this annotation to turn your page persistence scope into a conversation.
 * 
 * @author ccordenier
 */
@Target(ElementType.TYPE)
@Documented
@Retention(RUNTIME)
@UseWith(
{ PAGE })
public @interface Conversation
{
    String value() default "default_conversation";
}
