import Entities.Data.UserData;
import Storage.base.Accessors.User.UserAccessor;
import Storage.base.Util.StoredSqlLocator;
import org.jdbi.v3.core.Jdbi;

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

    public void get_userExist_userIsReturned()
    {
        String rootPath = System.getProperty("user.dir") + "/src/scripts/sql";
        String parentPath = "sqlite";
        StoredSqlLocator sqlLocator = new StoredSqlLocator(rootPath, parentPath);

        sqlLocator.register("Setup", "Setup");
        sqlLocator.register("getUser", "User/getUser");
        Jdbi jdbi = Jdbi.create("jdbc:sqlite::memory:");
        jdbi.withHandle(handle -> handle.createScript(sqlLocator.locate("Setup")).execute());


        UserAccessor ua = new UserAccessor(jdbi, null, sqlLocator);

        ua.add(new UserData("firstName", "lastName", "nickName", "emailAddress"));
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
