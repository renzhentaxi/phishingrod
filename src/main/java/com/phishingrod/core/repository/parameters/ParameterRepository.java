package com.phishingrod.core.repository.parameters;

import com.phishingrod.core.domain.parameters.Parameter;
import com.phishingrod.core.domain.parameters.ParameterSourceType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ParameterRepository extends CrudRepository<Parameter, Long>
{
    Optional<Parameter> findDistinctBySourceTypeAndName(ParameterSourceType sourceType, String name);
}
