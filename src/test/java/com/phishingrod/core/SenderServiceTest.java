package com.phishingrod.core;

import com.phishingrod.core.domain.Sender;
import com.phishingrod.core.domain.SenderServer;
import com.phishingrod.core.exceptions.UnknownIdValidationException;
import com.phishingrod.core.service.CrudServices.SenderServerService;
import com.phishingrod.core.service.CrudServices.SenderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static com.phishingrod.core.UniqueEntityProvider.makeUnique;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SenderServiceTest
{
    @Autowired
    SenderService senderService;
    @Autowired
    SenderServerService senderServerService;

    private static String TEST_SENDER_NAME = "senderName";
    private static String TEST_SENDER_PASSWORD = "senderPassword";

    private static int TEST_SENDER_SERVER_PORT = 1;
    private static String TEST_SENDER_SERVER_NAME = "senderServerName";
    private static String TEST_SENDER_SERVER_HOST = "senderServerHost";

    public Sender createUniqueSender(SenderServer server)
    {
        return new Sender(makeUnique(TEST_SENDER_NAME), TEST_SENDER_PASSWORD, server);
    }

    public SenderServer createUniqueSenderServer()
    {
        return new SenderServer(makeUnique(TEST_SENDER_SERVER_NAME), TEST_SENDER_SERVER_PORT, makeUnique(TEST_SENDER_SERVER_HOST));
    }

    @Test
    public void add_unknownServer_throwsException()
    {
        SenderServer unknownServer = createUniqueSenderServer();
        Sender sender = createUniqueSender(unknownServer);

        assertThatExceptionOfType(UnknownIdValidationException.class).isThrownBy(() -> senderService.add(sender));
    }

    @Test
    public void add_knownServer()
    {
        SenderServer senderServer = senderServerService.add(createUniqueSenderServer());
        Sender sender = senderService.add(createUniqueSender(senderServer));

        Sender returned = senderService.get(sender.getName());

        assertThat(returned.getPassword()).isEqualTo(TEST_SENDER_PASSWORD);
        assertThat(returned.getServer()).isEqualTo(senderServer);
    }

    @Test
    public void modify_change_Name_Password()
    {
        SenderServer senderServer = senderServerService.add(createUniqueSenderServer());
        Sender sender = senderService.add(createUniqueSender(senderServer));

        String oldName = sender.getName();
        String newName = "newName";
        String newPassword = "newPassword";

        sender.setPassword(newPassword);
        sender.setName(newName);
        senderService.modify(sender);

        Sender returned = senderService.get(sender.getId());

        assertThat(senderService.exist(newName)).isTrue();
        assertThat(senderService.exist(oldName)).isFalse();

        assertThat(returned.getPassword()).isEqualTo(newPassword);
    }

    @Test
    public void get_sender_givenServer()
    {
        SenderServer senderServerA = senderServerService.add(createUniqueSenderServer());
        Sender senderA1 = senderService.add(createUniqueSender(senderServerA));
        Sender senderA2 = senderService.add(createUniqueSender(senderServerA));
        Sender senderA3 = senderService.add(createUniqueSender(senderServerA));

        SenderServer senderServerB = senderServerService.add(createUniqueSenderServer());
        Sender senderB1 = senderService.add(createUniqueSender(senderServerB));
        Sender senderB2 = senderService.add(createUniqueSender(senderServerB));
        Sender senderB3 = senderService.add(createUniqueSender(senderServerB));


        assertThat(senderService.get(senderServerA)).containsAll(Arrays.asList(senderA1, senderA2, senderA3));
        assertThat(senderService.get(senderServerB)).containsAll(Arrays.asList(senderB1, senderB2, senderB3));
    }
}
