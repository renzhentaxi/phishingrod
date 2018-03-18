package Storage;

import Entities.Senders.ISender;
import Entities.Senders.ISenderEntity;
import Entities.Senders.Sender;
import Entities.Senders.SenderEntity;
import Entities.SessionTypes.SessionType;
import Entities.Users.User;
import Storage.base.Accessors.Exceptions.EntityAlreadyExistException;
import Storage.base.Accessors.Exceptions.EntityDoesNotExistException;
import Storage.base.Accessors.Exceptions.EntityUpdateException;
import Storage.base.Accessors.Sender.SenderAccessor;
import Storage.base.Accessors.SessionType.ISessionTypeAccessor;
import Storage.base.Accessors.User.IUserAccessor;
import Storage.base.Accessors.User.UserAccessor;
import Storage.base.Mappers.SenderMapper;
import Storage.base.Mappers.UserMapper;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * although we can mock both user/sessionType accessors, I decided not to because it brings up several complexities.
 * e.g. even if we mock method calls of user/sessionType accessors, we will not be able to determine whether user is updated
 * or not.
 */
public class SenderAccessorTest
{
    //default values for properties
    private static final String SESSIONTYPE_NAME = "sessionTypeName";
    private static final String EMAIL_ADDRESS = "emailAddress@email.com";

    //setup
    private static Jdbi jdbi;

    private static SenderAccessor getDefaultSenderAccessor()
    {
        //can't mock userAccessor because we need to actually add a user in the database due to foreign key restrictions
        ISessionTypeAccessor sessionTypeAccessor = DefaultAccessors.getDefaultSessionTypeAccessor(jdbi);
        sessionTypeAccessor.add(new SessionType(SESSIONTYPE_NAME, "host", 1, true, true));

        IUserAccessor userAccessor = DefaultAccessors.getDefaultUserAccessor(jdbi);


        return DefaultAccessors.getDefaultSenderAccessor(jdbi, userAccessor, sessionTypeAccessor);
    }

    private static ISender newTestSender()
    {
        return newTestSender(SESSIONTYPE_NAME, "");
    }

    private static ISender newTestSender(String prefix)
    {
        return newTestSender(SESSIONTYPE_NAME, prefix);
    }

    private static ISender newTestSenderWithNoPrefix(String sessionTypeName)
    {
        return newTestSender(sessionTypeName, "");
    }

    private static ISender newTestSender(String sessionTypeName, String prefix)
    {
        return new Sender(sessionTypeName, prefix + "Password", prefix + "FirstName", prefix + "LastName", prefix + "NickName", prefix + "emailAddress@email.com");
    }


    @BeforeEach
    void setup()
    {
        jdbi = AccessorTestHelper.newJdbi(new UserMapper(), new SenderMapper());
        AccessorTestHelper.setupDatabase(jdbi);
    }

    @AfterEach
    void tearDown()
    {
        AccessorTestHelper.clearDatabase(jdbi);
    }

    //tests
    @Test
    void add_senderAlreadyExist_throwsEntityAlreadyExistException()
    {
        SenderAccessor senderAccessor = getDefaultSenderAccessor();
        ISender sender = newTestSender();
        senderAccessor.add(sender);

        Throwable expectedException = catchThrowable(() -> senderAccessor.add(sender));

        assertThat(expectedException).isInstanceOf(EntityAlreadyExistException.class);
    }

    @Test
    void add_userAlreadyExist_throwsEntityAlreadyExistException()
    {

        SenderAccessor senderAccessor = getDefaultSenderAccessor();
        UserAccessor userAccessor = DefaultAccessors.getDefaultUserAccessor(jdbi);

        User user = new User("firstName", "lastName", "nickName", EMAIL_ADDRESS);
        Sender sender = new Sender(SESSIONTYPE_NAME, "password", "firstName", "lastName", "nickName", EMAIL_ADDRESS);

        userAccessor.add(user);

        Throwable expectedException = catchThrowable(() -> senderAccessor.add(sender));

        assertThat(expectedException).isInstanceOf(EntityAlreadyExistException.class);
    }

    @Test
    void add_sessionTypeDoesNotExist_throwsEntityDoesNotExistException()
    {
        SenderAccessor senderAccessor = getDefaultSenderAccessor();
        ISender senderWithNonExistentSessionType = newTestSenderWithNoPrefix("nonExistentSessionTypeName");

        Throwable expectedException = catchThrowable(() -> senderAccessor.add(senderWithNonExistentSessionType));

        assertThat(expectedException).isInstanceOf(EntityDoesNotExistException.class).hasMessageContaining("No sessionType");
    }

    @Test
    void get_senderExist_returnsSender()
    {
        SenderAccessor senderAccessor = getDefaultSenderAccessor();
        ISender sender = newTestSender();
        ISenderEntity expectedSender = senderAccessor.add(sender);

        ISenderEntity actualSender = senderAccessor.get(expectedSender.getId());

        assertThat(actualSender).isEqualTo(expectedSender);
    }

