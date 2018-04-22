package com.phishingrod.repositories.parameters;

import com.phishingrod.domain.next.components.params.Parameter;
import com.phishingrod.domain.next.phishingTarget.PhishingTarget;
import com.phishingrod.domain.next.phishingTarget.PhishingTargetParameter;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface PhishingTargetParameterRepository extends PagingAndSortingRepository<PhishingTargetParameter, Long>
{
    Optional<PhishingTargetParameter> findDistinctByEntityAndParameter(PhishingTarget target, Parameter parameter);

}
