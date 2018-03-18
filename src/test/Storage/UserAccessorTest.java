package Storage;

import Entities.Users.IUser;
import Entities.Users.IUserEntity;
import Entities.Users.User;
import Entities.Users.UserEntity;
import Storage.base.Accessors.Exceptions.EntityAlreadyExistException;
import Storage.base.Accessors.Exceptions.EntityDoesNotExistException;
import Storage.base.Accessors.Exceptions.EntityUpdateException;
import Storage.base.Accessors.User.UserAccessor;
import Storage.base.Mappers.UserMapper;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserAccessorTest
{
    //setup
    private static Jdbi jdbi;

    private static Jdbi getJdbi()
    {
        if (jdbi == null)
        {
            jdbi = AccessorTestHelper.newJdbi(new UserMapper());
        }
        return jdbi;
    }

    private static UserAccessor getDefaultUserAccessor()
    {
        return DefaultAccessors.getDefaultUserAccessor(getJdbi());
    }

    @BeforeEach
    void setUp()
    {
        AccessorTestHelper.setupDatabase(getJdbi());
    }

    @AfterEach
    void tearDown()
    {
        AccessorTestHelper.clearDatabase(getJdbi());
    }

    //tests
    @Test
    void add_userExist_throwsEntityAlreadyExistException()
    {
        //arrange
        UserAccessor ua = getDefaultUserAccessor();
        User expectedUserData = new User("firstName", "lastName", "nickName", "emailAddress");
        ua.add(expectedUserData);

        //act assert
        assertThrows(EntityAlreadyExistException.class, () -> ua.add(expectedUserData));
    }

    @Test
    void get_userExist_returnsUser()
    {
        //arrange
        UserAccessor ua = getDefaultUserAccessor();

        //test data
        User expectedUser = new User("firstName", "lastName", "nickName", "address");

        //add user
        int userId = ua.add(expectedUser).getId();

        //get user
        IUserEntity actualUser = ua.get(userId);

        //assert
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void get_userDoesNotExist_throwsEntityDoesNotExistException()
    {
        //arrange
        UserAccessor ua = getDefaultUserAccessor();

        int invalidUserId = 0;

        //assert
        assertThrows(EntityDoesNotExistException.class, () -> ua.get(invalidUserId));
    }

    @Test
    void getByEmail_userExist_returnsUser()
    {
        //arrange
        UserAccessor ua = getDefaultUserAccessor();

        //test data
        String expectedEmailAddress = "address";
        User expectedUser = new User("firstName", "lastName", "nickName", expectedEmailAddress);

        //act
        ua.add(expectedUser);

        IUserEntity actualUser = ua.getByEmail(expectedEmailAddress);

        //assert
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void getByEmail_userDoesNotExist_throwsEntityDoesNotExistException()
    {
        //arrange
        UserAccessor ua = getDefaultUserAccessor();

        String invalidUserEmail = "address";

        //act & assert
        assertThrows(EntityDoesNotExistException.class, () -> ua.getByEmail(invalidUserEmail));
    }


    @Test
    void updateUser_userExist_updatesUser()
    {
        //arrange
        UserAccessor ua = getDefaultUserAccessor();
        //create and add user to database
        User originalUserData = new User("firstName", "lastName", "nickName", "emailAddress");
        IUserEntity user = ua.add(originalUserData);

        //modify last name and update
        user.setLastName("taxi");
        ua.update(user);

        //retrieve updated user
        IUserEntity actualUser = ua.get(user.getId());

        assertEquals(user, actualUser);
    }


    @Test
    void updateUser_userDoesNotExist_throwsEntityDoesNotExistException()
    {
        //arrange
        UserAccessor ua = getDefaultUserAccessor();
        UserEntity fakeUser = new UserEntity(0, "l", "d", "ds", "ds");
        assertThrows(EntityDoesNotExistException.class, () -> ua.update(fakeUser));

    }


    @Test
    void updateUser_newEmailMatchesWithAnotherUserEmail_throwsEntityUpdateException()
    {
        //arrange
        UserAccessor ua = getDefaultUserAccessor();
        //create and add user to database
        String email1 = "emailAddress1";
        String email2 = "emailAddress2";
        User originalUserData1 = new User("firstName", "lastName", "nickName", email1);
        User originalUserData2 = new User("firstName", "lastName", "nickName", email2);

        ua.add(originalUserData1);
        ua.add(originalUserData2);

        //get user, modify emailAddress to another user's emailAddress
        IUserEntity user1 = ua.getByEmail(email1);
        user1.setEmailAddress(email2);

        //expect error
        assertThrows(EntityUpdateException.class, () -> ua.update(user1));
    }

    @Test
    void exist_userDoesNotExist_returnsFalse()
    {
        UserAccessor accessor = getDefaultUserAccessor();

        boolean exist = accessor.exist(1);

        assertThat(exist).isEqualTo(false);
    }

    @Test
    void exist_userExist_returnsTrue()
    {
        UserAccessor accessor = getDefaultUserAccessor();
        IUser user = new User("firstName", "lastName", "nickName", "email@email.com");
        IUserEntity userEntity = accessor.add(user);

        boolean exist = accessor.exist(userEntity.getId());

        assertThat(exist).isEqualTo(true);
    }

    @Test
    void existByName_sessionTypeDoesNotExist_returnsFalse()
    {
        UserAccessor accessor = getDefaultUserAccessor();

        boolean exist = accessor.exist("email@email.com");

        assertThat(exist).isEqualTo(false);
    }

    @Test
    void existByName_sessionTypeExist_returnsTrue()
    {
        UserAccessor accessor = getDefaultUserAccessor();
        IUser user = new User("firstName", "lastName", "nickName", "email@email.com");
        IUserEntity userEntity = accessor.add(user);

        boolean exist = accessor.exist(userEntity.getEmailAddress());

        assertThat(exist).isEqualTo(true);
    }
}
