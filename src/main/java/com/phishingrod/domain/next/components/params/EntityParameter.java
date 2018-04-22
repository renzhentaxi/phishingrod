package com.phishingrod.domain.next.components.params;

import com.phishingrod.domain.next.components.PhishingRodEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class EntityParameter<E extends PhishingRodEntity> extends PhishingRodEntity
{
    @ManyToOne(cascade = CascadeType.ALL) //todo: do i need this?
    @JoinColumn(name = "parameter")
    private Parameter parameter;

    @ManyToOne
    @JoinColumn(name = "entity")
    private E entity;

    @Column(nullable = false)
    private String value;

    public EntityParameter(E entity, Parameter parameter, String value)
    {
        this.parameter = parameter;
        this.entity = entity;
        this.value = value;
    }

    public EntityParameter(E entity, Parameter parameter)
    {
        this.parameter = parameter;
        this.entity = entity;
    }
}
