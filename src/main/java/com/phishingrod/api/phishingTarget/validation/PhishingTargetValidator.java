package com.phishingrod.api.phishingTarget.validation;

import com.phishingrod.services.PhishingTargetService;

import java.util.HashMap;
import java.util.Map;

public abstract class PhishingTargetValidator<T>
{
    protected PhishingTargetService phishingTargetService;

    public PhishingTargetValidator(PhishingTargetService phishingTargetService)
    {
        this.phishingTargetService = phishingTargetService;
    }

    public void validate(T target)
    {
        Map<String, String> errors = new HashMap<>();
        validateThis(target, errors);
        if (!errors.isEmpty()) throw new ValidationException(errors);
    }

    public abstract void validateThis(T target, Map<String, String> errors);
}
