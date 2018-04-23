package com.phishingrod.emailTemplate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.phishingrod.domain.parameters.Parameter;
import com.phishingrod.domain.parameters.ParameterSourceType;
import com.phishingrod.spoofTarget.SpoofTarget;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@JsonComponent
public class EmailTemplateDeserializer extends JsonDeserializer<EmailTemplate>
{
    @Override
    public EmailTemplate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException
    {
        ObjectNode objectNode = p.getCodec().readTree(p);

        String name = readString(objectNode, "name");
        String sourceHtml = readString(objectNode, "sourceHtml");
        String originalHtml = readString(objectNode, "originalHtml");
        List<SpoofTarget> spoofTargets = readSpoofTargets(objectNode, "spoofTargets");
        List<Parameter> parameters = readParameters(objectNode, "parameters");
        EmailTemplate template = new EmailTemplate();

        template.setName(name);
        template.setSourceHtml(sourceHtml);
        template.setOriginalHtml(originalHtml);
        template.setSpoofTargets(spoofTargets);
        template.setParameters(parameters);

        return template;
    }

    private static String readString(JsonNode tree, String fieldName)
    {
        return tree.get(fieldName).textValue();
    }

    private static List<SpoofTarget> readSpoofTargets(JsonNode tree, String fieldName)
    {
        List<SpoofTarget> targets = new ArrayList<>();

        tree.withArray(fieldName)
                .forEach(jsonNode ->
                        {
                            SpoofTarget target = new SpoofTarget();
                            if (jsonNode.isInt())
                            {
                                target.setId(jsonNode.asInt());
                            } else if (jsonNode.isTextual())
                            {
                                target.setEmailAddress(jsonNode.asText());
                            } else
                                throw new RuntimeException("invalid json field");
                            targets.add(target);
                            System.out.println(jsonNode);
                        }
                );
        return targets.isEmpty() ? null : targets;
    }

    private static ParameterSourceType readSourceType(JsonNode tree, String fieldName)
    {
        String sourceString = readString(tree, fieldName);
        return ParameterSourceType.valueOf(sourceString);
    }

    private static List<Parameter> readParameters(ObjectNode tree, String fieldName)
    {
        List<Parameter> parameters = new ArrayList<>();

        tree.withArray(fieldName)
                .forEach(jsonNode ->
                        {
                            Parameter parameter = new Parameter();
                            ParameterSourceType sourceType = readSourceType(jsonNode, "source");
                            String name = readString(jsonNode, "name");
                            parameter.setSourceType(sourceType);
                            parameter.setName(name);

                            parameters.add(parameter);
                        }
                );

        return parameters.isEmpty() ? null : parameters;
    }
}
