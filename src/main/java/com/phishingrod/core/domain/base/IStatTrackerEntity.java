package com.phishingrod.core.domain.base;

import java.util.Date;

public interface IStatTrackerEntity
{
    Date getCreatedOn();

    void setCreatedOn(Date createdOn);

    Date getLastModifiedOn();

    void setLastModifiedOn(Date lastModifiedOn);
    
}
