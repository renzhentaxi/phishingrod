package com.phishingrod.core;

import com.phishingrod.CoreApplication;
import com.phishingrod.domain.PhishingTarget;
import com.phishingrod.domain.parameters.Parameter;
import com.phishingrod.domain.parameters.ParameterSourceType;
import com.phishingrod.services.PhishingTargetService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParameterTest
{

    @Autowired
    private PhishingTargetService phishingTargetService;

    @Test
    public void test()
    {

        PhishingTarget target = new PhishingTarget("taxi@gmail.com");
        target.addParameter(new Parameter(ParameterSourceType.phishingTarget, "userName"), "taxi");
        phishingTargetService.add(target);
        target.removeParameter("userName");
//        target.addParameter("userName","renzhentaxi");
    }
}
