package com.phishingrod.core.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.phishingrod.core.TestUtil;
import com.phishingrod.core.domain.parameters.Parameter;
import com.phishingrod.core.domain.parameters.ParameterSourceType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Contains alot of util static methods to dynamically generate json instead of doing them by hand
 * also contains static assert methods that makes it easier to validate json
 */
public class RestTestUtil
{
    public static final String FIELD_PARAMETERS = "parameters";
    public static final String FILED_EMAIL = "emailAddress";
    public static final String FILED_NAME = "name";
    public static final String FILED_HTML = "sourceHtml";
    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectNode json(Object... values)
    {
        ObjectNode obj = objectMapper.createObjectNode();
        for (int i = 0; i < values.length; i += 2)
        {
            String fieldName = values[i].toString();
            Object value = values[i + 1];
            if (value instanceof JsonNode)
                obj.set(fieldName, (JsonNode) value);
            else if (value instanceof String)
                obj.put(fieldName, (String) value);
            else if (value instanceof Number)
                obj.put(fieldName, (int) value);
            else
                throw new RuntimeException("unknown type: " + fieldName + ":" + value.toString());
        }
        return obj;
    }

    public static ObjectNode targetJson(String emailAddress, Map<String, String> params)
    {
        ObjectNode map = objectMapper.valueToTree(params);
        return json(FILED_EMAIL, emailAddress, FIELD_PARAMETERS, map);
    }

    public static ObjectNode targetJson(String emailAddress, String... params)
    {
        return targetJson(emailAddress, convertToMap(params));
    }

    public static ObjectNode templateJson(String name, String html, ObjectNode... parameters)
    {
        ObjectNode json = objectMapper.createObjectNode();
        json.put(FILED_NAME, name);
        json.put(FILED_HTML, html);
        ArrayNode paramNodes = objectMapper.createArrayNode();
        paramNodes.addAll(Arrays.asList(parameters));

        json.set(FIELD_PARAMETERS, paramNodes);
        return json;
    }

    public static ObjectNode templateJson(String name, String html, JsonNode parameters)
    {
        return json(FILED_NAME, name, FILED_HTML, html, FIELD_PARAMETERS, parameters);
    }

    public static JsonNode json(MvcResult result) throws Exception
    {
        return objectMapper.readTree(result.getResponse().getContentAsString());
    }

    public static JsonNode json(ResultActions actions) throws Exception
    {
        return objectMapper.readTree(actions.andReturn().getResponse().getContentAsString());
    }

    public static Map<String, String> convertToMap(String... params)
    {
        return TestUtil.convertToMap(params);
    }

    public static ObjectNode convertToObject(String... params)
    {
        return objectMapper.valueToTree(TestUtil.convertToMap(params));
    }

    public static ObjectNode parameterJson(ParameterSourceType sourceType, String name)
    {
        return convertToObject("sourceType", sourceType.name(), "name", name);
    }

    public static ObjectNode parameterJson(Parameter p)
    {
        return parameterJson(p.getSourceType(), p.getName());
    }

    public static JsonNode json(Parameter... parameters)
    {
        ArrayNode node = objectMapper.createArrayNode();
        for (Parameter p : parameters)
            node.add(parameterJson(p));
        return node;
    }

    public static Parameter toParameter(JsonNode node)
    {
        return new Parameter(ParameterSourceType.valueOf(node.get("sourceType").asText()), node.get("name").asText());
    }

    public static Set<Parameter> toParameterSet(JsonNode node)
    {
        Set<Parameter> set = new HashSet<>();
        node.elements().forEachRemaining(jsonNode -> set.add(toParameter(jsonNode)));
        return set;
    }

    public static void assertJson(JsonNode json, Object... expected) throws Exception
    {
        String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        for (int i = 0; i < expected.length; i += 2)
        {
            String field = (String) expected[i];
            assertThat(json.has(field)).overridingErrorMessage("Expecting json to contain the field: <%s> but it doesn't.\n json: \n%s", field, jsonString).isTrue();
            Object expectedValue = expected[i + 1];
            if (expectedValue instanceof Integer)
            {
                assertThat(json.get(field).asInt()).isEqualTo(expectedValue);
            } else if (expectedValue instanceof String)
            {
                String actualValue = json.get(field).asText();
                assertThat(actualValue)
                        .overridingErrorMessage("Expecting field <%s> to contain \nthe value: <%s> \nbut actual value was: <%s>", field, expectedValue, actualValue)
                        .isEqualTo(expectedValue);
            } else
                throw new RuntimeException("Don't know how to handle!!: " + field + " : " + expectedValue);
        }
    }


    public static int extractId(ResultActions actions) throws Exception
    {
        return json(actions).get("id").asInt();
    }
}
