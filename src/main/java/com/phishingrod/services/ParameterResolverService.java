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

    public <p extends EntityParameter, E extends ParameterContainer<p>> E toRelational(E entity, ParameterSourceType sourceType)
    {
        List<p> parameters = entity.getParameterList();
        parameters.clear();

        for (Map.Entry<String, String> entry : entity.getParameterMap().entrySet())
        {
            String paramName = entry.getKey();
            String paramValue = entry.getValue();
            p parameter = resolveEntityParameter(entity, sourceType, paramName);
            parameter.setValue(paramValue);
            parameters.add(parameter);
        }
        return entity;
    }

    public <P extends EntityParameter, E extends ParameterContainer<P>> E toDomain(E entity)
    {
        Map<String, String> parameterMap = entity.getParameterMap();
        parameterMap.clear();
        for (P parameter : entity.getParameterList())
        {
            parameterMap.put(parameter.getParameter().getName(), parameter.getValue());
        }
        return entity;

    }

    private Parameter resolveParameter(ParameterSourceType type, String name)
    {
        return parameterRepository.findDistinctBySourceTypeAndName(type, name).orElse(parameterRepository.save(new Parameter(type, name)));
    }

    @SuppressWarnings("unchecked")
    private <P extends EntityParameter, E extends ParameterContainer<P>> P resolveEntityParameter(E entity, ParameterSourceType sourceType, String parameterName)
    {
        switch (sourceType)
        {
            case phishingTarget:
            {
                PhishingTarget target = (PhishingTarget) entity;
                PhishingTargetParameter parameter = resolvePhishingTargetParameter(target, parameterName);
                return (P) parameter;
            }
            default:
                throw new RuntimeException("Cant not find parameter of type!!");
        }
    }

    private PhishingTargetParameter resolvePhishingTargetParameter(PhishingTarget target, String parameterName)
    {
        Parameter parameter = resolveParameter(ParameterSourceType.phishingTarget, parameterName);

        if (target.isNew())//if the target is transient/ not in database yet
        {
            return new PhishingTargetParameter(target, parameter);
        } else
        {
            return phishingTargetParameterRepository.findDistinctByPhishingTargetAndParameter(target, parameter).orElse(new PhishingTargetParameter(target, parameter));
        }
    }
}
