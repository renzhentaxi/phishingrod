package com.phishingrod.core;

import com.phishingrod.core.domain.SpoofTarget;
import com.phishingrod.core.exceptions.DuplicateIdValidationException;
import com.phishingrod.core.service.CrudServices.SpoofTargetService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Map;

import static com.phishingrod.core.TestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class SpoofTargetServiceTest
{
    @Autowired
    private SpoofTargetService service;

    private SpoofTarget createAndAdd(String emailAddress, Map<String, String> paramMap)
    {
        return service.add(new SpoofTarget(emailAddress, paramMap));
    }

    private SpoofTarget createAndAdd(String emailAddress)
    {
        return service.add(new SpoofTarget(emailAddress));
    }

    private SpoofTarget modifyAndGet(SpoofTarget spoofTarget)
    {
        service.modify(spoofTarget);
        return service.get(spoofTarget.getId());
    }

    @Test
    public void add_emailConflict()
    {
        String email = "test@test.com";

        service.add(new SpoofTarget(email));

        assertThatExceptionOfType(DuplicateIdValidationException.class).isThrownBy(() -> service.add(new SpoofTarget(email)));
    }

    @Test
    public void add_getById()
    {
        long id = createAndAdd(TEST_EMAIL_ADDRESS, TEST_PARAMETER_MAP()).getId();

        SpoofTarget actual = service.get(id);

        assertThat(actual.getParameter(TEST_PARAM_NAME)).isEqualTo(TEST_PARAM_VALUE);
        assertThat(actual.getEmailAddress()).isEqualTo(TEST_EMAIL_ADDRESS);
    }

    @Test
    public void add_getByEmail()
    {
        createAndAdd(TEST_EMAIL_ADDRESS, TEST_PARAMETER_MAP());

        SpoofTarget actual = service.get(TEST_EMAIL_ADDRESS);

        assertThat(actual.getParameter(TEST_PARAM_NAME)).isEqualTo(TEST_PARAM_VALUE);
    }

    @Test
    public void modify_changeEmail()
    {
        SpoofTarget initial = createAndAdd(TEST_EMAIL_ADDRESS);

        initial.setEmailAddress(TEST_CHANGED_EMAIL_ADDRESS);

        SpoofTarget actual = modifyAndGet(initial);

        assertThat(actual.getEmailAddress()).isEqualTo(TEST_CHANGED_EMAIL_ADDRESS);
    }

    @Test
    public void modify_addParameter_NoInitialParameters()
    {
        SpoofTarget initial = createAndAdd(TEST_EMAIL_ADDRESS);

        initial.setParameter(TEST_PARAM_NAME, TEST_PARAM_VALUE);

        SpoofTarget actual = modifyAndGet(initial);

        assertThat(actual.getParameter(TEST_PARAM_NAME)).isEqualTo(TEST_PARAM_VALUE);
    }

    @Test
    public void modify_addParameter_WithInitialParameters()
    {
        SpoofTarget initial = createAndAdd(TEST_EMAIL_ADDRESS, TEST_PARAMETER_MAP());

        initial.setParameter(TEST_NEW_PARAM_NAME, TEST_NEW_PARAM_VALUE);
        SpoofTarget actual = modifyAndGet(initial);
        System.out.println(actual.getParameterMap());
        assertThat(actual.getParameter(TEST_PARAM_NAME)).isEqualTo(TEST_PARAM_VALUE);
        assertThat(actual.getParameter(TEST_NEW_PARAM_NAME)).isEqualTo(TEST_NEW_PARAM_VALUE);
    }

    @Test
    public void modify_changeParameterValue()
    {
        SpoofTarget initial = createAndAdd(TEST_EMAIL_ADDRESS, TEST_PARAMETER_MAP());

        initial.setParameter(TEST_PARAM_NAME, TEST_NEW_PARAM_VALUE);
        SpoofTarget actual = modifyAndGet(initial);

        assertThat(actual.getParameter(TEST_PARAM_NAME)).isEqualTo(TEST_NEW_PARAM_VALUE);

    }

    @Test
    public void modify_deleteParameter()
    {
        SpoofTarget initial = createAndAdd(TEST_EMAIL_ADDRESS, TEST_PARAMETER_MAP());

        initial.deleteParameter(TEST_PARAM_NAME);
        SpoofTarget actual = modifyAndGet(initial);

        assertThat(actual.hasParameter(TEST_PARAM_NAME)).isFalse();
    }


}
