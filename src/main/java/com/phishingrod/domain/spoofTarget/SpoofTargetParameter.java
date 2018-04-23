package com.phishingrod.domain.spoofTarget;

import com.phishingrod.domain.components.params.EntityParameter;
import com.phishingrod.domain.components.params.Parameter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"entity", "parameter"})
)
public class SpoofTargetParameter extends EntityParameter<SpoofTarget>
{
    public SpoofTargetParameter(SpoofTarget entity, Parameter parameter, String value)
    {
        super(entity, parameter, value);
    }

    public SpoofTargetParameter(SpoofTarget entity, Parameter parameter)
    {
        super(entity, parameter);
    }
}
