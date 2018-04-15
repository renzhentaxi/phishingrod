package com.phishingrod.domain;


import com.phishingrod.domain.base.NamedEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class EmailTemplate extends NamedEntity
{
    @Column(nullable = false, length = 1000000)
    private String sourceHtml;

    @Column(nullable = false, length = 1000000)
    private String originalHtml;


    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "template_id"),
            inverseJoinColumns = @JoinColumn(name = "spoof_id")
    )
    private List<SpoofTarget> spoofTargets = new ArrayList<>();

    public EmailTemplate(String name, String sourceHtml, String originalHtml, Date timeAdded)
    {
        super(timeAdded, name);
        this.sourceHtml = sourceHtml;
        this.originalHtml = originalHtml;
    }

    public void addSpoofTarget(SpoofTarget target)
    {
        if (spoofTargets.contains(target))
        {
            throw new RuntimeException("target is already included");
        }
        spoofTargets.add(target);
        target.getTemplates().add(this);
    }

    public void removeSpoofTarget(SpoofTarget target)
    {
        if (!spoofTargets.contains(target))
        {
            throw new RuntimeException("target is not included");
        }

        spoofTargets.remove(target);
        target.getTemplates().remove(this);
    }

}
