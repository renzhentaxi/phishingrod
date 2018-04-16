package com.phishingrod.services;

import com.phishingrod.domain.PhishingTarget;
import com.phishingrod.repositories.PhishingTargetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PhishingTargetService
{
    private PhishingTargetRepository repository;

    @Autowired
    public PhishingTargetService(PhishingTargetRepository repository)
    {
        this.repository = repository;
    }

    public boolean add(PhishingTarget target)
    {
        if(target.getEmailAddress().trim().isEmpty()) return false;
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
        repository.save(target);
    }

    public void addAll(List<PhishingTarget> targets)
    {
        for (PhishingTarget target : targets)
        {
            add(target);
        }
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