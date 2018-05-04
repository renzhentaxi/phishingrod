//package com.phishingrod.tools;
//
//import com.phishingrod.domain.phishingTarget.PhishingTarget;
//import com.phishingrod.services.PhishingTargetService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DatabasePopulator implements CommandLineRunner
//{
//    private PhishingTargetService service;
//
//    @Autowired
//    public DatabasePopulator(PhishingTargetService repository)
//    {
//        this.service = repository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception
//    {
//        for (int i = 0; i < 10; i++)
//        {
//            service.add(new PhishingTarget("txi@gamil.com" + i));
//        }
//
//        PhishingTarget target = service.get(1);
//        target.addParameter("parameter1", "parametervalue2");
//        service.modify(target);
//    }
//
//
//}