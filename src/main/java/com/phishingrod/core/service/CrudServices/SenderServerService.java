package com.phishingrod.core.service.CrudServices;

import com.phishingrod.core.domain.SenderServer;
import com.phishingrod.core.repository.SenderServerRepository;
import com.phishingrod.core.service.base.NameKeyedEntityService;
import com.phishingrod.core.service.serviceAddons.StatTrackerAddonProvider;
import org.springframework.stereotype.Service;

import static com.phishingrod.core.service.base.EntityServiceAddonType.preAdd;
import static com.phishingrod.core.service.base.EntityServiceAddonType.preMod;

@Service
public class SenderServerService extends NameKeyedEntityService<SenderServer, SenderServerRepository>
{
    public SenderServerService(SenderServerRepository repository)
    {
        super(repository, "Sender Server");
        registerAddon(preMod, this::merge);
        registerAddon(preAdd, StatTrackerAddonProvider::onCreate);
        registerAddon(preMod, StatTrackerAddonProvider::onUpdate);
    }

    private SenderServer merge(SenderServer change)
    {
        SenderServer original = find(change.getId());
        if (change.getName() != null) original.setName(change.getName());
        if (change.getHost() != null) original.setHost(change.getHost());
        original.setPort(change.getPort());
        return original;
    }
}