    @Test
    void getByEmail_senderExist_returnsSender()
    {
        SenderAccessor senderAccessor = getDefaultSenderAccessor();
        ISender sender = newTestSender();
        ISenderEntity expectedSender = senderAccessor.add(sender);

        ISenderEntity actualSender = senderAccessor.getByEmail(expectedSender.getEmailAddress());

        assertThat(actualSender).isEqualTo(expectedSender);
    }

    @Test
    void get_senderDoesNotExist_throwsEntityDoesNotExistException()
    {
        SenderAccessor senderAccessor = getDefaultSenderAccessor();
        int idOfNonexistentSender = 0;

        Throwable actualException = catchThrowable(() -> senderAccessor.get(idOfNonexistentSender));

        assertThat(actualException).isInstanceOf(EntityDoesNotExistException.class);
    }

    @Test
    void getByEmail_senderDoesNotExist_throwsException()
    {
        SenderAccessor senderAccessor = getDefaultSenderAccessor();
        String emailOfNonexistentSender = "email@email.com";

        Throwable actualException = catchThrowable(() -> senderAccessor.getByEmail(emailOfNonexistentSender));

        assertThat(actualException).isInstanceOf(EntityDoesNotExistException.class);
    }

    @Test
    void update_SenderDoesNotExist_throwsEntityDoesNotExistException()
    {
        SenderAccessor senderAccessor = getDefaultSenderAccessor();
        ISenderEntity nonExistentSenderEntity = new SenderEntity(0, newTestSender("nonExistent"));

        Throwable actualException = catchThrowable(() -> senderAccessor.update(nonExistentSenderEntity));

        assertThat(actualException).isInstanceOf(EntityDoesNotExistException.class);
    }

    @Test
    void update_senderExist_updatesSender()
    {
        SenderAccessor senderAccessor = getDefaultSenderAccessor();
        ISender sender = newTestSender();
        ISenderEntity addedEntity = senderAccessor.add(sender);

        ISenderEntity modifiedEntity = new SenderEntity(addedEntity.getId(), newTestSender("modified"));
        senderAccessor.update(modifiedEntity);
        ISenderEntity actualEntity = senderAccessor.getByEmail(modifiedEntity.getEmailAddress());

        assertThat(actualEntity).isEqualTo(modifiedEntity);
    }

    @Test
    void update_anotherSenderWithSameEmailExist_throwsEntityUpdateException()
    {
        SenderAccessor senderAccessor = getDefaultSenderAccessor();
        ISender sender1 = newTestSender("1");
        ISender sender2 = newTestSender("2");

        ISenderEntity senderEntity1 = senderAccessor.add(sender1);
        ISenderEntity senderEntity2 = senderAccessor.add(sender2);


        senderEntity1.setEmailAddress(senderEntity2.getEmailAddress());
        Throwable actualException = catchThrowable(() -> senderAccessor.update(senderEntity1));

        assertThat(actualException).isInstanceOf(EntityUpdateException.class);
    }

    @Test
    void update_newSessionTypeNameDoesNotExist_throwsEntityDoesNotExistException()
    {
        SenderAccessor senderAccessor = getDefaultSenderAccessor();
        ISender sender = newTestSender();
        ISenderEntity senderEntity = senderAccessor.add(sender);

        senderEntity.setSessionTypeName("NonExistentSessionTypeName");

        Throwable actualException = catchThrowable(() -> senderAccessor.update(senderEntity));

        assertThat(actualException).isInstanceOf(EntityDoesNotExistException.class);
    }

    @Test
    void exist_senderDoesNotExist_returnsFalse()
    {
        SenderAccessor accessor = getDefaultSenderAccessor();

        boolean exist = accessor.exist(1);

        assertThat(exist).isEqualTo(false);
    }

    @Test
    void exist_senderExist_returnsTrue()
    {
        SenderAccessor accessor = getDefaultSenderAccessor();
        ISender sender = newTestSender();
        ISenderEntity senderEntity = accessor.add(sender);

        boolean exist = accessor.exist(senderEntity.getId());

        assertThat(exist).isEqualTo(true);
    }

    @Test
    void existByEmail_senderDoesNotExist_returnsFalse()
    {
        SenderAccessor accessor = getDefaultSenderAccessor();

        boolean exist = accessor.exist("name");

        assertThat(exist).isEqualTo(false);
    }

    @Test
    void existByEmail_senderExist_returnsTrue()
    {
        SenderAccessor accessor = getDefaultSenderAccessor();

        ISender sender = newTestSender();
        ISenderEntity senderEntity = accessor.add(sender);

        boolean exist = accessor.exist(senderEntity.getEmailAddress());

        assertThat(exist).isEqualTo(true);
    }
}
