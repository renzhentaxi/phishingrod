import Entities.Users.User;
import Entities.Users.UserEntity;
import Storage.base.Accessors.Exceptions.EntityDoesNotExistException;
import Storage.base.Accessors.Exceptions.UserWithEmailAlreadyExistException;
import Storage.base.Accessors.User.UserAccessor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * missing tests:
 * <p>
 * check that if exception occurs, no change is done to the server
 * updateUser_userDoesNotExist_databaseIsNotModified
 * check that returned data is accurate
 * get_userExist_returnedUserIsAccurate ? maybe this belongs with user??
 */
public class UserAccessorTest
{
    private static UserAccessor getDefaultUserAccessor()
    {
        return new UserAccessor(TestHelper.getSimpleJdbi(), TestHelper.getSimpleSqlLocator());
    }

    @Test
    void get_userExist_userIsReturned()
    {
        //arrange
        UserAccessor ua = getDefaultUserAccessor();

        //test data
        User expectedUser = new User("firstName", "lastName", "nickName", "address");
        int userId = ua.add(expectedUser).getId();

        UserEntity actualUser = ua.get(userId);

        //assert
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void get_userDoesNotExist_exceptionIsThrown()
    {
        //arrange
        UserAccessor ua = getDefaultUserAccessor();

        int invalidUserId = 0;

        //assert
        assertThrows(EntityDoesNotExistException.class, () -> ua.get(invalidUserId));
    }

    @Test
    void getByEmail_userExist_userIsReturned()
    {
        //arrange
        UserAccessor ua = getDefaultUserAccessor();

        //test data
        String expectedEmailAddress = "address";
        User expectedUser = new User("firstName", "lastName", "nickName", expectedEmailAddress);

        //act
        ua.add(expectedUser);

        UserEntity actualUser = ua.getByEmail(expectedEmailAddress);

        //assert
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void getByEmail_userDoesNotExist_exceptionIsThrown()
    {
        //arrange
        UserAccessor ua = getDefaultUserAccessor();

        String invalidUserEmail = "address";

        //act & assert
        assertThrows(EntityDoesNotExistException.class, () -> ua.getByEmail(invalidUserEmail));
    }

    @Test
    void addUser_userWithSameEmailDoesNotExist_userIsReturned()
    {
        //arrange
        UserAccessor ua = getDefaultUserAccessor();
        User expectedUserData = new User("firstName", "lastName", "nickName", "emailAddress");

        //act
        UserEntity returnedUser = ua.add(expectedUserData);

        //assert
        assertEquals(expectedUserData, returnedUser);
    }

    @Test
    void addUser_userWithSameEmailExist_exceptionIsThrown()
    {
        //arrange
        UserAccessor ua = getDefaultUserAccessor();
        User expectedUserData = new User("firstName", "lastName", "nickName", "emailAddress");
        ua.add(expectedUserData);

        //act assert
        assertThrows(UserWithEmailAlreadyExistException.class, () -> ua.add(expectedUserData));
    }

    @Test
    void updateUser_userWithSameIdExist_databaseIsUpdated()
    {
        //arrange
        UserAccessor ua = getDefaultUserAccessor();
        //create and add user to database
        User originalUserData = new User("firstName", "lastName", "nickName", "emailAddress");
        UserEntity user = ua.add(originalUserData);

        //modify last name and update
        user.setLastName("taxi");
        ua.update(user);

        //retrieve updated user
        UserEntity actualUser = ua.get(user.getId());

        assertEquals(user, actualUser);
    }

    @Test
    void updateUser_newEmailMatchesWithAnotherUserEmail_databaseIsUpdated()
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
        UserEntity user1 = ua.getByEmail(email1);
        user1.setEmailAddress(email2);

        //expect error
        assertThrows(UserWithEmailAlreadyExistException.class, () -> ua.update(user1));
    }

    @Test
    void updateUser_userDoesNotExist_exceptionIsThrown()
    {
        //arrange
        UserAccessor ua = getDefaultUserAccessor();
        UserEntity fakeUser = new UserEntity(0, "l", "d", "ds", "ds");
        assertThrows(EntityDoesNotExistException.class, () -> ua.update(fakeUser));

    }

}
