package com.phishingrod.domain;


import com.phishingrod.domain.base.EmailedEntity;
import com.phishingrod.domain.parameters.PhishingTargetParameter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class PhishingTarget extends EmailedEntity
{
    @OneToMany(mappedBy = "phishingTarget", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhishingTargetParameter> parameters = new ArrayList<>();

    public PhishingTarget(String emailAddress)
    {
        super(emailAddress);
    }

}
