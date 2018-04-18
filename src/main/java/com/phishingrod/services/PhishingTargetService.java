package com.phishingrod.services;

import com.phishingrod.domain.PhishingTarget;
import com.phishingrod.domain.parameters.PhishingTargetParameter;
import com.phishingrod.repositories.PhishingTargetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PhishingTargetService
{
    private PhishingTargetRepository repository;
    private ParameterResolverService parameterResolverService;

    @Autowired
    public PhishingTargetService(PhishingTargetRepository repository)
    {
        this.repository = repository;
    }

    public boolean add(PhishingTarget target)
    {
        if (target.getEmailAddress().trim().isEmpty()) return false;
        Date current = new Date();
        target.setCreatedAt(current);
        target.setLastModified(current);
        repository.save(target);
        return true;
    }

    public void save(PhishingTarget target)
    {
        Date current = new Date();
        target.setLastModified(current);
//        Map<String, String> map = target.getParameterMap();
//        for (Map.Entry<String, String> entry : map.entrySet())
//        {
//
//        }
        repository.save(target);
    }

    public void addAll(List<PhishingTarget> targets)
    {
        for (PhishingTarget target : targets)
        {
            add(target);
        }
    }

    public Optional<PhishingTarget> get(long id)
    {
        return repository.findById(id);
    }

    public Optional<PhishingTarget> get(String emailAddress)
    {
        return repository.findDistinctByEmailAddress(emailAddress);
    }

    public Iterable<PhishingTarget> getAll()
    {
        return repository.findAll();
    }

    public boolean delete(long id)
    {
        if (repository.findById(id).isPresent())
        {
            repository.deleteById(id);
            return true;
        } else return false;
    }


    private PhishingTarget syncToDatabase(PhishingTarget target)
    {
        target.getParameters().clear();
        for (Map.Entry<String, String> entry : target.getParameterMap().entrySet())
        {
            String name = entry.getKey();
            String value = entry.getValue();
            PhishingTargetParameter parameter = parameterResolverService.valueOf(target, name);
            parameter.setValue(value);
            target.getParameters().add(parameter);
        }
        return target;
    }

    private void syncFromDatabase(PhishingTarget target)
    {

    }
}