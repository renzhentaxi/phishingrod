package com.phishingrod.repositories;

import com.phishingrod.domain.PhishingTarget;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PhishingTargetRepository extends PagingAndSortingRepository<PhishingTarget, Long>
{
}
