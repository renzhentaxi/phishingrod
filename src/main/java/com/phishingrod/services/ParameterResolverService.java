package com.phishingrod.services;

import com.phishingrod.domain.PhishingTarget;
import com.phishingrod.domain.parameters.*;
import com.phishingrod.repositories.parameters.ParameterRepository;
import com.phishingrod.repositories.parameters.PhishingTargetParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ParameterResolverService
{
    private ParameterRepository parameterRepository;
    private PhishingTargetParameterRepository phishingTargetParameterRepository;

    @Autowired
    public ParameterResolverService(ParameterRepository parameterRepository, PhishingTargetParameterRepository phishingTargetParameterRepository)
    {
        this.parameterRepository = parameterRepository;
        this.phishingTargetParameterRepository = phishingTargetParameterRepository;
    }

    private Parameter valueOf(ParameterSourceType type, String name)
    {
        return parameterRepository.findDistinctBySourceTypeAndName(type, name).orElse(parameterRepository.save(new Parameter(type, name)));
    }

    PhishingTargetParameter valueOf(PhishingTarget target, String name)
    {
        Parameter parameter = valueOf(ParameterSourceType.phishingTarget, name);
        return phishingTargetParameterRepository.findDistinctByPhishingTargetAndParameter(target, parameter).orElse(new PhishingTargetParameter(target, parameter));
    }

    @SuppressWarnings("unchecked")
    private <P extends EntityParameter, E extends ParameterContainer<P>> P valueOf(E entity, ParameterSourceType sourceType, String parameterName)
    {
        switch (sourceType)
        {
            case phishingTarget:
                return (P) valueOf((PhishingTarget) entity, parameterName);
            default:
                throw new RuntimeException("Cant not find parameter of type!!");
        }
    }

    public <p extends EntityParameter, E extends ParameterContainer<p>> E syncToDatabase(E entity, ParameterSourceType sourceType)
    {
        List<p> parameters = entity.getParameters();
        parameters.clear();

        for (Map.Entry<String, String> entry : entity.getParameterMap().entrySet())
        {
            String paramName = entry.getKey();
            String paramValue = entry.getValue();
            p parameter = valueOf(entity, sourceType, paramName);
            parameter.setValue(paramValue);
            parameters.add(parameter);
        }
        return entity;
    }

    public <P extends EntityParameter, E extends ParameterContainer<P>> E syncFromDatabase(E entity)
    {
        Map<String, String> parameterMap = entity.getParameterMap();
        parameterMap.clear();
        for (P parameter : entity.getParameters())
        {
            parameterMap.put(parameter.getParameter().getName(), parameter.getValue());
        }
        return entity;

    }
}
