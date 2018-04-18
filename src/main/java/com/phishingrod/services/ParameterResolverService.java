package com.phishingrod.services;

import com.phishingrod.domain.PhishingTarget;
import com.phishingrod.domain.parameters.Parameter;
import com.phishingrod.domain.parameters.ParameterSourceType;
import com.phishingrod.domain.parameters.PhishingTargetParameter;
import com.phishingrod.repositories.parameters.ParameterRepository;
import com.phishingrod.repositories.parameters.PhishingTargetParameterRepository;

public class ParameterResolverService
{
    private ParameterRepository parameterRepository;
    private PhishingTargetParameterRepository phishingTargetParameterRepository;

    public Parameter valueOf(ParameterSourceType type, String name)
    {
        return parameterRepository.findDistinctBySourceTypeAndName(type, name).orElse(new Parameter(type, name));
    }

    public PhishingTargetParameter valueOf(PhishingTarget target, String name)
    {
        Parameter parameter = valueOf(ParameterSourceType.phishingTarget, name);
        return phishingTargetParameterRepository.findDistinctByPhishingTargetAndParameter(target, parameter).orElse(new PhishingTargetParameter(target, parameter));
    }
}
