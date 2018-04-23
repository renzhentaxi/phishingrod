package com.phishingrod.domain.spoofTarget;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.phishingrod.api.RestView;
import com.phishingrod.domain.EmailTemplate;
import com.phishingrod.domain.components.DateTrackingComponent;
import com.phishingrod.domain.components.EmailKeyedEntity;
import com.phishingrod.domain.components.PhishingRodEntity;
import com.phishingrod.domain.components.params.ParameterContainer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class SpoofTarget extends PhishingRodEntity implements EmailKeyedEntity, DateTrackingComponent, ParameterContainer<SpoofTarget, SpoofTargetParameter>
{
    @Column(unique = true, nullable = false)
    @JsonView(RestView.Get.class)
    private String emailAddress;

    //****************************Date Tracking component****************************
    @Column(nullable = false)
    @JsonView(RestView.Add.class)
    private Date createdAt;

    @Column(nullable = false)
    @JsonView(RestView.Modify.class)
    private Date lastModified;


    //****************************Parameters****************************
    @OneToMany(mappedBy = "entity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<SpoofTargetParameter> parameterList = new ArrayList<>();

    @Transient
    @JsonView(RestView.Get.class)
    @JsonProperty("parameters")
    private Map<String, String> parameterMap;

    //****************************other****************************
    @ManyToMany(mappedBy = "spoofTargets")
    private List<EmailTemplate> templates = new ArrayList<>();

    //****************************Constructor****************************
    public SpoofTarget(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

}
