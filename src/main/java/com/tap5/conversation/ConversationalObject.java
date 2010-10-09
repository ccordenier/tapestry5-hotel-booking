package com.tap5.conversation;

/**
 * Container for
 * 
 * @author ccordenier
 */
public class ConversationalObject<T>
{
    private final T object;

    private final Long cid;

    public ConversationalObject(T object, Long cid)
    {
        this.object = object;
        this.cid = cid;
    }

    public T getObject()
    {
        return object;
    }

    public Long getCid()
    {
        return cid;
    }
}
