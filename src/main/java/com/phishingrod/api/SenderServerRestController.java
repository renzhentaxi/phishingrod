package com.phishingrod.api;

import com.phishingrod.api.base.EntityRestController;
import com.phishingrod.core.domain.SenderServer;
import com.phishingrod.core.repository.SenderServerRepository;
import com.phishingrod.core.service.CrudServices.SenderServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/senderServer")
public class SenderServerRestController extends EntityRestController<SenderServer, SenderServerRepository, SenderServerService>
{
    @Autowired
    public SenderServerRestController(SenderServerService service)
    {
        super(service);
    }
}
