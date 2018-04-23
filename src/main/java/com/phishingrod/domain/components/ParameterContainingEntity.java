package com.phishingrod.domain.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.phishingrod.api.RestView;
import com.phishingrod.domain.parameters.EntityParameter;
import com.phishingrod.domain.parameters.ParameterContainer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class ParameterContainingEntity<E extends ParameterContainingEntity<E, P>, P extends EntityParameter<E>> extends DateTrackingEntity implements ParameterContainer<E, P>
{
    @OneToMany(mappedBy = "entity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    protected List<P> parameterList = new ArrayList<>();

    @Transient
    @JsonView(RestView.Get.class)
    @JsonProperty("parameters")
    private Map<String, String> parameterMap;
}
