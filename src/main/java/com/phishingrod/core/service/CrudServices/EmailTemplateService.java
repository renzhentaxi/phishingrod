package com.phishingrod.core.service.CrudServices;

import com.phishingrod.core.domain.EmailTemplate;
import com.phishingrod.core.domain.SpoofTarget;
import com.phishingrod.core.domain.parameters.Parameter;
import com.phishingrod.core.repository.EmailTemplateRepository;
import com.phishingrod.core.service.base.EntityServiceAddonType;
import com.phishingrod.core.service.base.NameKeyedEntityService;
import com.phishingrod.core.service.serviceAddons.StatTrackerAddonProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmailTemplateService extends NameKeyedEntityService<EmailTemplate, EmailTemplateRepository>
{
    private SpoofTargetService spoofTargetService;
    private PhishingTargetService phishingTargetService;
    private ParameterService parameterService;

    @Autowired
    public EmailTemplateService(EmailTemplateRepository repository, SpoofTargetService spoofTargetService, PhishingTargetService phishingTargetService, ParameterService parameterService)
    {
        super(repository, "Email Template");
        this.spoofTargetService = spoofTargetService;
        this.phishingTargetService = phishingTargetService;
        this.parameterService = parameterService;

        registerAddon(EntityServiceAddonType.preInsert, this::preInsert);
        registerAddon(EntityServiceAddonType.preAdd, StatTrackerAddonProvider::onCreate);
        registerAddon(EntityServiceAddonType.preMod, StatTrackerAddonProvider::onUpdate);
        registerAddon(EntityServiceAddonType.preMod, this::merge);
    }

    private EmailTemplate merge(EmailTemplate change)
    {
        EmailTemplate original = find(change.getId());

        if (change.getHtml() != null) original.setHtml(change.getHtml());
        if (change.getName() != null) original.setName(change.getName());
        if (change.getParameters() != null) original.replaceParameters(change.getParameters());
        return original;
    }

    private boolean isValidSpoofTarget(EmailTemplate template, SpoofTarget spoofTarget)
    {
        for (Parameter p : template.getParameters())
        {
            if (!spoofTarget.hasParameter(p.getName()))
            {
                return false;
            }
        }
        return true;
    }

    private EmailTemplate preInsert(EmailTemplate template)
    {
        template.replaceParameters(template.getParameters().stream().map(parameter -> parameterService.valueOf(parameter.getSourceType(), parameter.getName())).collect(Collectors.toSet()));
        return template;
    }
}
