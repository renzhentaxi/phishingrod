package com.phishingrod.emailTemplate;

import com.phishingrod.api.ValidationException;
import com.phishingrod.domain.parameters.Parameter;
import com.phishingrod.domain.parameters.ParameterSourceType;
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
        String changedName = change.getName();
        if (changedName != null)
        {
            //todo:check for duplicate
            source.setName(changedName);
        }

        String changedOriginalHtml = change.getOriginalHtml();
        if (changedOriginalHtml != null)
        {
            source.setOriginalHtml(changedOriginalHtml);
        }

        String changedSourceHtml = change.getSourceHtml();
        if (changedSourceHtml != null)
        {
            source.setSourceHtml(changedSourceHtml);
        }

        List<Parameter> changedParameters = change.getParameters();
        if (changedParameters != null)
        {
            source.setParameters(changedParameters);
        }

        List<SpoofTarget> changedSpoofTargets = change.getSpoofTargets();
        if (changedSpoofTargets != null)
        {
            source.setSpoofTargets(changedSpoofTargets);
        }
    }

    @Override
    protected void preAdd(EmailTemplate entity)
    {
        checkAllSpoofTargetsExist(entity);
        createParametersIfDoesNotExist(entity);
        if (entity.getSourceHtml() == null)
            entity.setSourceHtml(entity.getOriginalHtml());
        entity.initialDate();
    }


    @Override
    protected void preMod(EmailTemplate entity)
    {
        checkAllSpoofTargetsExist(entity);
        createParametersIfDoesNotExist(entity);
        entity.updateLastModified();
    }

    @Override
    protected EmailTemplate postGet(EmailTemplate entity)
    {
        return entity;
    }

    private void checkAllSpoofTargetsExist(EmailTemplate entity)
    {
        List<SpoofTarget> suppliedSpoofTargets = entity.getSpoofTargets();
        if (suppliedSpoofTargets == null) return;
        List<SpoofTarget> actualSpoofTargets = new ArrayList<>();
        for (SpoofTarget target : suppliedSpoofTargets)
        {
            long id = target.getId();
            if (id != 0)
            {
                if (spoofTargetService.exist(id))
                    target = spoofTargetService.get(id);
                else throw new ValidationException("Invalid Fields", "SpoofTarget with id: " + id + " does not exist");
            } else
            {
                String email = target.getEmailAddress();
                if (!spoofTargetService.exist(email))
                {
                    throw new ValidationException("Invalid Fields", "SpoofTarget with email:" + email + " does not exist.");
                }
                target = spoofTargetService.get(email);
            }
            actualSpoofTargets.add(target);
        }
        entity.setSpoofTargets(actualSpoofTargets);
    }

    private void createParametersIfDoesNotExist(EmailTemplate entity)
    {
        List<Parameter> suppliedParameters = entity.getParameters();
        List<Parameter> actualParameters = new ArrayList<>();

        if (suppliedParameters == null) return;
        for (Parameter parameter : entity.getParameters())
        {
            String name = parameter.getName();
            ParameterSourceType sourceType = parameter.getSourceType();
            switch (sourceType)
            {
                case phishingTarget:

                    actualParameters.add(parameterResolver.replaceWithActualIfExist(sourceType,name));
                    break;
                case spoofTarget:
                    validateParameterName(entity.getSpoofTargets(), name);
                    actualParameters.add(parameterResolver.replaceWithActualIfExist(sourceType, name));
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
