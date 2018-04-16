package com.phishingrod.core;

import com.phishingrod.domain.EmailTemplate;
import com.phishingrod.domain.PhishingTarget;
import com.phishingrod.services.EmailTemplateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages = "com.phishingrod.services")
@DataJpaTest
public class RepositoryTest
{
    @Autowired
    EmailTemplateService service;

    @Test
    public void test()
    {
//        EmailTemplate template = new EmailTemplate("hi", "boi", "wut", new Date());
//
//        service.saveTemplate(template);
        PhishingTarget target = new PhishingTarget("taxi@gmail.com");
        System.out.println(target.getId());
        System.out.println(service.getTemplate(1));
    }
}
