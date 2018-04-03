package com.phishingrod.repositories;

import com.phishingrod.domain.PhishingTarget;
import org.springframework.data.repository.CrudRepository;

public interface PhishingTargetRepository extends CrudRepository<PhishingTarget, Long>
{
}
