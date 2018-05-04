package com.phishingrod.core;

import com.phishingrod.core.domain.EmailTemplate;
import com.phishingrod.core.domain.PhishingTarget;
import com.phishingrod.core.domain.SpoofTarget;
import com.phishingrod.core.service.AttemptGenerationService;
import com.phishingrod.core.service.CrudServices.AttemptService;
import com.phishingrod.core.service.CrudServices.EmailTemplateService;
import com.phishingrod.core.service.CrudServices.PhishingTargetService;
import com.phishingrod.core.service.CrudServices.SpoofTargetService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AttemptGenerationTest
{
    @Autowired
    private EmailTemplateService templateService;
    @Autowired
    private SpoofTargetService spoofTargetService;
    @Autowired
    private PhishingTargetService phishingTargetService;
    @Autowired
    private AttemptService attemptService;

    @Autowired
    private AttemptGenerationService generationService;

    private SpoofTarget sp_name;
    private SpoofTarget sp_name_age;
    private SpoofTarget sp_age;
    private SpoofTarget sp;

    private PhishingTarget ps_name;
    private PhishingTarget ps_name_age;
    private PhishingTarget ps_age;
    private PhishingTarget ps;

    private EmailTemplate template_name;
    private EmailTemplate template;
    private EmailTemplate template_sex;
    private EmailTemplate template_name_age;
    private EmailTemplate template_age;

    @Before
    public void common()
    {
        sp = UniqueEntityProvider.makeUniqueSpoofTarget();
        sp_name = UniqueEntityProvider.makeUniqueSpoofTarget("name", "taxi");
        sp_age = UniqueEntityProvider.makeUniqueSpoofTarget("age", "2");
        sp_name_age = UniqueEntityProvider.makeUniqueSpoofTarget("name", "taxi2", "age", "2");

        spoofTargetService.add(sp);
        spoofTargetService.add(sp_name);
        spoofTargetService.add(sp_age);
        spoofTargetService.add(sp_name_age);

        ps = UniqueEntityProvider.makeUniquePhishingTarget();
        ps_name = UniqueEntityProvider.makeUniquePhishingTarget("name", "taxi");
        ps_age = UniqueEntityProvider.makeUniquePhishingTarget("age", "2");
        ps_name_age = UniqueEntityProvider.makeUniquePhishingTarget("name", "taxi2", "age", "2");


        phishingTargetService.add(ps);
        phishingTargetService.add(ps_name);
        phishingTargetService.add(ps_age);
        phishingTargetService.add(ps_name_age);

        template = UniqueEntityProvider.makeUniqueTemplate();
        template_name = UniqueEntityProvider.makeUniqueTemplate("s.name", "p.name");
        template_sex = UniqueEntityProvider.makeUniqueTemplate("s.sex", "p.sex");
        template_age = UniqueEntityProvider.makeUniqueTemplate("s.age", "p.age");
        template_name_age = UniqueEntityProvider.makeUniqueTemplate("s.age", "s.name", "p.age", "p.name");


        templateService.add(template);
        templateService.add(template_name);
        templateService.add(template_sex);
        templateService.add(template_name_age);
        templateService.add(template_age);
    }

    @After
    public void clear()
    {
        phishingTargetService.delete(ps.getId());
        phishingTargetService.delete(ps_age.getId());
        phishingTargetService.delete(ps_name.getId());
        phishingTargetService.delete(ps_name_age.getId());

        spoofTargetService.delete(sp.getId());
        spoofTargetService.delete(sp_age.getId());
        spoofTargetService.delete(sp_name.getId());
        spoofTargetService.delete(sp_name_age.getId());

        templateService.delete(template_sex.getId());
        templateService.delete(template_age.getId());
        templateService.delete(template_name.getId());
        templateService.delete(template_name_age.getId());
        templateService.delete(template.getId());
    }

    @Test
    public void getSuitableSpoofTargets()
    {
        assertThat(generationService.retrieveCompatibleSpoofTargetsFor(template_sex)).isEmpty();
        assertThat(generationService.retrieveCompatibleSpoofTargetsFor(template_age)).containsExactlyInAnyOrder(sp_name_age, sp_age);
        assertThat(generationService.retrieveCompatibleSpoofTargetsFor(template_name)).containsExactlyInAnyOrder(sp_name, sp_name_age);
        assertThat(generationService.retrieveCompatibleSpoofTargetsFor(template_name_age)).containsExactlyInAnyOrder(sp_name_age);
        assertThat(generationService.retrieveCompatibleSpoofTargetsFor(template)).containsExactlyInAnyOrder(sp, sp_age, sp_name, sp_name_age);
    }

    @Test
    public void getSuitablePhishingTargets()
    {
        assertThat(generationService.retrieveCompatiblePhishingTargetFor(template)).containsExactlyInAnyOrder(ps, ps_name_age, ps_name, ps_age);
        assertThat(generationService.retrieveCompatiblePhishingTargetFor(template_sex)).isEmpty();
        assertThat(generationService.retrieveCompatiblePhishingTargetFor(template_age)).containsExactlyInAnyOrder(ps_age, ps_name_age);
        assertThat(generationService.retrieveCompatiblePhishingTargetFor(template_name)).containsExactlyInAnyOrder(ps_name, ps_name_age);
        assertThat(generationService.retrieveCompatiblePhishingTargetFor(template_name_age)).containsExactlyInAnyOrder(ps_name_age);
    }

    @Test
    public void getSuitableEmailTemplates()
    {
        assertThat(generationService.retrieveCompatibleEmailTemplateFor(ps)).containsExactlyInAnyOrder(template);
        assertThat(generationService.retrieveCompatibleEmailTemplateFor(ps_age)).containsExactlyInAnyOrder(template, template_age);
        assertThat(generationService.retrieveCompatibleEmailTemplateFor(ps_name)).containsExactlyInAnyOrder(template, template_name);
        assertThat(generationService.retrieveCompatibleEmailTemplateFor(ps_name_age)).containsExactlyInAnyOrder(template, template_age, template_name, template_name_age);

        assertThat(generationService.retrieveCompatibleEmailTemplateFor(sp)).containsExactlyInAnyOrder(template);
        assertThat(generationService.retrieveCompatibleEmailTemplateFor(sp_age)).containsExactlyInAnyOrder(template, template_age);
        assertThat(generationService.retrieveCompatibleEmailTemplateFor(sp_name)).containsExactlyInAnyOrder(template, template_name);
        assertThat(generationService.retrieveCompatibleEmailTemplateFor(sp_name_age)).containsExactlyInAnyOrder(template, template_age, template_name, template_name_age);
    }
}
