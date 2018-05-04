package com.phishingrod.core.service.CrudServices;

import com.phishingrod.core.domain.parameters.Parameter;
import com.phishingrod.core.domain.parameters.ParameterContainerEntity;
import com.phishingrod.core.domain.parameters.ParameterInstance;
import com.phishingrod.core.domain.parameters.ParameterSourceType;
import com.phishingrod.core.repository.parameters.ParameterInstanceRepository;
import com.phishingrod.core.repository.parameters.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ParameterService
{
    private ParameterRepository repository;
    private ParameterInstanceRepository instanceRepository;

    @Autowired
    public ParameterService(ParameterRepository repository, ParameterInstanceRepository instanceRepository)
    {
        this.repository = repository;
        this.instanceRepository = instanceRepository;
    }

    public Parameter valueOf(ParameterSourceType sourceType, String name)
    {
        return repository
                .findDistinctBySourceTypeAndName(sourceType, name)
                .orElseGet(() -> repository.save(new Parameter(sourceType, name)));
    }

    private ParameterInstance getInstance(Parameter parameter, ParameterContainerEntity container)
    {
        if (container.getId() == 0)
        {
            return new ParameterInstance(parameter, container);
        } else
        {
            return instanceRepository
                    .findDistinctByParameterAndContainer(parameter, container)
                    .orElseGet(() -> new ParameterInstance(parameter, container));
        }
    }

    public <T extends ParameterContainerEntity> T syncUsingMap(T containerEntity)
    {
        if (containerEntity.getParameterMap() == null)
        {
            containerEntity.setParameterMap(new HashMap<>());
            return containerEntity;
        }


        ParameterSourceType sourceType = containerEntity.getSourceType();

        Set<ParameterInstance> parameterSet = containerEntity.getParameterMap().entrySet().stream().map(nameValueEntry ->
                {
                    Parameter parameter = valueOf(sourceType, nameValueEntry.getKey());
                    ParameterInstance instance = getInstance(parameter, containerEntity);
                    instance.setValue(nameValueEntry.getValue());
                    return instance;
                }
        ).collect(Collectors.toSet());

        Set<ParameterInstance> oldSet = containerEntity.getParameterSet();

        if (oldSet != null)
        {
            oldSet.clear();
            oldSet.addAll(parameterSet);
            parameterSet = oldSet;
        }

        containerEntity.setParameterSet(parameterSet);
        return containerEntity;
    }

    public <T extends ParameterContainerEntity> T syncUsingSet(T containerEntity)
    {
        Map<String, String> map = containerEntity.getParameterSet().stream()
                .collect(
                        Collectors
                                .toMap(parameterInstance -> parameterInstance.getParameter().getName(),
                                        ParameterInstance::getValue));

        containerEntity.setParameterMap(map);
        return containerEntity;
    }

    public Set<ParameterInstance> getInstances(Parameter parameter)
    {
        return instanceRepository.findAllByParameter(parameter);
    }

    public Set<ParameterContainerEntity> getContainers(Parameter parameter)
    {
        return getInstances(parameter).stream().map(ParameterInstance::getContainer).collect(Collectors.toSet());
    }
}
