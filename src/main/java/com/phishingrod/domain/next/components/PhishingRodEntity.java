package com.phishingrod.domain.next.components;


import com.fasterxml.jackson.annotation.JsonView;
import com.phishingrod.api.RestView;
import com.phishingrod.util.EntityToStringStyle;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public class PhishingRodEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(RestView.Add.class)
    private long id;

    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, EntityToStringStyle.instance);
    }

    public boolean isNew()
    {
        return this.id == 0;
    }
}
