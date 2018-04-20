package com.phishingrod.domain.base;

import com.fasterxml.jackson.annotation.JsonView;
import com.phishingrod.api.RestView;
import com.phishingrod.util.EntityToStringStyle;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public class PhishingRodEntity
{
    private static EntityToStringStyle style = new EntityToStringStyle();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(RestView.Add.class)
    private long id;

    @JsonView(RestView.Add.class)
    @Column(name = "created_at", nullable = false)
    private java.util.Date createdAt;

    @JsonView(RestView.Modify.class)
    @Column(name = "last_Modified", nullable = false)

    private java.util.Date lastModified;

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, style);
    }

}
