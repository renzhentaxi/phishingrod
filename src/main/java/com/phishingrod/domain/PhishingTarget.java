package com.phishingrod.domain;


import com.phishingrod.domain.base.EmailedEntity;
import com.phishingrod.domain.parameters.Parameter;
import com.phishingrod.domain.parameters.PhishingTargetParameter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PhishingTarget extends EmailedEntity
{
    @OneToMany(mappedBy = "phishingTarget", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<PhishingTargetParameter> parameters = new ArrayList<>();

    public PhishingTarget(String emailAddress)
    {
        super(emailAddress);
    }

    public void addParameter(Parameter parameter, String value)
    {
        PhishingTargetParameter p = new PhishingTargetParameter(this, parameter, value);
        parameters.add(p);
    }

    public void removeParameter(String userName)
    {
    }
}
