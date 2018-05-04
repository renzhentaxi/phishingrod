package com.phishingrod.api;

import com.phishingrod.api.base.EntityRestController;
import com.phishingrod.core.domain.PhishingTarget;
import com.phishingrod.core.repository.PhishingTargetRepository;
import com.phishingrod.core.service.CrudServices.PhishingTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/phishingTarget")
public class PhishingTargetRestController extends EntityRestController<PhishingTarget, PhishingTargetRepository, PhishingTargetService>
{
    @Autowired
    public PhishingTargetRestController(PhishingTargetService service)
    {
        super(service);
    }
}
