package com.phishingrod.services;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;

@Service
public class JsonService
{
    public void populateMapUsingJson(Map<String, String> mapToPopulate, JsonNode json)
    {
        Iterator<Map.Entry<String, JsonNode>> iterator = json.fields();
        while (iterator.hasNext())
        {
            Map.Entry<String, JsonNode> field = iterator.next();
            mapToPopulate.put(field.getKey(), field.getValue().textValue());
        }
    }
}
