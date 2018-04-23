package com.phishingrod.domain.phishingTarget;

import com.phishingrod.domain.components.params.EntityParameter;
import com.phishingrod.domain.components.params.Parameter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"entity", "parameter"})
)
@NoArgsConstructor
public class PhishingTargetParameter extends EntityParameter<PhishingTarget>
{
    public PhishingTargetParameter(PhishingTarget entity, Parameter parameter, String value)
    {
        super(entity, parameter, value);
    }

    public PhishingTargetParameter(PhishingTarget entity, Parameter parameter)
    {
        super(entity, parameter);
    }
}
