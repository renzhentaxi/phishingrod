package com.phishingrod.domain.parameters;

import com.phishingrod.domain.components.NameKeyedEntity;
import com.phishingrod.domain.components.PhishingRodEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(
        uniqueConstraints = @UniqueConstraint(name = "uk_name_sourceType", columnNames = {"name", "sourceType"})
)
public class Parameter extends PhishingRodEntity implements NameKeyedEntity
{
    private String name;

    @Enumerated(EnumType.STRING)
    private ParameterSourceType sourceType;

    public Parameter(ParameterSourceType type, String name)
    {
        this.sourceType = type;
        this.name = name;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(sourceType, name);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Parameter)
        {
            Parameter other = (Parameter) obj;
            return other.name.equals(this.name) && other.sourceType.equals(this.sourceType);
        }
        return false;
    }
}
