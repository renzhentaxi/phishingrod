package com.phishingrod.api.phishingTarget.validation;

import com.phishingrod.services.PhishingTargetService;

import java.util.Map;

public class PhishingTargetIdValidator extends PhishingTargetValidator<Long>
{
    public PhishingTargetIdValidator(PhishingTargetService phishingTargetService)
    {
        super(phishingTargetService);
    }

    @Override
    public void validateThis(Long target, Map<String, String> errors)
    {
        if (!phishingTargetService.exist(target))
        {
            errors.put("Not Found", "No phishing target exist with Id: " + target);
        }
    }
}
