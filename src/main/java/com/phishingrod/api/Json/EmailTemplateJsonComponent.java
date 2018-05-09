package com.phishingrod.api.Json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.phishingrod.core.domain.EmailTemplate;
import com.phishingrod.core.domain.parameters.Parameter;
import com.phishingrod.core.domain.parameters.ParameterSourceType;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@JsonComponent
public class EmailTemplateJsonComponent
{
    public final static String HTML_FIELD = "html";
    public final static String NAME_FIELD = "name";
    public final static String PARAM_FIELD = "parameters";
    public final static String PARAM_SOURCETYPE_FIELD = "source";
    public final static String PARAM_NAME_FIELD = "name";

    public static class deserializer extends JsonDeserializer<EmailTemplate>
    {
        @Override
        public EmailTemplate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
        {
            EmailTemplate template = new EmailTemplate();
            ObjectNode json = p.readValueAsTree();
            if (json.has(NAME_FIELD))
                template.setName(json.get(NAME_FIELD).asText());
            if (json.has(HTML_FIELD))
                template.setHtml(json.get(HTML_FIELD).asText());

            if (json.has(PARAM_FIELD))
            {
                Set<Parameter> parameterList = new HashSet<>();
                json.get(PARAM_FIELD).elements().forEachRemaining(jsonNode ->
                {
                    ParameterSourceType sourceType = ParameterSourceType.valueOf(jsonNode.get(PARAM_SOURCETYPE_FIELD).asText());
                    String name = jsonNode.get(PARAM_NAME_FIELD).asText();
                    parameterList.add(new Parameter(sourceType, name));
                });
                template.setParameters(parameterList);
            }
            return template;
        }
    }
}
