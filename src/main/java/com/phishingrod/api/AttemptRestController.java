package com.phishingrod.api;

import com.phishingrod.core.domain.Attempt;
import com.phishingrod.core.service.CrudServices.AttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/api/attempt")
public class AttemptRestController
{
    private AttemptService service;

    @Autowired
    public AttemptRestController(AttemptService service)
    {
        this.service = service;
    }

    @GetMapping(path = "{id}/open")
    public void open(@PathVariable("id") long id)
    {
        service.open(id);
        System.out.println("Someone has opened the email");
    }

    @GetMapping(path = "{id}/track")
    public ModelAndView click(@PathVariable("id") long id)
    {
        service.track(id);
        return new ModelAndView("trickHtml");
    }

    @GetMapping(path = "{id}/execute")
    public void execute(@PathVariable("id") long id)
    {
        service.execute(id);
    }

    @PostMapping("schedule")
    public Attempt schedule(@RequestBody Attempt toAdd)
    {
        return service.add(toAdd);
    }

    @GetMapping
    public List<Attempt> all()
    {
        return service.all();
    }

    @GetMapping("/{id}")
    public Attempt get(@PathVariable("id") long id)
    {
        return service.get(id);
    }
}
