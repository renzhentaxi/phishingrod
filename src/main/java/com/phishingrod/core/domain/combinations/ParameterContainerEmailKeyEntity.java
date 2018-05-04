package com.phishingrod.core.domain.combinations;

import com.phishingrod.core.domain.base.IEmailKeyEntity;
import com.phishingrod.core.domain.parameters.ParameterContainerEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class ParameterContainerEmailKeyEntity extends ParameterContainerEntity implements IEmailKeyEntity
{
    @Column(nullable = false, unique = true)
    private String emailAddress;

    public ParameterContainerEmailKeyEntity(String emailAddress)
    {
        super(new HashMap<>());
        this.emailAddress = emailAddress;
    }

    public ParameterContainerEmailKeyEntity(String emailAddress, Map<String, String> parameterMap)
    {
        super(parameterMap);
        this.emailAddress = emailAddress;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParameterContainerEmailKeyEntity that = (ParameterContainerEmailKeyEntity) o;
        return Objects.equals(emailAddress, that.emailAddress);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(emailAddress);
    }
}
