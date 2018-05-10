package com.phishingrod.core.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.phishingrod.core.domain.parameters.Parameter;
import com.phishingrod.core.domain.parameters.ParameterSourceType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;

import static com.phishingrod.core.UniqueEntityProvider.makeUnique;
import static com.phishingrod.core.rest.RestTestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class EmailTemplateRestTest extends EntityRestControllerTest
{
    public EmailTemplateRestTest()
    {
        super("/api/emailTemplate");
    }

    @Test
    public void addThenGet() throws Exception
    {
        String name = makeUnique("templateName");
        String html = "html_message";
        ObjectNode s_param = parameterJson(ParameterSourceType.spoofTarget, "name");
        ObjectNode p_param = parameterJson(ParameterSourceType.phishingTarget, "name");

        ObjectNode addJson = templateJson(name, html, s_param, p_param);

        MvcResult result = restAdd(addJson)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.createdOn").isNotEmpty())
                .andExpect(jsonPath("$.lastModifiedOn").isNotEmpty())
                .andReturn();
        int id = extractId(result);

        MvcResult getResult = restGet(id).andExpect(status().isOk()).andReturn();
        JsonNode responseJson = json(getResult);
        assertThat(responseJson.get("id").asInt()).isEqualTo(id);
        assertThat(responseJson.get("name").asText()).isEqualTo(name);
        assertThat(responseJson.get("sourceHtml").asText()).isEqualTo(html);


        JsonNode params = responseJson.get("parameters");

        assertThat(toParameterSet(params)).containsExactlyInAnyOrder(
                new Parameter(ParameterSourceType.spoofTarget, "name"),
                new Parameter(ParameterSourceType.phishingTarget, "name"));
    }

    @Test
    public void addThenDelete() throws Exception
    {
        String name = makeUnique("templateName");
        String html = "html_message";
        ObjectNode s_param = parameterJson(ParameterSourceType.spoofTarget, "name");
        ObjectNode p_param = parameterJson(ParameterSourceType.phishingTarget, "name");
        ObjectNode addJson = templateJson(name, html, s_param, p_param);

        int id = extractId(restAdd(addJson).andReturn());

        restDelete(id).andExpect(status().isOk()).andReturn();

        expectUnknownIdException(id, "id", restGet(id));
    }

    @Test
    public void modify_changeSourceHtml() throws Exception
    {
        String name = makeUnique("templateName");
        String html = "html_message";
        ObjectNode s_param = parameterJson(ParameterSourceType.spoofTarget, "name");
        ObjectNode p_param = parameterJson(ParameterSourceType.phishingTarget, "name");
        ObjectNode addJson = templateJson(name, html, s_param, p_param);

        int id = extractId(restAdd(addJson).andReturn());

        String newHtml = "new_html_message";
        ObjectNode modifyJson = convertToObject("sourceHtml", newHtml);

        MvcResult response = restModify(id, modifyJson).andExpect(status().isOk()).andReturn();
        JsonNode responseJson = json(response);

        assertThat(responseJson.get("id").asInt()).isEqualTo(id);
        assertThat(responseJson.get("name").asText()).isEqualTo(name);
        assertThat(responseJson.get("sourceHtml").asText()).isEqualTo(newHtml);

        JsonNode params = responseJson.get("parameters");

        assertThat(toParameterSet(params)).containsExactlyInAnyOrder(
                new Parameter(ParameterSourceType.spoofTarget, "name"),
                new Parameter(ParameterSourceType.phishingTarget, "name"));
    }


    @Test
    public void modify_changeName() throws Exception
    {
        String name = makeUnique("templateName");
        String html = "html_message";
        ObjectNode s_param = parameterJson(ParameterSourceType.spoofTarget, "name");
        ObjectNode p_param = parameterJson(ParameterSourceType.phishingTarget, "name");
        ObjectNode addJson = templateJson(name, html, s_param, p_param);

        int id = extractId(restAdd(addJson).andReturn());

        String newName = "newTemplateName";
        ObjectNode modifyJson = convertToObject("name", newName);

        MvcResult response = restModify(id, modifyJson).andExpect(status().isOk()).andReturn();
        JsonNode responseJson = json(response);

        assertThat(responseJson.get("id").asInt()).isEqualTo(id);
        assertThat(responseJson.get("name").asText()).isEqualTo(newName);
        assertThat(responseJson.get("sourceHtml").asText()).isEqualTo(html);

        JsonNode params = responseJson.get("parameters");
        assertThat(toParameterSet(params)).containsExactlyInAnyOrder(
                new Parameter(ParameterSourceType.spoofTarget, "name"),
                new Parameter(ParameterSourceType.phishingTarget, "name"));
    }

    @Test
    public void modify_changeParameters() throws Exception
    {
        String name = makeUnique("templateName");
        String html = "html_message";
        Parameter toKeep = Parameter.spoofTarget("keep");
        Parameter toDelete = Parameter.phishingTarget("delete");
        Parameter toAdd = Parameter.spoofTarget("schedule");

        ObjectNode addJson = templateJson(name, html, json(toDelete, toKeep));
        ObjectNode modifyJson = json("parameters", json(toAdd, toKeep));

        int id = RestTestUtil.extractId(restAdd(addJson, HttpStatus.OK));
        JsonNode responseJson = json(restModify(id, modifyJson, HttpStatus.OK));

        assertJson(responseJson,
                "id", id,
                "name", name,
                "sourceHtml", html);

        JsonNode params = responseJson.get("parameters");
        assertThat(toParameterSet(params))
                .containsExactlyInAnyOrder(toAdd,toKeep);
    }


}
