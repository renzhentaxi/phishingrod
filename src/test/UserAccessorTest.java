import Entities.Data.UserData;
import Entities.User;
import Storage.base.Accessors.User.UserAccessor;
import Storage.base.Mappers.UserMapper;
import Storage.base.Util.AlternativeSqlLocator;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    @Test
    public void get_userExist_userIsReturned()
    {
        //setting up testDatabase
        Jdbi testDatabase = TestHelper.getJdbi(new UserMapper());
        AlternativeSqlLocator sqlLocator = TestHelper.getMockSqlLocator(
                "getUser", "user/getUser",
                "addUser", "user/addUser");

        UserAccessor ua = new UserAccessor(testDatabase, null, sqlLocator);

        //test data
        UserData expectedUser = new UserData("firstName", "lastName", "nickName", "address");
        int userId = ua.add(expectedUser).getId();

        User actualUser = ua.get(userId);

        //compare data
        assertEquals(expectedUser.getFirstName(), actualUser.getFirstName());
        assertEquals(expectedUser.getLastName(), actualUser.getLastName());
        assertEquals(expectedUser.getNickName(), actualUser.getNickName());
        assertEquals(expectedUser.getEmailAddress(), actualUser.getEmailAddress());
    }

    public void get_userDoesNotExist_exceptionIsThrown()
    {
    }

    public void getByEmail_userExist_userIsReturned()
    {
    }

    public void getByEmail_userDoesNotExist_exceptionIsThrown()
    {
    }

    public void addUser_userDoesNotExist_userIsAddedToDatabase()
    {
    }

    public void addUser_userWithSameIDDoesNotExist_userIsReturned()
    {
    }

    public void addUser_userWithSameIDExist_exceptionIsThrown()
    {
    }

    public void addUser_userWithSameEmailDoesNotExist_userIsReturned()
    {
    }

    public void addUser_userWithSameEmailExist_exceptionIsThrown()
    {
    }

    public void updateUser_userWithSameIdExist_databaseIsUpdated()
    {
    }
    public void updateUser_newEmailMatchesWithAnotherUserEmail_databaseIsUpdated()
    {
    }
    public void updateUser_userWithSameUserDoesNotExist_exceptionIsThrown()
    {
    }

}
