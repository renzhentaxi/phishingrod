import Entities.Data.UserData;
import Entities.User;
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
        UserData expectedUser = new UserData("firstName", "lastName", "nickName", "address");
        int userId = ua.add(expectedUser).getId();

        User actualUser = ua.get(userId);

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
        UserData expectedUser = new UserData("firstName", "lastName", "nickName", expectedEmailAddress);

        //act
        ua.add(expectedUser);

        User actualUser = ua.getByEmail(expectedEmailAddress);

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
        UserData expectedUserData = new UserData("firstName", "lastName", "nickName", "emailAddress");

        //act
        User returnedUser = ua.add(expectedUserData);

        //assert
        assertEquals(expectedUserData, returnedUser);
    }

    @Test
    void addUser_userWithSameEmailExist_exceptionIsThrown()
    {
        //arrange
        UserAccessor ua = getDefaultUserAccessor();
        UserData expectedUserData = new UserData("firstName", "lastName", "nickName", "emailAddress");
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
        UserData originalUserData = new UserData("firstName", "lastName", "nickName", "emailAddress");
        User user = ua.add(originalUserData);

        //modify last name and update
        user.setLastName("taxi");
        ua.update(user);

        //retrieve updated user
        User actualUser = ua.get(user.getId());

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
        UserData originalUserData1 = new UserData("firstName", "lastName", "nickName", email1);
        UserData originalUserData2 = new UserData("firstName", "lastName", "nickName", email2);

        ua.add(originalUserData1);
        ua.add(originalUserData2);

        //get user, modify emailAddress to another user's emailAddress
        User user1 = ua.getByEmail(email1);
        user1.setEmailAddress(email2);

        //expect error
        assertThrows(UserWithEmailAlreadyExistException.class, () -> ua.update(user1));
    }

    @Test
    void updateUser_userDoesNotExist_exceptionIsThrown()
    {
        //arrange
        UserAccessor ua = getDefaultUserAccessor();
        User fakeUser = new User(0, "l", "d", "ds", "ds");
        assertThrows(EntityDoesNotExistException.class, () -> ua.update(fakeUser));

    }

}
