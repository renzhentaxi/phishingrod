package com.phishingrod.domain.parameters;

import java.util.List;
import java.util.Map;

public interface ParameterContainer<p extends EntityParameter>
{
    long getId();

    List<p> getParameterList();

    Map<String, String> getParameterMap();

    default boolean isNew()
    {
        return getId() == 0;
    }

    default void addParameter(String name, String value)
    {
        getParameterMap().put(name, value);
    }

    default void removeParameter(String userName)
    {
        getParameterMap().remove(userName);
    }
}
