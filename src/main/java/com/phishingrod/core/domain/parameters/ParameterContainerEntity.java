package com.phishingrod.core.domain.parameters;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.phishingrod.core.domain.base.StatTrackingEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Getter
@Setter
public abstract class ParameterContainerEntity extends StatTrackingEntity
{
    @JsonIgnore
    @OneToMany(mappedBy = "container", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ParameterInstance> parameterSet;

    @Transient
    @JsonProperty("parameters")
    private Map<String, String> parameterMap;

    @JsonIgnore
    public abstract ParameterSourceType getSourceType();

    public void setParameter(String name, String value)
    {
        parameterMap.put(name, value);
    }

    public String getParameter(String name)
    {
        return parameterMap.get(name);
    }

    public boolean hasParameter(String name)
    {
        return parameterMap.containsKey(name);
    }

    public void deleteParameter(String name)
    {
        parameterMap.remove(name);
    }

    public ParameterContainerEntity(Map<String, String> parameterMap)
    {
        this.parameterMap = parameterMap;
    }
}
