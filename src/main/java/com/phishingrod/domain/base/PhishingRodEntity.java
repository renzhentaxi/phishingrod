package com.phishingrod.domain.base;

import com.phishingrod.util.EntityToStringStyle;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public class PhishingRodEntity
{
    private static EntityToStringStyle style = new EntityToStringStyle();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Date lastModified;

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, style);
    }

}
