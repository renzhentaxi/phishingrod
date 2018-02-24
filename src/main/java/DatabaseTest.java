import Accounts.User;
import Storage.Sqlite.DaggerSqliteStorageManager;
import Storage.base.JdbiUtil.StoredSqlLocator;
import Storage.base.StorageManager;
import Storage.base.UserAccessor;

public class DatabaseTest {
    public static void main(String[] args) {
        String databaseLocation = System.getProperty("user.dir") + "/data/database/test.db";


        StorageManager storageManager = DaggerSqliteStorageManager.builder().databaseUrl(databaseLocation).build();

        UserAccessor accessor = storageManager.getUserAccessor();
        System.out.println(accessor.get("gmail.com"));

        accessor.update(new User("taxi", "boi", "kdfjkds", "gmail.com")); // update the account with the address of gmail.com

    }
}
