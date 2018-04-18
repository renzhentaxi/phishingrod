package com.phishingrod.domain.parameters;

import java.util.List;
import java.util.Map;

public interface ParameterContainer<p extends EntityParameter>
{
    List<p> getParameters();
    Map<String,String>  getParameterMap();
}
