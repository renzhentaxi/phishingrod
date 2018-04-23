package com.phishingrod.emailTemplate;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.phishingrod.api.RestView;
import com.phishingrod.domain.parameters.Parameter;
import com.phishingrod.spoofTarget.SpoofTarget;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class EmailTemplateSerializer extends JsonSerializer<EmailTemplate>
{
    @Override
    public void serialize(EmailTemplate value, JsonGenerator gen, SerializerProvider serializers) throws IOException
    {
        gen.writeStartObject();
        Class<?> view = serializers.getActiveView();
        if (RestView.Add.class.isAssignableFrom(serializers.getActiveView()))
        {
            gen.writeNumberField("id", value.getId());
            gen.writeStringField("createdAt", value.getCreatedAt().toString());
            gen.writeStringField("lastModified", value.getLastModified().toString());

        }
        if (RestView.Get.class.isAssignableFrom(serializers.getActiveView()))
        {
            gen.writeStringField("name", value.getName());
            gen.writeStringField("sourceHtml", value.getSourceHtml());
            gen.writeStringField("originalHtml", value.getOriginalHtml());
            gen.writeArrayFieldStart("spoofTargets");
            for (SpoofTarget target : value.getSpoofTargets())
            {
                gen.writeStartObject();
                gen.writeNumberField("id", target.getId());
                gen.writeStringField("emailAddress", target.getEmailAddress());
                gen.writeEndObject();
            }
            gen.writeEndArray();

            gen.writeArrayFieldStart("parameters");
            for (Parameter parameter : value.getParameters())
            {
                gen.writeStartObject();
                gen.writeStringField("sourceType", parameter.getSourceType().toString());
                gen.writeStringField("name", parameter.getName());
                gen.writeEndObject();
            }
            gen.writeEndArray();
        }

        gen.writeEndObject();
    }
}
