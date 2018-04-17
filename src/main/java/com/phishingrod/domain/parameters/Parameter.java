package com.phishingrod.domain.parameters;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(
        uniqueConstraints = @UniqueConstraint(name = "uk_name_sourceType", columnNames = {"name", "sourceType"})
)
public class Parameter
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ParameterSourceType sourceType;

    public Parameter(ParameterSourceType type, String name)
    {
        this.sourceType = type;
        this.name = name;
    }
}
