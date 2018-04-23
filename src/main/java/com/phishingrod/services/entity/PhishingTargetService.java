package com.phishingrod.services.entity;

import com.phishingrod.domain.parameters.ParameterSourceType;
import com.phishingrod.domain.phishingTarget.PhishingTarget;
import com.phishingrod.repositories.PhishingTargetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PhishingTargetService extends EmailKeyedEntityService<PhishingTarget, PhishingTargetRepository>
{
    private ParameterResolverService parameterResolver;

    @Autowired
    public PhishingTargetService(PhishingTargetRepository repository, ParameterResolverService parameterResolver)
    {
        super(repository);
        this.parameterResolver = parameterResolver;
    }

    @Override
    protected PhishingTarget merge(PhishingTarget source, PhishingTarget change)
    {
        String emailAddress = change.getEmailAddress();
        if (emailAddress != null)
            source.setEmailAddress(emailAddress);
        Map<String, String> map = change.getParameterMap();

        if (map != null)
        {
            source.setParameterMap(map);
        }
        return source;
    }

    @Override
    protected PhishingTarget preAdd(PhishingTarget entity)
    {
        entity.initialDate();
        return parameterResolver.toRelational(entity, ParameterSourceType.phishingTarget);
    }

    @Override
    protected PhishingTarget preMod(PhishingTarget entity)
    {
        entity.updateLastModified();
        return parameterResolver.toRelational(entity, ParameterSourceType.phishingTarget);
    }

    @Override
    protected PhishingTarget postGet(PhishingTarget entity)
    {
        return parameterResolver.toDomain(entity);
    }


}
