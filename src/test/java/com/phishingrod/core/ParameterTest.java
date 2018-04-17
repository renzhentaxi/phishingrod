package com.phishingrod.core;

import com.phishingrod.domain.PhishingTarget;
import com.phishingrod.services.PhishingTargetService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ParameterTest
{
    @TestConfiguration
    @EntityScan(basePackages = {"com.phishingrod.domain"})
    @ComponentScan(basePackages = {"com.phishingrod.repositories"})
    static class config
    {
    }

    @Autowired
    private PhishingTargetService phishingTargetService;

    @Test
    public void test()
    {

        PhishingTarget target = new PhishingTarget("taxi@gmail.com");
        phishingTargetService.add(target);
//        target.addParameter("userName","renzhentaxi");
        for (PhishingTarget pt : phishingTargetService.getAll())
        {
            System.out.println(pt);
        }
    }
}
