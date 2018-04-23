package com.phishingrod.emailTemplate;

import com.phishingrod.api.ValidationException;
import com.phishingrod.domain.parameters.Parameter;
import com.phishingrod.domain.parameters.ParameterSourceType;
import com.phishingrod.repositories.parameters.ParameterRepository;
import com.phishingrod.services.entity.NameKeyedEntityService;
import com.phishingrod.services.entity.ParameterResolverService;
import com.phishingrod.spoofTarget.SpoofTarget;
import com.phishingrod.spoofTarget.SpoofTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmailTemplateService extends NameKeyedEntityService<EmailTemplate, EmailTemplateRepository>
{
    private ParameterResolverService parameterResolver;
    private SpoofTargetService spoofTargetService;
    private ParameterRepository parameterRepository;

    @Autowired
    public EmailTemplateService(EmailTemplateRepository repository, ParameterResolverService parameterResolver, SpoofTargetService spoofTargetService)
    {
        super(repository);
        this.parameterResolver = parameterResolver;
        this.spoofTargetService = spoofTargetService;
    }


    @Override
    protected void merge(EmailTemplate source, EmailTemplate change)
    {

    }

    @Override
    protected void preAdd(EmailTemplate entity)
    {
        createSpoofTargetIfDoesNotExist(entity);
        createParametersIfDoesNotExist(entity);
        entity.initialDate();
    }


    @Override
    protected void preMod(EmailTemplate entity)
    {
        createSpoofTargetIfDoesNotExist(entity);
        createParametersIfDoesNotExist(entity);
        entity.updateLastModified();
    }

    @Override
    protected EmailTemplate postGet(EmailTemplate entity)
    {
        return entity;
    }

    private void createSpoofTargetIfDoesNotExist(EmailTemplate entity)
    {
        List<SpoofTarget> actualSpoofTargets = new ArrayList<>();
        for (SpoofTarget target : entity.getSpoofTargets())
        {
            long id = target.getId();
            if (id != 0)
            {
                if (spoofTargetService.exist(id))
                    target = spoofTargetService.get(id);
                else throw new ValidationException("Invalid Fields", "SpoofTarget with id: " + id + " does not exist");
            } else
            {
                target = spoofTargetService.createIfDoesNotExist(target);
            }
            actualSpoofTargets.add(target);
        }
        entity.setSpoofTargets(actualSpoofTargets);
    }

    private void createParametersIfDoesNotExist(EmailTemplate entity)
    {
        List<Parameter> actualParameters = new ArrayList<>();

        for (Parameter parameter : entity.getParameters())
        {
            String name = parameter.getName();
            ParameterSourceType sourceType = parameter.getSourceType();
            switch (sourceType)
            {
                case phishingTarget:
                    actualParameters.add(parameterResolver.resolveParameter(sourceType, name));
                    break;
                case spoofTarget:
                    validateParameterName(entity.getSpoofTargets(), name);
                    break;
            }
        }

        entity.setParameters(actualParameters);
    }

    private void validateParameterName(List<SpoofTarget> spoofTargets, String parameterName)
    {
        Optional<SpoofTarget> firstInvalidParameter = spoofTargets.stream().filter(spoofTarget -> !spoofTarget.hasParameter(parameterName)).findFirst();
        if (firstInvalidParameter.isPresent())
        {
            throw new ValidationException("Invalid Field", "SpoofTarget: " + firstInvalidParameter.get().getEmailAddress() + " does not contain a parameter with name: " + parameterName);
        }
    }
}
