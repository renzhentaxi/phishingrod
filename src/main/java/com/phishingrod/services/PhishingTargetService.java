package com.phishingrod.services;

import com.phishingrod.domain.parameters.ParameterSourceType;
import com.phishingrod.domain.phishingTarget.PhishingTarget;
import com.phishingrod.repositories.PhishingTargetRepository;
import com.phishingrod.services.entity.EmailKeyedEntityService;
import com.phishingrod.services.entity.ParameterResolverService;
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
    protected void merge(PhishingTarget source, PhishingTarget change)
    {
        String emailAddress = change.getEmailAddress();
        if (emailAddress != null)
            source.setEmailAddress(emailAddress);

        Map<String, String> map = change.getParameterMap();
        if (map != null)
        {
            source.setParameterMap(map);
        }
    }

    @Override
    protected void preAdd(PhishingTarget entity)
    {
        entity.initialDate();
        parameterResolver.toRelational(entity, ParameterSourceType.phishingTarget);
    }

    @Override
    protected void preMod(PhishingTarget entity)
    {
        entity.updateLastModified();
        parameterResolver.toRelational(entity, ParameterSourceType.phishingTarget);
    }

    @Override
    protected PhishingTarget postGet(PhishingTarget entity)
    {
        return parameterResolver.toDomain(entity);
    }


}
