package com.phishingrod.core.service.CrudServices;

import com.phishingrod.core.domain.PhishingTarget;
import com.phishingrod.core.exceptions.MissingRequiredValidationException;
import com.phishingrod.core.repository.PhishingTargetRepository;
import com.phishingrod.core.service.base.EmailKeyedEntityService;
import com.phishingrod.core.service.serviceAddons.StatTrackerAddonProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.phishingrod.core.service.base.EntityServiceAddonType.*;

@Service
public class PhishingTargetService extends EmailKeyedEntityService<PhishingTarget, PhishingTargetRepository>
{
    @Autowired
    public PhishingTargetService(PhishingTargetRepository repository, ParameterService service)
    {
        super(repository, "Phishing Target");
        registerAddon(preAdd, StatTrackerAddonProvider::onCreate);
        registerAddon(preMod, StatTrackerAddonProvider::onUpdate);
        registerAddon(preMod, this::merge);
        registerAddon(addValidation, this::validateInsert);
        registerAddon(preInsert, service::syncUsingMap);
        registerAddon(postExtract, service::syncUsingSet);
    }

    private PhishingTarget merge(PhishingTarget change)
    {
        PhishingTarget original = find(change.getId());
        if (change.getEmailAddress() != null) original.setEmailAddress(change.getEmailAddress());
        if (change.getParameterMap() != null) original.setParameterMap(change.getParameterMap());
        return original;
    }

    private PhishingTarget validateInsert(PhishingTarget target)
    {
        if (target.getEmailAddress() == null)
            throw new MissingRequiredValidationException(entityType, "emailAddress");
        return target;
    }
}
