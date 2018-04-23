package com.phishingrod.domain.components;

import com.fasterxml.jackson.annotation.JsonView;
import com.phishingrod.api.RestView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public class DateTrackingEntity extends PhishingRodEntity implements DateTrackingComponent
{
    @Column(nullable = false)
    @JsonView(RestView.Add.class)
    private Date createdAt;

    @Column(nullable = false)
    @JsonView(RestView.Modify.class)
    private Date lastModified;
}
