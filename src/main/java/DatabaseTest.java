import Storage.Sqlite.DaggerSqliteStorageManager;
import Storage.base.StorageManager;
import Storage.base.Daos.UserDao;

public class DatabaseTest {
    public static void main(String[] args) {
        String databaseLocation = System.getProperty("user.dir") + "/data/database/test.db";


        StorageManager storageManager = DaggerSqliteStorageManager.builder().databaseUrl(databaseLocation).build();

//        UserAccessor accessor = storageManager.getUserAccessor();
//        System.out.println(accessor.get("gmail.com"));

        UserDao dao = storageManager.getUserDao();
        System.out.println(dao.get("gmail.com"));

    }
}
