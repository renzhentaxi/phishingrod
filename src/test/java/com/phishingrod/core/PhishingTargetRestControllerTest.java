package com.phishingrod.core;


import com.phishingrod.core.domain.PhishingTarget;
import com.phishingrod.core.exceptions.DuplicateIdValidationException;
import com.phishingrod.core.repository.parameters.ParameterInstanceRepository;
import com.phishingrod.core.service.CrudServices.PhishingTargetService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class PhishingTargetRestControllerTest
{
    @Autowired
    private PhishingTargetService service;

    @Autowired
    ParameterInstanceRepository parameterInstanceRepository;

    private PhishingTarget create(String email, String[][] params)
    {
        Map<String, String> map = new HashMap<>();
        Arrays.stream(params).forEach(strings -> map.put(strings[0], strings[1]));
        return new PhishingTarget(email, map);
    }

    private PhishingTarget createAndAdd(String email, String[][] params)
    {
        return service.add(create(email, params));
    }

    private PhishingTarget createAndAdd(String email)
    {
        return service.add(new PhishingTarget(email));
    }

    private PhishingTarget modifyAndGet(PhishingTarget entity)
    {
        service.modify(entity);
        return service.get(entity.getId());
    }

    @Test
    public void addConflict()
    {
        String email = "test@test.com";

        PhishingTarget a = createAndAdd(email);

        assertThatExceptionOfType(DuplicateIdValidationException.class).isThrownBy(() -> createAndAdd(email));
    }

    @Test
    public void addGetById()
    {
        String expectedEmail = "test@test.com";
        String expectedParamName = "paramName";
        String expectedParamValue = "paramValue";

        PhishingTarget expected = createAndAdd(expectedEmail, new String[][]{{expectedParamName, expectedParamValue}});
        PhishingTarget actual = service.get(expected.getId());

        assertThat(actual.getParameter(expectedParamName)).isEqualTo(expectedParamValue);
        assertThat(actual.getEmailAddress()).isEqualTo(expectedEmail);
    }

    @Test
    public void addGetByEmail()
    {
        String expectedEmail = "test@test.com";
        String expectedParamName = "paramName";
        String expectedParamValue = "paramValue";

        createAndAdd(expectedEmail, new String[][]{{expectedParamName, expectedParamValue}});

        PhishingTarget actual = service.get(expectedEmail);

        assertThat(actual.getParameter(expectedParamName)).isEqualTo(expectedParamValue);
        assertThat(actual.getEmailAddress()).isEqualTo(expectedEmail);
    }

    @Test
    public void modify_changeEmail()
    {
        String initialEmail = "test@test.com";
        PhishingTarget initial = service.add(new PhishingTarget(initialEmail));

        String changedEmail = "changedEmail@test.com";
        initial.setEmailAddress(changedEmail);

        PhishingTarget actual = modifyAndGet(initial);

        assertThat(actual.getEmailAddress()).isEqualTo(changedEmail);
    }

    @Test
    public void modify_addParameter_NoInitialParameters()
    {
        String initialEmail = "test@test.com";
        PhishingTarget initial = createAndAdd(initialEmail);

        String newParamName = "param_Name";
        String expectedParamValue = "param_Value";
        initial.setParameter(newParamName, expectedParamValue);
        PhishingTarget actual = modifyAndGet(initial);

        assertThat(actual.getParameter(newParamName)).isEqualTo(expectedParamValue);
    }

    @Test
    public void modify_addParameter_WithInitialParameters()
    {
        String initialEmail = "test@test.com";
        String initialParamName = "initial_Param_Name";
        String initialParamValue = "initial_Param_Value";
        PhishingTarget initial = createAndAdd(initialEmail, new String[][]{{initialParamName, initialParamValue}});

        String newParamName = "paramName";
        String expectedParamValue = "paramValue";
        initial.setParameter(newParamName, expectedParamValue);
        PhishingTarget actual = modifyAndGet(initial);

        assertThat(actual.getParameter(initialParamName)).isEqualTo(initialParamValue);
        assertThat(actual.getParameter(newParamName)).isEqualTo(expectedParamValue);
    }

    @Test
    public void modify_changeParameterValue()
    {
        String initialEmail = "test@test.com";
        String paramName = "initial_Param_Name";
        String initialParamValue = "initial_Param_Value";
        PhishingTarget initial = createAndAdd(initialEmail, new String[][]{{paramName, initialParamValue}});

        String newParamValue = "new_param_value";
        initial.setParameter(paramName, newParamValue);
        PhishingTarget actual = modifyAndGet(initial);

        assertThat(actual.getParameter(paramName)).isEqualTo(newParamValue);

    }

    @Test
    public void modify_deleteParameter()
    {
        String initialEmail = "test@test.com";
        String paramName = "initial_Param_Name";
        String initialParamValue = "initial_Param_Value";
        PhishingTarget initial = createAndAdd(initialEmail, new String[][]{{paramName, initialParamValue}});

        initial.deleteParameter(paramName);
        PhishingTarget actual = modifyAndGet(initial);

        assertThat(actual.hasParameter(paramName)).isFalse();
    }

}
