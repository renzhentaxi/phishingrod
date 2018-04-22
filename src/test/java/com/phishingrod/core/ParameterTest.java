package com.phishingrod.core;

import com.phishingrod.domain.next.phishingTarget.PhishingTarget;
import com.phishingrod.services.PhishingTargetService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        phishingTargetService.add(target);
        target.removeParameter("userName");
//        target.addParameterOld("userName","renzhentaxi");
    }
}
