package com.phishingrod.core.domain.parameters;

import com.phishingrod.core.domain.base.BasicEntity;
import com.phishingrod.core.domain.base.INameKeyEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "sourceType"})
)
@NoArgsConstructor
public class Parameter extends BasicEntity implements INameKeyEntity
{
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParameterSourceType sourceType;

    @Column(nullable = false)
    private String name;

    @Override
    public int hashCode()
    {
        return Objects.hash(name, sourceType);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Parameter)
        {
            Parameter other = (Parameter) obj;
            return this.name.equals(other.name) && this.sourceType.equals(other.sourceType);
        }
        return false;
    }

    public Parameter(ParameterSourceType sourceType, String name)
    {
        this.sourceType = sourceType;
        this.name = name;
    }

    public static Parameter spoofTarget(String name)
    {
        return new Parameter(ParameterSourceType.spoofTarget, name);
    }

    public static Parameter phishingTarget(String name)
    {
        return new Parameter(ParameterSourceType.phishingTarget, name);
    }
}
