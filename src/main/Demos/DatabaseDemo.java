import Entities.Senders.ISenderEntity;
import Entities.SessionTypes.ISessionTypeEntity;
import Entities.Users.IUserEntity;
import Storage.StorageManager;
import Storage.base.Accessors.Sender.ISenderAccessor;
import Storage.base.Accessors.SessionType.ISessionTypeAccessor;
import Storage.base.Accessors.User.IUserAccessor;
import Storage.base.IStorageManager;

import static Database.DatabaseLocation.databaseLocation;

public class DatabaseDemo
{
    public static void main(String[] args) {
        IStorageManager storageManager = StorageManager.newInstance(databaseLocation);

        IUserAccessor userAccessor = storageManager.getUserAccessor();
        ISessionTypeAccessor sessionTypeAccessor = storageManager.getSessionTypeAccessor();
        ISenderAccessor senderAccessor = storageManager.getSenderAccessor();

        IUserEntity userEntity = userAccessor.getByEmail("phishingrod123@gmail.com");
        ISessionTypeEntity sessionTypeEntity = sessionTypeAccessor.getByName("gmail");
        ISenderEntity senderEntity = senderAccessor.get(1);
        System.out.println("UserEntity: " + userEntity);
        System.out.println("sessionTypeEnity: " + sessionTypeEntity);
        System.out.println("senderEntity: " + senderEntity);

    }
}
