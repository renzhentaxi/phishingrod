package com.phishingrod.repositories;

import com.phishingrod.domain.next.phishingTarget.PhishingTarget;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface PhishingTargetRepository extends PagingAndSortingRepository<PhishingTarget, Long>
{
    Optional<PhishingTarget> findDistinctByEmailAddress(String emailAddress);
}
