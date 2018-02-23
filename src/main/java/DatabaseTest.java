import Storage.Sqlite.DaggerSqliteStorageManager;
import Storage.StorageManager;
import Storage.UserAccessor;

public class DatabaseTest
{
    public static void main(String[] args)
    {
        String databaseLocation = System.getProperty("user.dir") + "/data/database/test.db";

        StorageManager storageManager = DaggerSqliteStorageManager.builder().databaseUrl(databaseLocation).build();

        UserAccessor accessor = storageManager.getUserAccessor();
        System.out.println(accessor.get("gmail.com"));

    }
}
