package com.phishingrod.core.repository.parameters;

import com.phishingrod.core.domain.parameters.Parameter;
import com.phishingrod.core.domain.parameters.ParameterContainerEntity;
import com.phishingrod.core.domain.parameters.ParameterInstance;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface ParameterInstanceRepository extends CrudRepository<ParameterInstance, Long>
{
    Optional<ParameterInstance> findDistinctByParameterAndContainer(Parameter parameter, ParameterContainerEntity container);

    Set<ParameterInstance> findAllByParameter(Parameter parameter);
}
