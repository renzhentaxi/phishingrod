package Storage.base;

import Storage.base.Accessors.Sender.ISenderAccessor;
import Storage.base.Accessors.SessionType.ISessionTypeAccessor;
import Storage.base.Accessors.User.IUserAccessor;
import org.jdbi.v3.core.Jdbi;

public interface IStorageManager
{

    IUserAccessor getUserAccessor();

    ISenderAccessor getSenderAccessor();

    ISessionTypeAccessor getSessionTypeAccessor();

    Jdbi getJdbi();
}
