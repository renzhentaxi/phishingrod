package com.phishingrod.tools;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import javax.persistence.Entity;
import java.io.File;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class SchemaGen
{
    private final static String fileName = "db-schema.sql";
    private final static String domainPackage = "com.phishingrod.domain";

    public static void main(String[] args)
    {
        Map<String, String> settings = getSettings();
        ServiceRegistry sr = new StandardServiceRegistryBuilder().applySettings(settings).build();
        MetadataSources metadata = getMetadata(sr);
        File file = new File(fileName);
        file.delete();

        export(fileName, metadata, sr);
        System.exit(0);

    }

    private static Map<String, String> getSettings()
    {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        HashMap<String, String> settings = new HashMap<>();
        settings.put("connection.driver_class", "org.h2.driver");
        settings.put("dialect", "org.hibernate.dialect.H2Dialect");
        settings.put("hibernate.connection.url", "jdbc:h2:mem:");
        settings.put("hibernate.connection.username", "sa");
        settings.put("hibernate.connection.password", "");
        settings.put("hibernate.hbm2ddl.auto", "create");
        settings.put("show_sql", "false");
        return settings;

    }

    private static ClassPathScanningCandidateComponentProvider createComponentScanner()
    {
        ClassPathScanningCandidateComponentProvider provider
                = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
        return provider;
    }

    private static MetadataSources getMetadata(ServiceRegistry sr)
    {
        MetadataSources metadata = new MetadataSources(sr);
        ClassPathScanningCandidateComponentProvider provider = createComponentScanner();
        for (BeanDefinition beanDefinition : provider.findCandidateComponents(domainPackage))
        {
            metadata.addAnnotatedClassName(beanDefinition.getBeanClassName());
        }
        return metadata;
    }

    private static void export(String fileName, MetadataSources metadata, ServiceRegistry serviceRegistry)
    {
        SchemaExport schemaExport = new SchemaExport();
        schemaExport.setHaltOnError(false);
        schemaExport.setFormat(true);
        schemaExport.setDelimiter(";");
        schemaExport.setOutputFile(fileName);
        schemaExport.execute(EnumSet.of(TargetType.SCRIPT), SchemaExport.Action.CREATE, metadata.buildMetadata(), serviceRegistry);
    }
}
