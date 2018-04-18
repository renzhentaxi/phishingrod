package com.phishingrod.domain.parameters;

import java.util.List;
import java.util.Map;

public interface ParameterContainer<p extends EntityParameter>
{
    long getId();

    List<p> getParameters();

    Map<String, String> getParameterMap();

    default boolean isNew()
    {
        return getId() == 0;
    }
}
