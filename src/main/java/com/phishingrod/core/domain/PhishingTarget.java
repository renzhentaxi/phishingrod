package com.phishingrod.core.domain;

import com.phishingrod.core.domain.base.IEmailKeyEntity;
import com.phishingrod.core.domain.combinations.ParameterContainerEmailKeyEntity;
import com.phishingrod.core.domain.parameters.ParameterSourceType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PhishingTarget extends ParameterContainerEmailKeyEntity implements IEmailKeyEntity
{
    public PhishingTarget(String emailAddress)
    {
        super(emailAddress);
    }

    public PhishingTarget(String emailAddress, Map<String, String> parameterMap)
    {
        super(emailAddress, parameterMap);
    }

    @Override

    public ParameterSourceType getSourceType()
    {
        return ParameterSourceType.phishingTarget;
    }
}
