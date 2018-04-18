package com.phishingrod.domain;


import com.phishingrod.domain.base.EmailedEntity;
import com.phishingrod.domain.parameters.Parameter;
import com.phishingrod.domain.parameters.PhishingTargetParameter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PhishingTarget extends EmailedEntity
{
    @OneToMany(mappedBy = "phishingTarget", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<PhishingTargetParameter> parameters = new ArrayList<>();

    @Transient
    private Map<String, String> parameterMap = new HashMap<>();

    public PhishingTarget(String emailAddress)
    {
        super(emailAddress);
    }

    public void addParameterOld(Parameter parameter, String value)
    {
        PhishingTargetParameter p = new PhishingTargetParameter(this, parameter, value);
        parameters.add(p);
    }

    public void addParameter(String name, String value)
    {
        System.out.println("added");
        parameterMap.put(name, value);
        System.out.println(name + " " + value);
    }

    public void removeParameter(String userName)
    {
        parameterMap.remove(userName);
    }
}
