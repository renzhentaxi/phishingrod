package com.phishingrod.core;

import com.phishingrod.core.domain.SpoofTarget;
import com.phishingrod.core.domain.parameters.Parameter;
import com.phishingrod.core.domain.parameters.ParameterSourceType;
import com.phishingrod.core.service.CrudServices.ParameterService;
import com.phishingrod.core.service.CrudServices.SpoofTargetService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ParameterServiceTest
{
    @Autowired
    private ParameterService service;

    @Autowired
    private SpoofTargetService spoofTargetService;

    @Test
    public void getParameterInstances()
    {
        SpoofTarget sp = UniqueEntityProvider.makeUniqueSpoofTarget("name", "taxi");
        spoofTargetService.add(sp);
        Parameter p = service.valueOf(ParameterSourceType.spoofTarget, "name");

        service.getInstances(p);
    }
}
