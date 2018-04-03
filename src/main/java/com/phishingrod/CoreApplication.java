package com.phishingrod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.phishingrod.domain")
public class CoreApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(CoreApplication.class, args);
    }
}
