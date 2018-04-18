package com.phishingrod.services;

import com.phishingrod.domain.PhishingTarget;
import com.phishingrod.domain.parameters.ParameterSourceType;
import com.phishingrod.repositories.PhishingTargetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PhishingTargetService
{
    private PhishingTargetRepository repository;
    private ParameterResolverService parameterResolverService;

    @Autowired
    public PhishingTargetService(PhishingTargetRepository repository, ParameterResolverService parameterResolverService)
    {
        this.repository = repository;
        this.parameterResolverService = parameterResolverService;
    }

    public boolean add(PhishingTarget target)
    {
        if (target.getEmailAddress().trim().isEmpty()) return false;
        Date current = new Date();
        target.setCreatedAt(current);
        target.setLastModified(current);
        target = parameterResolverService.syncToDatabase(target, ParameterSourceType.phishingTarget);
        repository.save(target);
        return true;
    }

    public void modify(PhishingTarget target)
    {
        Date current = new Date();
        target.setLastModified(current);
        target = parameterResolverService.syncToDatabase(target, ParameterSourceType.phishingTarget);
        repository.save(target);
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


}