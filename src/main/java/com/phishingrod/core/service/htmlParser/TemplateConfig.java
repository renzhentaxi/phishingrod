package com.phishingrod.core.service.htmlParser;

public class TemplateConfig
{
    public String class_parameterNode;
    public String attribute_parameterName;
    public String attribute_parameterType;
    public static final TemplateConfig DEFAULT_CONFIG = new TemplateConfig("paramnode", "name", "source");

    public TemplateConfig(String class_parameterNode, String attribute_parameterName, String attribute_parameterType)
    {
        this.class_parameterNode = class_parameterNode;
        this.attribute_parameterName = attribute_parameterName;
        this.attribute_parameterType = attribute_parameterType;
    }
}
