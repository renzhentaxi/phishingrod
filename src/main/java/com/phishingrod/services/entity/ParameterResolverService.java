package com.phishingrod.services.entity;

import com.phishingrod.domain.components.PhishingRodEntity;
import com.phishingrod.domain.parameters.EntityParameter;
import com.phishingrod.domain.parameters.Parameter;
import com.phishingrod.domain.parameters.ParameterContainer;
import com.phishingrod.domain.parameters.ParameterSourceType;
import com.phishingrod.domain.phishingTarget.PhishingTarget;
import com.phishingrod.domain.phishingTarget.PhishingTargetParameter;
import com.phishingrod.repositories.parameters.ParameterRepository;
import com.phishingrod.repositories.parameters.PhishingTargetParameterRepository;
import com.phishingrod.spoofTarget.SpoofTarget;
import com.phishingrod.spoofTarget.SpoofTargetParameter;
import com.phishingrod.spoofTarget.SpoofTargetParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ParameterResolverService
{
    private ParameterRepository parameterRepository;
    private PhishingTargetParameterRepository phishingTargetParameterRepository;
    private SpoofTargetParameterRepository spoofTargetParameterRepository;

    @Autowired
    public ParameterResolverService(ParameterRepository parameterRepository, PhishingTargetParameterRepository phishingTargetParameterRepository, SpoofTargetParameterRepository spoofTargetParameterRepository)
    {
        this.parameterRepository = parameterRepository;
        this.phishingTargetParameterRepository = phishingTargetParameterRepository;
        this.spoofTargetParameterRepository = spoofTargetParameterRepository;
    }


    public <P extends EntityParameter<E>, E extends PhishingRodEntity & ParameterContainer<E, P>> void toRelational(E entity, ParameterSourceType sourceType)
    {
        List<P> parameters = entity.getParameterList();
        Map<String, String> parameterMap = entity.getParameterMap();
        if (parameterMap != null)
        {
            parameters.clear();
            for (Map.Entry<String, String> entry : parameterMap.entrySet())
            {
                String paramName = entry.getKey();
                String paramValue = entry.getValue();

                P parameter = resolveEntityParameter(entity, sourceType, paramName);
                parameter.setValue(paramValue);

                parameters.add(parameter);
            }
        }
    }

    public <P extends EntityParameter<E>, E extends PhishingRodEntity & ParameterContainer<E, P>> E toDomain(E entity)
    {
        Map<String, String> parameterMap = entity.getParameterMap();
        if (parameterMap == null)
        {
            parameterMap = new HashMap<>();
            entity.setParameterMap(parameterMap);
        } else parameterMap.clear();

        for (P parameter : entity.getParameterList())
        {
            parameterMap.put(parameter.getParameter().getName(), parameter.getValue());
        }
        return entity;

    }

    public Parameter replaceWithActualIfExist(ParameterSourceType type, String name)
    {
        return parameterRepository.findDistinctBySourceTypeAndName(type, name).orElseGet(() -> new Parameter(type, name));
    }

    public Parameter resolveParameter(ParameterSourceType type, String name)
    {
        Optional<Parameter> optionalParameter = parameterRepository.findDistinctBySourceTypeAndName(type, name);
        if (optionalParameter.isPresent())
        {
            return optionalParameter.get();
        } else
        {
            System.out.println(parameterRepository.findAll());
            System.out.println(type + " " + name);
            System.out.println(parameterRepository.findDistinctBySourceTypeAndName(type, name));
            return parameterRepository.save(new Parameter(type, name));
        }
    }

    @SuppressWarnings("unchecked")
    private <P extends EntityParameter<E>, E extends PhishingRodEntity & ParameterContainer<E, P>> P resolveEntityParameter(E entity, ParameterSourceType sourceType, String parameterName)
    {
        switch (sourceType)
        {
            case phishingTarget:
            {
                PhishingTarget target = (PhishingTarget) entity;
                PhishingTargetParameter parameter = resolvePhishingTargetParameter(target, parameterName);
                return (P) parameter;
            }
            case spoofTarget:
            {
                SpoofTarget target = (SpoofTarget) entity;
                SpoofTargetParameter parameter = resolveSpoofTargetParameter(target, parameterName);
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
            return phishingTargetParameterRepository.findDistinctByEntityAndParameter(target, parameter).orElseGet(() -> new PhishingTargetParameter(target, parameter));
        }
    }

    private SpoofTargetParameter resolveSpoofTargetParameter(SpoofTarget target, String parameterName)
    {
        Parameter parameter = resolveParameter(ParameterSourceType.spoofTarget, parameterName);

        if (target.isNew())//if the target is transient/ not in database yet
        {
            return new SpoofTargetParameter(target, parameter);
        } else
        {
            return spoofTargetParameterRepository.findDistinctByEntityAndParameter(target, parameter).orElseGet(() -> new SpoofTargetParameter(target, parameter));
        }
    }
}
