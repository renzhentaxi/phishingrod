package com.phishingrod.core.domain.parameters;

import com.phishingrod.core.domain.base.BasicEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"parameter_id", "container_id"})
)
@NoArgsConstructor
public class ParameterInstance extends BasicEntity
{
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Parameter parameter;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private ParameterContainerEntity container;

    @Column(nullable = false)
    private String value;

    @Override
    public int hashCode()
    {
        if (parameter == null)
        {
            return Objects.hash(getId());
        }
        return Objects.hash(parameter.hashCode(), container.hashCode());
    }

    @Override
    public boolean equals(Object obj)
    {
        return super.equals(obj);
    }

    public ParameterInstance(Parameter parameter, ParameterContainerEntity container)
    {
        this.parameter = parameter;
        this.container = container;
    }

    public ParameterInstance(Parameter parameter, ParameterContainerEntity container, String value)
    {
        this.parameter = parameter;
        this.container = container;
        this.value = value;
    }

}
