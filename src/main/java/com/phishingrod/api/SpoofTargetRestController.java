package com.phishingrod.api;

import com.phishingrod.api.base.EntityRestController;
import com.phishingrod.core.domain.SpoofTarget;
import com.phishingrod.core.repository.SpoofTargetRepository;
import com.phishingrod.core.service.CrudServices.SpoofTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/spoofTarget")
public class SpoofTargetRestController extends EntityRestController<SpoofTarget, SpoofTargetRepository, SpoofTargetService>
{
    @Autowired
    public SpoofTargetRestController(SpoofTargetService service)
    {
        super(service);
    }
}

