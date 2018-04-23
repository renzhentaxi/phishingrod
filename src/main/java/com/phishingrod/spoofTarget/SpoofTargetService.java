package com.phishingrod.spoofTarget;

import com.phishingrod.domain.parameters.ParameterSourceType;
import com.phishingrod.services.entity.EmailKeyedEntityService;
import com.phishingrod.services.entity.ParameterResolverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SpoofTargetService extends EmailKeyedEntityService<SpoofTarget, SpoofTargetRepository>
{
    private ParameterResolverService parameterResolver;

    @Autowired
    public SpoofTargetService(SpoofTargetRepository repository, ParameterResolverService parameterResolver)
    {
        super(repository);
        this.parameterResolver = parameterResolver;
    }

    @Override
    protected void merge(SpoofTarget source, SpoofTarget change)
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
    protected void preAdd(SpoofTarget entity)
    {
        entity.initialDate();
        parameterResolver.toRelational(entity, ParameterSourceType.spoofTarget);
    }

    @Override
    protected void preMod(SpoofTarget entity)
    {
        entity.updateLastModified();
        parameterResolver.toRelational(entity, ParameterSourceType.spoofTarget);
    }

    @Override
    protected SpoofTarget postGet(SpoofTarget entity)
    {
        return parameterResolver.toDomain(entity);
    }
}
