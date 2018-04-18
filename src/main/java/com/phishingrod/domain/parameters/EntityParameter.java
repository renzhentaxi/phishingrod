package com.phishingrod.domain.parameters;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class EntityParameter
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parameter")
    private Parameter parameter;

    @Column(nullable = false)
    private String value;

    public EntityParameter(Parameter parameter)
    {
        this.parameter = parameter;
    }

    public EntityParameter(Parameter parameter, String value)
    {
        this.parameter = parameter;
        this.value = value;
    }
}
