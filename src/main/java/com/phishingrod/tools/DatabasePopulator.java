package com.phishingrod.tools;

import com.phishingrod.domain.PhishingTarget;
import com.phishingrod.repositories.PhishingTargetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabasePopulator implements CommandLineRunner {
    private PhishingTargetRepository repository;
    
    @Autowired
    public DatabasePopulator(PhishingTargetRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("I am runnning");
    }

    
}