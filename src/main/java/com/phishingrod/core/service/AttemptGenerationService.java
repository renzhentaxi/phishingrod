package com.phishingrod.core.service;

import com.phishingrod.core.domain.EmailTemplate;
import com.phishingrod.core.domain.PhishingTarget;
import com.phishingrod.core.domain.SpoofTarget;
import com.phishingrod.core.domain.parameters.Parameter;
import com.phishingrod.core.domain.parameters.ParameterContainerEntity;
import com.phishingrod.core.domain.parameters.ParameterSourceType;
import com.phishingrod.core.service.CrudServices.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AttemptGenerationService
{
    private SenderService senderService;
    private SpoofTargetService spoofTargetService;
    private EmailTemplateService emailTemplateService;
    private PhishingTargetService phishingTargetService;
    private AttemptService attemptService;
    private ParameterService parameterService;

    @Autowired
    public AttemptGenerationService(SenderService senderService, SpoofTargetService spoofTargetService, EmailTemplateService emailTemplateService, PhishingTargetService phishingTargetService, AttemptService attemptService, ParameterService parameterService)
    {
        this.senderService = senderService;
        this.spoofTargetService = spoofTargetService;
        this.emailTemplateService = emailTemplateService;
        this.phishingTargetService = phishingTargetService;
        this.attemptService = attemptService;
        this.parameterService = parameterService;
    }

    public List<SpoofTarget> retrieveCompatibleSpoofTargetsFor(EmailTemplate emailTemplate)
    {
        List<Parameter> spoofParameters = emailTemplate.getParameters().stream().filter(parameter -> parameter.getSourceType() == ParameterSourceType.spoofTarget).collect(Collectors.toList());
        if (spoofParameters.isEmpty()) return spoofTargetService.all();

        return selectCommonParameterContainers(spoofParameters);
    }

    public List<PhishingTarget> retrieveCompatiblePhishingTargetFor(EmailTemplate emailTemplate)
    {
        List<Parameter> phishingParameters = emailTemplate.getParameters().stream().filter(parameter -> parameter.getSourceType() == ParameterSourceType.phishingTarget).collect(Collectors.toList());
        if (phishingParameters.isEmpty()) return phishingTargetService.all();

        return selectCommonParameterContainers(phishingParameters);
    }

    public List<EmailTemplate> retrieveCompatibleEmailTemplateFor(SpoofTarget spoofTarget)
    {
        return retrieveCompatibleEmailTemplateFor(spoofTarget, ParameterSourceType.spoofTarget);
    }

    public List<EmailTemplate> retrieveCompatibleEmailTemplateFor(PhishingTarget phishingTarget)
    {
        return retrieveCompatibleEmailTemplateFor(phishingTarget, ParameterSourceType.phishingTarget);
    }

    private List<EmailTemplate> retrieveCompatibleEmailTemplateFor(ParameterContainerEntity container, ParameterSourceType sourceType)
    {
        List<EmailTemplate> templates = emailTemplateService.all();

        return templates.stream()
                .filter(template -> template.getParameters().stream().allMatch(parameter -> parameter.getSourceType() != sourceType || container.hasParameter(parameter.getName())))
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private <E extends ParameterContainerEntity> List<E> selectCommonParameterContainers(List<Parameter> parameters)
    {
        Set<ParameterContainerEntity> candidates = parameterService.getContainers(parameters.get(0));
        Set<ParameterContainerEntity> futureCandidates = new HashSet<>();

        for (int i = 1; i < parameters.size() && !candidates.isEmpty(); i++)
        {
            futureCandidates.clear();
            Set<ParameterContainerEntity> newCandidates = parameterService.getContainers(parameters.get(i));

            for (ParameterContainerEntity c : candidates)
            {
                if (newCandidates.contains(c)) futureCandidates.add(c);
            }
            Set<ParameterContainerEntity> temp = candidates;
            candidates = futureCandidates;
            futureCandidates = temp;
        }
        return candidates.stream().map(containerEntity -> (E) containerEntity).collect(Collectors.toList());
    }
}
