package com.phishingrod.core.domain;

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
public class SpoofTarget extends ParameterContainerEmailKeyEntity
{
    public SpoofTarget(String emailAddress)
    {
        super(emailAddress);
    }

    public SpoofTarget(String emailAddress, Map<String, String> parameterMap)
    {
        super(emailAddress, parameterMap);
    }

    @Override

    public ParameterSourceType getSourceType()
    {
        return ParameterSourceType.spoofTarget;
    }
}
