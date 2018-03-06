import Entities.Users.IUserEntity;
import Entities.Users.User;
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

//        User userData = new User("taxi", "boi", "245", "taxi@yahoo.com");
        IUserEntity user = ua2.getByEmail("gmail.com");
        user.setLastName("lastName");
        ua2.update(user);

        User userData = new User("abc", "fda", "342", "taxi@fajs.com");
        IUserEntity user2 = ua2.add(userData);

//        Sender senderData = new Sender(user, "password", "gmail");
//
//        SenderEntity sender = sa.getByEmail("gmail.com");
//        sender.setPassword("520");
//        sender.getUser().setEmailAddress("gmail.com");
//        sa.update(sender);
//        System.out.println(sender.getUser().getEmailAddress());
    }
}
