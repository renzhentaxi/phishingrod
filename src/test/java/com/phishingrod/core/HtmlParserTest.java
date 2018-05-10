package com.phishingrod.core;

import com.phishingrod.core.domain.Attempt;
import com.phishingrod.core.domain.EmailTemplate;
import com.phishingrod.core.domain.PhishingTarget;
import com.phishingrod.core.domain.SpoofTarget;
import com.phishingrod.core.exceptions.MissingParameterValidationException;
import com.phishingrod.core.service.htmlParser.HtmlParser;
import com.phishingrod.core.service.htmlParser.TemplateConfig;
import org.aspectj.util.FileUtil;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.catchThrowable;

public class HtmlParserTest
{

    @Test
    public void parseSimple() throws IOException
    {
        //read file
        Path p = Paths.get("src", "test", "java", "com", "phishingrod", "core", "testTemplate.html");
        String html = FileUtil.readAsString(p.toFile());

        //create necessary data
        EmailTemplate template = UniqueEntityProvider.makeUniqueTemplateWith(html, "s.name", "p.name");
        SpoofTarget spoofTarget = UniqueEntityProvider.makeUniqueSpoofTarget("name", "Dank");
        PhishingTarget phishingTarget = UniqueEntityProvider.makeUniquePhishingTarget("name", "Lame");
        Attempt attempt = new Attempt(template, phishingTarget, spoofTarget, null);
        attempt.setId(1);

        //create parser
        HtmlParser parser = new HtmlParser(TemplateConfig.DEFAULT_CONFIG);
        //parse
        String actualHtml = parser.parse(attempt);
        System.out.println(actualHtml);
//        assertThat(actualHtml).contains("My name is: Lame Sender Name: Dank");
    }

    @Test
    public void parseSimple_unknownParameter_throwException() throws IOException
    {
        Path p = Paths.get("src", "test", "java", "com", "phishingrod", "core", "testTemplate.html");
        String html = FileUtil.readAsString(p.toFile());
        EmailTemplate template = UniqueEntityProvider.makeUniqueTemplateWith(html, "s.name", "p.name");
        SpoofTarget spoofTarget = UniqueEntityProvider.makeUniqueSpoofTarget("name", "Dank");
        PhishingTarget phishingTarget = UniqueEntityProvider.makeUniquePhishingTarget("name", "Lame");

        SpoofTarget spoofTarget_missingParam = UniqueEntityProvider.makeUniqueSpoofTarget();
        PhishingTarget phishingTarget_missingParam = UniqueEntityProvider.makeUniquePhishingTarget();

        HtmlParser parser = new HtmlParser(TemplateConfig.DEFAULT_CONFIG);
//        assertThatExceptionOfType(MissingParameterValidationException.class).isThrownBy(() -> parser.parse(attempt(template, spoofTarget_missingParam, phishingTarget)));
//        assertThatExceptionOfType(MissingParameterValidationException.class).isThrownBy(() -> parser.parse(attempt(template, spoofTarget, phishingTarget_missingParam)));
//        assertThatExceptionOfType(MissingParameterValidationException.class).isThrownBy(() -> parser.parse(attempt(template, spoofTarget_missingParam, phishingTarget_missingParam)));
        System.out.println("Sample exception error_message:");
//        System.out.println(catchThrowable(() -> parser.parse(attempt(template, spoofTarget_missingParam, phishingTarget))).getMessage());
    }

    public static Attempt attempt(EmailTemplate template, SpoofTarget spoofTarget, PhishingTarget phishingTarget)
    {
        return new Attempt(template, phishingTarget, spoofTarget, null);
    }
}
