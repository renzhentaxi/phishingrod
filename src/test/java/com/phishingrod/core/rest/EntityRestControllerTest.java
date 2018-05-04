package com.phishingrod.core.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class EntityRestControllerTest
{
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

    private String url;
    private String instanceUrl;

    public EntityRestControllerTest(String url)
    {
        this.url = url;
        this.instanceUrl = url + "/{0}";
    }

    public ResultActions restAdd(ObjectNode addJson) throws Exception
    {
        MockHttpServletRequestBuilder postRequest = post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(addJson));

        return mockMvc.perform(postRequest);
    }

    public ResultActions restAdd(ObjectNode addJson, HttpStatus status) throws Exception
    {
        return restAdd(addJson).andExpect(status().is(status.value()));
    }

    public ResultActions restGet(int id) throws Exception
    {
        return mockMvc.perform(get(instanceUrl, id));
    }

    public ResultActions restGet(int id, HttpStatus status) throws Exception
    {
        return restGet(id).andExpect(status().is(status.value()));
    }

    public ResultActions restModify(int id, ObjectNode modifyJson) throws Exception
    {
        MockHttpServletRequestBuilder putRequest = put(instanceUrl, id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(modifyJson));

        return mockMvc.perform(putRequest);
    }

    public ResultActions restModify(int id, ObjectNode modifyJson, HttpStatus status) throws Exception
    {
        return restModify(id, modifyJson).andExpect(status().is(status.value()));
    }

    public ResultActions restDelete(int id) throws Exception
    {
        return mockMvc.perform(delete(instanceUrl, id));
    }

    public ResultActions restDelete(int id, HttpStatus status) throws Exception
    {
        return restDelete(id).andExpect(status().is(status.value()));
    }

    public static ResultActions expectUnknownIdException(int id, String idType, ResultActions actions) throws Exception
    {
        return actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error_type").value("unknown_id"))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.id_type").value(idType));
    }

    public int extractId(MvcResult result) throws Exception
    {
        return objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asInt();
    }

    //common test
    @Test
    public void addDeleteModify_when_idDoesNotExist_shouldThrow_UnknownIdException() throws Exception
    {
        int idImpossible = -1;
        expectUnknownIdException(idImpossible, "id", restGet(idImpossible));
        expectUnknownIdException(idImpossible, "id", restDelete(idImpossible));
        expectUnknownIdException(idImpossible, "id", restModify(idImpossible, objectMapper.createObjectNode()));
    }
}
