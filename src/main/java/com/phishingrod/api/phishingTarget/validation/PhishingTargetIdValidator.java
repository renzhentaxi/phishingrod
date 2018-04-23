package com.phishingrod.api.phishingTarget.validation;

import com.phishingrod.api.phishingTarget.SimpleValidator;
import com.phishingrod.services.PhishingTargetService;

import java.util.Map;

public class PhishingTargetIdValidator extends SimpleValidator<Long>
{
    private PhishingTargetService phishingTargetService;

    public PhishingTargetIdValidator(PhishingTargetService phishingTargetService)
    {

        this.phishingTargetService = phishingTargetService;
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
