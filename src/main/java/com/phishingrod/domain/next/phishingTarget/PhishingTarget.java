package com.phishingrod.domain.next.phishingTarget;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.phishingrod.api.RestView;
import com.phishingrod.domain.next.components.DateTrackingComponent;
import com.phishingrod.domain.next.components.EmailKeyedEntity;
import com.phishingrod.domain.next.components.PhishingRodEntity;
import com.phishingrod.domain.next.components.params.ParameterContainer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PhishingTarget extends PhishingRodEntity implements EmailKeyedEntity, DateTrackingComponent, ParameterContainer<PhishingTarget, PhishingTargetParameter>
{
    @Column(unique = true, nullable = false)
    @JsonView(RestView.Get.class)
    private String emailAddress;


    //************** Date Tracking Component *****************
    @Column(nullable = false)
    @JsonView(RestView.Add.class)
    private Date createdAt;

    @Column(nullable = false)
    @JsonView(RestView.Modify.class)
    private Date lastModified;


    //************* Parameter Container Component *************

    @OneToMany(mappedBy = "entity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<PhishingTargetParameter> parameterList = new ArrayList<>();

    @Transient
    @JsonView(RestView.Get.class)
    @JsonProperty("parameters")
    private Map<String, String> parameterMap;

    //************* Parameter Container Component *************
    public PhishingTarget(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public PhishingTarget(String emailAddress, Map<String, String> parameterMap)
    {
        this.emailAddress = emailAddress;
        this.parameterMap = parameterMap;
    }
}

