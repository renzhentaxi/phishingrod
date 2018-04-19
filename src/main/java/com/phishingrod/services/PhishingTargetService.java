package com.phishingrod.services;

import com.phishingrod.domain.PhishingTarget;
import com.phishingrod.domain.parameters.ParameterSourceType;
import com.phishingrod.repositories.PhishingTargetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        //basic validation
        if (target.getEmailAddress().trim().isEmpty()) return false;

        //set dates
        Date current = new Date();
        target.setCreatedAt(current);
        target.setLastModified(current);
        //sync parameters
        target = parameterResolverService.toRelational(target, ParameterSourceType.phishingTarget);

        //persist
        repository.save(target);
        return true;
    }

    public void modify(PhishingTarget target)
    {
        //set date
        Date current = new Date();
        target.setLastModified(current);

        //sync parameters
        target = parameterResolverService.toRelational(target, ParameterSourceType.phishingTarget);

        //persist
        repository.save(target);
    }

    public PhishingTarget get(long id)
    {
        return repository.findById(id).map(parameterResolverService::toDomain).orElse(null);
    }

    public Optional<PhishingTarget> get(String emailAddress)
    {
        return repository.findDistinctByEmailAddress(emailAddress);
    }

    public List<PhishingTarget> getAll()
    {
        List<PhishingTarget> targets = new ArrayList<>();
        repository.findAll().forEach(phishingTarget -> targets.add(parameterResolverService.toDomain(phishingTarget)));
        return targets;
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