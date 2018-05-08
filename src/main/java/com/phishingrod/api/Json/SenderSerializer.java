package com.phishingrod.api.Json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.phishingrod.core.domain.Sender;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class SenderSerializer extends JsonSerializer<Sender>
{

    public final static String ID_FIELD = "id";
    public final static String LASTMOD_FIELD = "lastModifiedOn";
    public final static String CREATED_FIELD = "createdOn";
    public final static String NAME_FIELD = "name";
    public final static String PASSWORD_FEILD = "password";
    public final static String SERVER_FIELD = "server";

    @Override
    public void serialize(Sender value, JsonGenerator gen, SerializerProvider serializers) throws IOException
    {
        gen.writeStartObject();
        gen.writeObjectField(ID_FIELD, value.getId());
        gen.writeObjectField(CREATED_FIELD, value.getCreatedOn());
        gen.writeObjectField(LASTMOD_FIELD, value.getLastModifiedOn());
        gen.writeObjectField(NAME_FIELD, value.getName());
        gen.writeObjectField(PASSWORD_FEILD, value.getPassword());
        gen.writeObjectField(SERVER_FIELD, value.getServer().getName());
        gen.writeEndObject();
    }
}
