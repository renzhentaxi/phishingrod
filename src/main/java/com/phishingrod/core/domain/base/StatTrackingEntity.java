package com.phishingrod.core.domain.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public abstract class StatTrackingEntity extends BasicEntity implements IStatTrackerEntity
{
    @Column(nullable = false)
    private Date createdOn;
    @Column(nullable = false)
    private Date lastModifiedOn;
}
