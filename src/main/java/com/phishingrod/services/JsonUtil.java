package com.phishingrod.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;

@Service
public class JsonUtil
{
    public static void populateMapUsingJson(Map<String, String> mapToPopulate, JsonNode json)
    {
        Iterator<Map.Entry<String, JsonNode>> iterator = json.fields();
        while (iterator.hasNext())
        {
            Map.Entry<String, JsonNode> field = iterator.next();
            mapToPopulate.put(field.getKey(), field.getValue().textValue());
        }
    }

    public static void populateJsonUsingMap(ObjectNode jsonToPopulate, Map<String, String> map)
    {
        for (Map.Entry<String, String> entry : map.entrySet())
        {
            jsonToPopulate.put(entry.getKey(), entry.getValue());
        }
    }
}
