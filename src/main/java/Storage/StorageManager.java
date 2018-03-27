package Storage;
import Storage.Sqlite.DaggerSqliteIStorageManager;
import Storage.base.IStorageManager;

public class StorageManager
{
    public static IStorageManager newInstance(String databaseLocation)
    {
        return DaggerSqliteIStorageManager.builder().databaseUrl(databaseLocation).build();
    }
}
