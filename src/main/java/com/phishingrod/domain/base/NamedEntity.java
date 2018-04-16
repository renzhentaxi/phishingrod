package com.phishingrod.domain.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class NamedEntity extends PhishingRodEntity
{
    @Column(unique = true, nullable = false)
    @NaturalId
    private String name;

    public NamedEntity(String name)
    {
        this.name = name;
    }
}
