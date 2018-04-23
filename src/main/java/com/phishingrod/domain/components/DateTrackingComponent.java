package com.phishingrod.domain.components;

import java.util.Date;

public interface DateTrackingComponent
{
    Date getCreatedAt();

    void setCreatedAt(Date createdAt);


    Date getLastModified();

    void setLastModified(Date lastModified);


    default void updateLastModified()
    {
        setLastModified(new Date());
    }


    default void initialDate()
    {
        Date current = new Date();
        setLastModified(current);
        setCreatedAt(current);
    }
}
