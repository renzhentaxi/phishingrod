package com.phishingrod.core.rest;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static com.phishingrod.core.UniqueEntityProvider.makeUnique;
import static com.phishingrod.core.rest.RestTestUtil.targetJson;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class TargetRestTest extends EntityRestControllerTest
{
    protected String entityType;

    public TargetRestTest(String url, String entityType)
    {
        super(url);
        this.entityType = entityType;
    }


    @Test
    public void addThenGet_shouldReturn_added() throws Exception
    {

        String emailAddress = makeUnique("test@email.com");
        String paramName = "name";
        String paramValue = "taxi";
        ObjectNode json = targetJson(emailAddress, paramName, paramValue);

        MvcResult result = restAdd(json)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.createdOn").isNotEmpty())
                .andExpect(jsonPath("$.lastModifiedOn").isNotEmpty())
                .andReturn();
        int id = extractId(result);

        restGet(id)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.createdOn").isNotEmpty())
                .andExpect(jsonPath("$.lastModifiedOn").isNotEmpty())
                .andExpect(jsonPath("$.emailAddress").value(emailAddress))
                .andExpect(jsonPath("$.parameters." + paramName).value(paramValue));
    }

    @Test
    public void addThenAddTheSameThing_shouldThrow_DuplicateIdException() throws Exception
    {

        String emailAddress = makeUnique("test@email.com");
        ObjectNode json = targetJson(emailAddress);

        restAdd(json);
        restAdd(json)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error_type").value("duplicate_id"))
                .andExpect(jsonPath("$.id").value(emailAddress))
                .andExpect(jsonPath("$.entity_type").value(entityType));
    }

    @Test
    public void addThenDelete() throws Exception
    {
        String emailAddress = makeUnique("emailAddress@gmail.com");
        ObjectNode json = targetJson(emailAddress);

        int id = extractId(restAdd(json).andReturn());

        restDelete(id).andExpect(status().isOk());

        expectUnknownIdException(id, "id", restGet(id));
    }

    @Test
    public void modify_shouldChangeEmailAndParameters() throws Exception
    {
        String emailAddress = makeUnique("emailAddress@gmail.com");
        ObjectNode addJson = targetJson(emailAddress, "toChange", "initial", "toDelete", "initial");
        int id = extractId(restAdd(addJson).andReturn());


        String newEmailAddress = makeUnique("newEmailAddress@gmail.com");
        ObjectNode modifyJson = targetJson(newEmailAddress, "toChange", "changed", "toAdd", "added");
        restModify(id, modifyJson)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.emailAddress").value(newEmailAddress))
                .andExpect(jsonPath("$.parameters.toChange").value("changed"))
                .andExpect(jsonPath("$.parameters.toDelete").doesNotExist())
                .andExpect(jsonPath("$.parameters.toAdd").value("added"));
    }
}
