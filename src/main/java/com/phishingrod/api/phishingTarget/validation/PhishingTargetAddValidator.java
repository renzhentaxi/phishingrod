package com.phishingrod.api.phishingTarget.validation;

import com.phishingrod.domain.PhishingTarget;
import com.phishingrod.services.PhishingTargetService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PhishingTargetAddValidator extends PhishingTargetValidator<PhishingTarget>
{

    public PhishingTargetAddValidator(PhishingTargetService phishingTargetService)
    {
        super(phishingTargetService);
    }

    @Override
    public void validateThis(PhishingTarget target, Map<String, String> errors)
    {
        String emailAddress = target.getEmailAddress();
        if (emailAddress == null || emailAddress.trim().isEmpty())
            errors.put("Missing", "required emailAddress is missing or empty");
        else if (phishingTargetService.exist(emailAddress))
            errors.put("Conflict", "PhishingTarget with the same email Address already exist on the database");
    }
}
