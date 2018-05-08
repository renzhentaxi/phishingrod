package com.phishingrod.core.service.CrudServices;

import com.phishingrod.core.domain.Sender;
import com.phishingrod.core.domain.SenderServer;
import com.phishingrod.core.exceptions.UnknownIdValidationException;
import com.phishingrod.core.repository.SenderRepository;
import com.phishingrod.core.service.base.NameKeyedEntityService;
import com.phishingrod.core.service.serviceAddons.StatTrackerAddonProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import static com.phishingrod.core.service.base.EntityServiceAddonType.*;

@Service
public class SenderService extends NameKeyedEntityService<Sender, SenderRepository>
{
    private SenderServerService senderServerService;

    @Autowired
    public SenderService(SenderRepository repository, SenderServerService senderServerService)
    {
        super(repository, "Sender");
        this.senderServerService = senderServerService;
        registerAddon(preAdd, StatTrackerAddonProvider::onCreate);
        registerAddon(preMod, this::merge);
        registerAddon(preMod, StatTrackerAddonProvider::onUpdate);
        registerAddon(modValidation, this::checkServerIsKnown);
        registerAddon(addValidation, this::checkServerIsKnown);
    }

    private Sender merge(Sender change)
    {
        Sender original = find(change.getId());
        if (change.getName() != null) original.setName(change.getName());
        if (change.getServer() != null) original.setServer(change.getServer());
        if (change.getPassword() != null) original.setPassword(change.getPassword());
        return original;
    }

    public Sender checkServerIsKnown(Sender sender)
    {
        if (!senderServerService.exist(sender.getServer().getName()))
        {
            throw new UnknownIdValidationException("Sender Server", "name", sender.getServer().getName());
        }
        return sender;
    }

    public Iterable<Sender> get(SenderServer server)
    {
        return repository.findAllByServer(server);
    }

    public Sender getRandom()
    {
        long count = repository.count();
        if (count == 0) throw new RuntimeException("No sender in database at all!!");
        int randomIndex = (int) (Math.random() * count);
        Page<Sender> senderPage = repository.findAll(PageRequest.of(randomIndex, 1));
        Sender sender = senderPage.getContent().get(0);

        return runAddons(sender, postExtract);
    }
}
