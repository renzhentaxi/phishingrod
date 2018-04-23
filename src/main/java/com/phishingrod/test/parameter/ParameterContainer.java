//package com.phishingrod.test.parameter;
//
//import com.phishingrod.domain.components.PhishingRodEntity;
//
//import java.util.List;
//import java.util.Map;
//
//public interface ParameterContainer<E extends PhishingRodEntity, P extends EntityParameterLink<E>>
//{
//    List<P> getParameterList();
//
//    Map<String, String> getParameterMap();
//
//    void setParameterMap(Map<String, String> map);
//
//    default void addParameter(String name, String value)
//    {
//        getParameterMap().put(name, value);
//    }
//
//    default void removeParameter(String userName)
//    {
//        getParameterMap().remove(userName);
//    }
//
//}
