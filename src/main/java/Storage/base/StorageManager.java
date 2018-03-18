package Storage.base;

import Storage.base.Accessors.Sender.old.SenderAccessor;
import Storage.base.Accessors.User.UserAccessor;
import org.jdbi.v3.core.Jdbi;

public interface StorageManager
{

    UserAccessor getUserAccessor();

    SenderAccessor getSenderAccessor();
    Jdbi getJdbi();
}
