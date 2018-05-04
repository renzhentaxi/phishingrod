package com.phishingrod.api;

import com.phishingrod.api.base.EntityRestController;
import com.phishingrod.core.domain.Sender;
import com.phishingrod.core.repository.SenderRepository;
import com.phishingrod.core.service.CrudServices.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/sender")
public class SenderRestController extends EntityRestController<Sender, SenderRepository, SenderService>
{
    @Autowired
    public SenderRestController(SenderService service)
    {
        super(service);
    }
}
