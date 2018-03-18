import Entities.Users.IUserEntity;
import Entities.Users.User;
import Storage.Sqlite.DaggerSqliteStorageManager;
import Storage.base.StorageManager;

public class DatabaseDemo
{
    public static void main(String[] args) {
        String databaseLocation = System.getProperty("user.dir") + "/data/database/dev.db";

        StorageManager storageManager = DaggerSqliteStorageManager.builder().databaseUrl(databaseLocation).build();

        IUserAccessor userAccessor = storageManager.getUserAccessor();
        ISenderAccessor senderAccessor = storageManager.getSenderAccessor();

        User userData = new User("taxi", "boi", "245", "taxi@yahoo.com");
        IUserEntity user = userAccessor.getByEmail("gmail.com");
        user.setLastName("lastName");
        userAccessor.update(user);

        User userData = new User("abc", "fda", "342", "taxi@fajs.com");
        IUserEntity user2 = userAccessor.add(userData);

//        Sender senderData = new Sender(user, "password", "gmail");
//
//        SenderEntity sender = sa.getByEmail("gmail.com");
//        sender.setPassword("520");
//        sender.getUser().setEmailAddress("gmail.com");
//        sa.update(sender);
//        System.out.println(sender.getUser().getEmailAddress());
    }
}
