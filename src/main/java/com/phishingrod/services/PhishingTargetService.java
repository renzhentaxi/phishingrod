package com.phishingrod.services;

import com.phishingrod.domain.PhishingTarget;
import com.phishingrod.domain.parameters.ParameterSourceType;
import com.phishingrod.repositories.PhishingTargetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public PhishingTarget simpleGet(long id)
    {
        return repository.findById(id).orElse(null);
    }

    public PhishingTarget simpleGet(String emailAddress)
    {
        return repository.findDistinctByEmailAddress(emailAddress).orElse(null);
    }


    public PhishingTarget get(long id)
    {
        PhishingTarget target = simpleGet(id);
        return target != null ? parameterResolverService.toDomain(target) : null;
    }

    public PhishingTarget get(String emailAddress)
    {
        return repository.findDistinctByEmailAddress(emailAddress).map(parameterResolverService::toDomain).orElse(null);
    }

    public PhishingTarget add(PhishingTarget target)
    {
        //basic validation
        if (target.getEmailAddress().trim().isEmpty()) throw new RuntimeException("Invalid email addresss");

        //set dates
        Date current = new Date();
        target.setCreatedAt(current);
        target.setLastModified(current);
        //sync parameterList
        target = parameterResolverService.toRelational(target, ParameterSourceType.phishingTarget);

        //persist
        repository.save(target);
        return target;
    }

    public PhishingTarget modify(PhishingTarget target)
    {
        PhishingTarget original = simpleGet(target.getId());

        if (target.getParameterMap() != null)
        {
            original.setParameterMap(target.getParameterMap());
            original = parameterResolverService.toRelational(original, ParameterSourceType.phishingTarget);
        }
        if (target.getEmailAddress() != null)
            original.setEmailAddress(target.getEmailAddress());
        original.setLastModified(new Date());

        //persist
        return repository.save(original);
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

    public boolean exist(String emailAddress)
    {
        return repository.findDistinctByEmailAddress(emailAddress).isPresent();
    }

    public boolean exist(long id)
    {
        return repository.findById(id).isPresent();
    }
}