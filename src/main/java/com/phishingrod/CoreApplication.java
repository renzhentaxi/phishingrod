package com.phishingrod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EntityScan(basePackages = "com.phishingrod")
//@ComponentScan(basePackages = {"com.phishingrod"})
public class CoreApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(CoreApplication.class, args);
    }
}
