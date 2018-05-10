package com.phishingrod.core.domain;

import com.phishingrod.core.domain.base.BasicEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Attempt extends BasicEntity
{
    @ManyToOne
    private EmailTemplate template;

    @ManyToOne
    private PhishingTarget phishingTarget;

    @ManyToOne
    private SpoofTarget spoofTarget;

    @ManyToOne
    private Sender sender;

    private Date openedOn;
    private Date sendOn;
    private Date trickedOn;

    @Enumerated(EnumType.STRING)
    private Stage stage;

    public Attempt(EmailTemplate template, PhishingTarget phishingTarget, SpoofTarget spoofTarget, Sender sender)
    {
        this.template = template;
        this.phishingTarget = phishingTarget;
        this.spoofTarget = spoofTarget;
        this.sender = sender;
    }
}
