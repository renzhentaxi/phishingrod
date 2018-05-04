package com.phishingrod.api.base;

import com.phishingrod.core.domain.base.BasicEntity;
import com.phishingrod.core.repository.BasicEntityRepository;
import com.phishingrod.core.service.base.BasicEntityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
public abstract class EntityRestController<E extends BasicEntity, R extends BasicEntityRepository<E>, S extends BasicEntityService<E, R>>
{
    protected S service;

    public EntityRestController(S service)
    {
        this.service = service;
    }

    @PostMapping
    public E add(@RequestBody E toAdd)
    {
        return service.add(toAdd);
    }

    @GetMapping("/{id}")
    public E get(@PathVariable("id") long id)
    {
        return service.get(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") long id)
    {
        service.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    public List<E> all()
    {
        return service.all();
    }

    @PutMapping("/{id}")
    public E modify(@PathVariable("id") long id, @RequestBody E toModify)
    {
        toModify.setId(id);
        return service.modify(toModify);
    }
}
