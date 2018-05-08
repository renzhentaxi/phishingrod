package com.phishingrod.api.Json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.phishingrod.core.domain.Sender;
import com.phishingrod.core.domain.SenderServer;
import com.phishingrod.core.service.CrudServices.SenderServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class SenderDeserializer extends JsonDeserializer<Sender>
{

    public final static String NAME_FIELD = "name";
    public final static String PASSWORD_FEILD = "password";
    public final static String SERVER_FIELD = "server";


    SenderServerService senderServerService;

    @Autowired
    public SenderDeserializer(SenderServerService serverService)
    {
        this.senderServerService = serverService;
    }

    @Override
    public Sender deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
    {
        Sender sender = new Sender();

        ObjectNode json = p.readValueAsTree();
        if (json.has(NAME_FIELD))
            sender.setName(json.get(NAME_FIELD).asText());
        if (json.has(PASSWORD_FEILD))
            sender.setPassword(json.get(PASSWORD_FEILD).asText());
        if (json.has(SERVER_FIELD))
        {
            SenderServer senderServer = senderServerService.get(json.get(SERVER_FIELD).asText());
            sender.setServer(senderServer);
        }
        return sender;
    }
}
