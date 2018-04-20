package com.phishingrod.api.phishingTarget.validation;

import com.phishingrod.api.phishingTarget.SimpleValidator;
import com.phishingrod.domain.PhishingTarget;
import com.phishingrod.services.PhishingTargetService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PhishingTargetModValidator extends SimpleValidator<PhishingTarget>
{
    private PhishingTargetService phishingTargetService;

    public PhishingTargetModValidator(PhishingTargetService phishingTargetService)
    {
        this.phishingTargetService = phishingTargetService;
    }

    @Override
    public void validateThis(PhishingTarget target, Map<String, String> errors)
    {

        String emailAddress = target.getEmailAddress();
        if (emailAddress == null)
        {
            System.out.println(target.getParameterMap()==null);
            System.out.println(target.getParameterMap());
            if (target.getParameterMap() == null)
            {
                errors.put("Missing Fields", "Either emailAddress and/or parameters should be supplied in the request body");
            }
        } else if (emailAddress.trim().isEmpty())
        {
            errors.put("Blank Email Address", "The emailAddress field is empty.");
        } else
        {

            PhishingTarget old = phishingTargetService.simpleGet(emailAddress);
            if (old != null && old.getId() != target.getId())
                errors.put("Conflict", "The email address: " + emailAddress + " is associated with another phishing target");

        }
    }
}
