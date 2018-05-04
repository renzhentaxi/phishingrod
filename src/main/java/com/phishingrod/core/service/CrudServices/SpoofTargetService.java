package com.phishingrod.core.service.CrudServices;

import com.phishingrod.core.domain.SpoofTarget;
import com.phishingrod.core.repository.SpoofTargetRepository;
import com.phishingrod.core.service.serviceAddons.StatTrackerAddonProvider;
import com.phishingrod.core.service.base.EmailKeyedEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.phishingrod.core.service.base.EntityServiceAddonType.*;

@Service
public class SpoofTargetService extends EmailKeyedEntityService<SpoofTarget, SpoofTargetRepository>
{
    @Autowired
    public SpoofTargetService(SpoofTargetRepository repository, ParameterService service)
    {
        super(repository, "Spoof Target");
        registerAddon(preAdd, StatTrackerAddonProvider::onCreate);
        registerAddon(preMod, StatTrackerAddonProvider::onUpdate);
        registerAddon(preMod, this::merge);
        registerAddon(preInsert, service::syncUsingMap);
        registerAddon(postExtract, service::syncUsingSet);
    }

    private SpoofTarget merge(SpoofTarget change)
    {
        SpoofTarget original = find(change.getId());
        if (change.getEmailAddress() != null) original.setEmailAddress(change.getEmailAddress());
        if (change.getParameterMap() != null) original.setParameterMap(change.getParameterMap());
        return original;
    }
}
