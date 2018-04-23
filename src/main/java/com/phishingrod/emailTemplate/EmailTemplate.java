package com.phishingrod.emailTemplate;

import com.fasterxml.jackson.annotation.JsonView;
import com.phishingrod.api.RestView;
import com.phishingrod.domain.components.DateTrackingEntity;
import com.phishingrod.domain.components.NameKeyedEntity;
import com.phishingrod.domain.parameters.Parameter;
import com.phishingrod.spoofTarget.SpoofTarget;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class EmailTemplate extends DateTrackingEntity implements NameKeyedEntity
{
    @Column(unique = true, nullable = false)
    @JsonView(RestView.Get.class)
    private String name;

    @Column(nullable = false, length = 1000000)
    @JsonView(RestView.Get.class)
    private String sourceHtml;

    @Column(nullable = false, length = 1000000)
    @JsonView(RestView.Get.class)
    private String originalHtml;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "template_id"),
            inverseJoinColumns = @JoinColumn(name = "spoof_id")
    )
    @JsonView(RestView.Get.class)
    private List<SpoofTarget> spoofTargets = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "template_id"),
            inverseJoinColumns = @JoinColumn(name = "parameter_id")
    )
    @JsonView(RestView.Get.class)
    private List<Parameter> parameters = new ArrayList<>();

    public EmailTemplate(String name)
    {
        this.name = name;
    }
}
