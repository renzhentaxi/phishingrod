package com.phishingrod.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.phishingrod.core.domain.combinations.StatTrackingNameKeyEntity;
import com.phishingrod.core.domain.parameters.Parameter;
import com.phishingrod.core.domain.parameters.ParameterSourceType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class EmailTemplate extends StatTrackingNameKeyEntity
{
    @JsonProperty("sourceHtml")
    @Column(length = 1000000, nullable = false)
    private String html;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Parameter> parameters;

    public void replaceParameters(Set<Parameter> parameters)
    {
        this.parameters.clear();
        this.parameters.addAll(parameters);
    }


    public EmailTemplate(String name, String html)
    {
        this(name, html, new HashSet<>(), new HashSet<>());
    }

    public EmailTemplate(String name, String html, Set<Parameter> parameters, Set<SpoofTarget> spoofTargets)
    {
        super(name);
        this.html = html;
        this.parameters = parameters;
    }

    public boolean addParameter(Parameter parameter)
    {
        return parameters.add(parameter);
    }

    public void addParameter(ParameterSourceType sourceType, String name)
    {
        parameters.add(new Parameter(sourceType, name));
    }

    public void deleteParameter(ParameterSourceType sourceType, String name)
    {
        parameters.remove(new Parameter(sourceType, name));
    }

    public boolean hasParameter(ParameterSourceType sourceType, String name)
    {
        return parameters.contains(new Parameter(sourceType, name));
    }

}
