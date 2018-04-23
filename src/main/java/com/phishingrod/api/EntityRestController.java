package com.phishingrod.api;

import com.fasterxml.jackson.annotation.JsonView;
import com.phishingrod.domain.components.PhishingRodEntity;
import com.phishingrod.services.entity.EntityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public abstract class EntityRestController<E extends PhishingRodEntity, S extends EntityService<E>>
{
    protected S service;

    public EntityRestController(S service)
    {
        this.service = service;
    }

    public abstract void validateAdd(E toAdd);

    public abstract void validateGet(long id);

    public abstract void validateDelete(long id);

    public abstract void validateModify(E toModify);

    public MappingJacksonValue convertToJackson(E entity, Class<?> view)
    {
        MappingJacksonValue result = new MappingJacksonValue(entity);
        result.setSerializationView(view);
        return result;
    }

    @PostMapping("add")
    public MappingJacksonValue add(@RequestBody E toAdd)
    {
        validateAdd(toAdd);
        service.add(toAdd);
        return convertToJackson(toAdd, RestView.Add.class);
    }

    @GetMapping("get")
    @JsonView(RestView.Get.class)
    public E get(@RequestParam("id") long id)
    {
        validateGet(id);
        return service.get(id);
    }

    @PostMapping("del")
    public ResponseEntity delete(@RequestParam("id") long id)
    {
        validateDelete(id);
        service.delete(id);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("all")
    @JsonView(RestView.Get.class)
    public List<E> all()
    {
        return service.all();
    }

    @PostMapping("mod")
    @JsonView(RestView.Modify.class)
    public E modify(@RequestParam("id") long id, @RequestBody E toModify)
    {
        toModify.setId(id);
        validateModify(toModify);
        return service.modify(toModify);
    }
}
