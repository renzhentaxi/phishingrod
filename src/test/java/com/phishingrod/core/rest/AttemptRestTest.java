package com.phishingrod.core.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phishingrod.core.domain.Attempt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class AttemptRestTest extends EntityRestControllerTest
{
    public AttemptRestTest()
    {
        super("/api/attempt");
    }

    @Test
    public void attemptJson() throws Exception
    {
        Attempt attempt = new Attempt();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(attempt);
        System.out.println(json);
    }
}
