package com.phishingrod.spoofTarget;

import com.fasterxml.jackson.annotation.JsonView;
import com.phishingrod.api.RestView;
import com.phishingrod.domain.components.EmailKeyedEntity;
import com.phishingrod.domain.components.ParameterContainingEntity;
import com.phishingrod.emailTemplate.EmailTemplate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class SpoofTarget extends ParameterContainingEntity<SpoofTarget, SpoofTargetParameter> implements EmailKeyedEntity
{
    @Column(unique = true, nullable = false)
    @JsonView(RestView.Get.class)
    private String emailAddress;

    @ManyToMany(mappedBy = "spoofTargets")
    private List<EmailTemplate> templates = new ArrayList<>();

    //****************************Constructor****************************
    public SpoofTarget(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

}
