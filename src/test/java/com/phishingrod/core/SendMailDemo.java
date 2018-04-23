package com.phishingrod.core;

import com.phishingrod.domain.Sender;
import com.phishingrod.domain.SenderServer;
import com.phishingrod.domain.phishingTarget.PhishingTarget;
import com.phishingrod.services.SenderService;
import com.phishingrod.spoofTarget.SpoofTarget;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@ComponentScan(basePackages = "com.phishingrod.services")
public class SendMailDemo
{
    @Autowired
    SenderService service;

    @Test
    public void demo()
    {
        SpoofTarget from = new SpoofTarget("renzhentaxibaerde@gmail.com");
        PhishingTarget to = new PhishingTarget("phishingrod123@gmail.com");

        SenderServer server = new SenderServer("gmail", "smtp.gmail.com", true, 587);
        Sender sender = new Sender(server, "phishingrod123@gmail.com", "2132018fish");
        service.send("hi", from, to, sender);
    }
}