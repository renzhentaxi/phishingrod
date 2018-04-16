package com.phishingrod.tools;

import com.phishingrod.domain.PhishingTarget;
import com.phishingrod.services.PhishingTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabasePopulator implements CommandLineRunner
{
    private PhishingTargetService service;

    @Autowired
    public DatabasePopulator(PhishingTargetService service)
    {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception
    {

        service.addAll(targetGenerator("fakeEmail@gmail.com", 10));
        System.out.println("I am runnning");
    }

    public List<PhishingTarget> targetGenerator(String prefix, int count)
    {
        List<PhishingTarget> targets = new ArrayList<>(count);
        for (int i = 0; i < count; i++)
        {
            targets.add(new PhishingTarget(prefix + i));
        }
        return targets;
    }
}