package com.phishingrod.domain.next.components;

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


    default void initializeDateTrackingComponent()
    {
        Date current = new Date();
        setLastModified(current);
        setCreatedAt(current);
    }
}
