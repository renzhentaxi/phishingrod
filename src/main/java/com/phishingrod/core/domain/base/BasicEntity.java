package com.phishingrod.core.domain.base;

import com.phishingrod.core.util.EntityToStringStyle;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class BasicEntity implements IIdKeyEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, EntityToStringStyle.instance);
    }
}
