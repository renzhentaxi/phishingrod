package com.phishingrod.core.service.CrudServices;

import com.phishingrod.core.domain.SenderServer;
import com.phishingrod.core.repository.SenderServerRepository;
import com.phishingrod.core.service.serviceAddons.StatTrackerAddonProvider;
import com.phishingrod.core.service.base.NameKeyedEntityService;
import org.springframework.stereotype.Service;

import static com.phishingrod.core.service.base.EntityServiceAddonType.preAdd;
import static com.phishingrod.core.service.base.EntityServiceAddonType.preMod;

@Service
public class SenderServerService extends NameKeyedEntityService<SenderServer, SenderServerRepository>
{
    public SenderServerService(SenderServerRepository repository)
    {
        super(repository, "Sender Server");
        registerAddon(preAdd, StatTrackerAddonProvider::onCreate);
        registerAddon(preMod, StatTrackerAddonProvider::onUpdate);
    }
}
