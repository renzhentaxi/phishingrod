import Accounts.Entities.Sender;
import Accounts.Entities.User;
import Storage.Sqlite.DaggerSqliteStorageManager;
import Storage.base.Accessors.Sender.SenderAccessor;
import Storage.base.Accessors.User.UserAccessor;
import Storage.base.StorageManager;

public class DatabaseTest {
    public static void main(String[] args) {
        String databaseLocation = System.getProperty("user.dir") + "/data/database/test.db";


        StorageManager storageManager = DaggerSqliteStorageManager.builder().databaseUrl(databaseLocation).build();

        UserAccessor ua2 = storageManager.getUserAccessor();
        SenderAccessor sa = storageManager.getSenderAccessor();

//        UserData userData = new UserData("taxi", "boi", "245", "taxi@yahoo.com");
        User user = ua2.getByEmail("gmail.com");

//        SenderData senderData = new SenderData(user, "password", "gmail");

        Sender sender = sa.getByEmail("gmail.com");
        sender.setPassword("520");
        sender.getUser().setEmailAddress("gmail.com");
        sa.update(sender);
        System.out.println(sender.getUser().getEmailAddress());
    }
}
