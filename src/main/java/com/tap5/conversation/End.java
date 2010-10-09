package com.tap5.conversation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Use this annotation to annotate methods that will trigger the end of the current conversation.
 */
@Target(ElementType.METHOD)
@Documented
@Retention(RUNTIME)
public @interface End
{

}
