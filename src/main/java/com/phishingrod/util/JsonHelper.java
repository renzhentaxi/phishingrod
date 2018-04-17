package com.phishingrod.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonHelper
{
    public static Map<String, String> parse(String json)
    {
        Map<String, String> map = new HashMap<>();

        ObjectMapper mapper = new ObjectMapper();
        try
        {
            mapper.readTree(json).fields().forEachRemaining(stringJsonNodeEntry -> map.put(stringJsonNodeEntry.getKey(), stringJsonNodeEntry.getValue().textValue()));
            return map;
        } catch (IOException e)
        {
            throw new RuntimeException("not valid json", e);
        }
    }


}
