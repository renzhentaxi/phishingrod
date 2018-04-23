//package com.phishingrod.test.parameter;
//
//import com.phishingrod.domain.components.PhishingRodEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class ParameterResolver
//{
//    public <E extends PhishingRodEntity & ParameterContainer<E, P>, P extends EntityParameterLink<E>> E syncRelationalRepresentation(E entity)
//    {
//        List<P> parameters = entity.getParameterList();
//        Map<String, String> parameterMap = entity.getParameterMap();
//        if (parameterMap != null)
//        {
//            parameters.clear();
//            parameterMap.forEach((name, value) ->
//            {
//                P parameter = resolveParameter()
//            });
//        }
//    }
//
//
//
//    public <E> E syncDomainRepresentation()
//    {
//
//    }
//
//}
