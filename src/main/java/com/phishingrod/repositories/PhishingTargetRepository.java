package com.phishingrod.repositories;

import com.phishingrod.domain.phishingTarget.PhishingTarget;

import java.util.Optional;

public interface PhishingTargetRepository extends EmailKeyedRepository<PhishingTarget>
{
    Optional<PhishingTarget> findDistinctByEmailAddress(String emailAddress);
}
