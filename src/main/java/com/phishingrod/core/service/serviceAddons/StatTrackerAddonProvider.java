package com.phishingrod.core.service.serviceAddons;

import com.phishingrod.core.domain.base.StatTrackingEntity;

import java.util.Date;

public class StatTrackerAddonProvider
{
    public static <T extends StatTrackingEntity> T onCreate(T entity)
    {
        Date current = new Date();
        entity.setCreatedOn(current);
        entity.setLastModifiedOn(current);
        return entity;
    }

    public static <T extends StatTrackingEntity> T onUpdate(T entity)
    {
        entity.setLastModifiedOn(new Date());
        return entity;
    }
}
