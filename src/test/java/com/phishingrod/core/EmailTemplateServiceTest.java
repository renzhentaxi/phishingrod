package com.phishingrod.core;

import com.phishingrod.core.domain.EmailTemplate;
import com.phishingrod.core.domain.SpoofTarget;
import com.phishingrod.core.domain.parameters.Parameter;
import com.phishingrod.core.domain.parameters.ParameterSourceType;
import com.phishingrod.core.exceptions.DuplicateIdValidationException;
import com.phishingrod.core.service.CrudServices.EmailTemplateService;
import com.phishingrod.core.service.CrudServices.PhishingTargetService;
import com.phishingrod.core.service.CrudServices.SpoofTargetService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.phishingrod.core.UniqueEntityProvider.makeUnique;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class EmailTemplateServiceTest
{
    public final static String TEST_CHANGED_TEMPLATE_NAME = "changedTestName";
    public static final String TEST_HTML = "<body>TEST_HTML</body>";
    public static final String TEST_PARAMETER_NAME = "paramName";

    public final static String TEST_TEMPLATE_NAME = "testName";
    @Autowired
    private EmailTemplateService service;
    @Autowired
    private SpoofTargetService spoofTargetService;
    @Autowired
    private PhishingTargetService phishingTargetService;

    private EmailTemplate createAndAdd(String name, String html)
    {
        return service.add(new EmailTemplate(name, html));
    }

    @Test
    public void add_GetById()
    {
        String templateName = makeUnique(TEST_TEMPLATE_NAME);

        long id = createAndAdd(templateName, TEST_HTML).getId();

        EmailTemplate actualTemplate = service.get(id);

        assertThat(actualTemplate.getName()).isEqualTo(templateName);
        assertThat(actualTemplate.getHtml()).isEqualTo(TEST_HTML);
    }

    @Test
    public void add_GetByName()
    {
        String templateName = makeUnique(TEST_TEMPLATE_NAME);

        createAndAdd(templateName, TEST_HTML);

        assertThat(service.exist(templateName)).isTrue();
        assertThat(service.get(templateName).getHtml()).isEqualTo(TEST_HTML);
    }

    @Test
    public void add_NameConflict()
    {
        String templateName = makeUnique(TEST_TEMPLATE_NAME);
        createAndAdd(templateName, TEST_HTML);
        assertThatExceptionOfType(DuplicateIdValidationException.class).isThrownBy(() -> createAndAdd(templateName, TEST_HTML));
    }

    @Test
    public void add_withParameters()
    {
        String templateName = makeUnique(TEST_TEMPLATE_NAME);
        EmailTemplate emailTemplate = new EmailTemplate(templateName, TEST_HTML);

        Parameter parameter = new Parameter(ParameterSourceType.spoofTarget, TEST_PARAMETER_NAME);
        emailTemplate.addParameter(parameter);
        service.add(emailTemplate);

        EmailTemplate actual = service.get(templateName);
        assertThat(actual.getParameters()).contains(parameter);
    }

    @Test
    public void modify_addParameter()
    {
        String templateName = makeUnique(TEST_TEMPLATE_NAME);
        EmailTemplate emailTemplate = new EmailTemplate(templateName, TEST_HTML);
        service.add(emailTemplate);

        EmailTemplate returned = service.get(templateName);
        returned.addParameter(ParameterSourceType.spoofTarget, TEST_PARAMETER_NAME);
        service.modify(returned);

        returned = service.get(templateName);
        assertThat(returned.hasParameter(ParameterSourceType.spoofTarget, TEST_PARAMETER_NAME)).isTrue();
    }

    @Test
    public void modify_deleteParameter()
    {
        String templateName = makeUnique(TEST_TEMPLATE_NAME);
        EmailTemplate emailTemplate = new EmailTemplate(templateName, TEST_HTML);
        emailTemplate.addParameter(ParameterSourceType.spoofTarget, TEST_PARAMETER_NAME);
        service.add(emailTemplate);

        EmailTemplate returned = service.get(templateName);
        returned.deleteParameter(ParameterSourceType.spoofTarget, TEST_PARAMETER_NAME);
        service.modify(returned);

        returned = service.get(templateName);
        assertThat(returned.hasParameter(ParameterSourceType.spoofTarget, TEST_PARAMETER_NAME)).isFalse();
    }


    private EmailTemplate makeSimpleUniqueEmailTemplate()
    {
        return new EmailTemplate(makeUnique(TEST_TEMPLATE_NAME), TEST_HTML);
    }

    private SpoofTarget makeSimpleUniqueSpoofTarget()
    {
        return new SpoofTarget(makeUnique(TestUtil.TEST_EMAIL_ADDRESS));
    }

    private EmailTemplate makeSimpleUniqueEmailTemplateAndAdd()
    {
        return service.add(makeSimpleUniqueEmailTemplate());
    }
}
