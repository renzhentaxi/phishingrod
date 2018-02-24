package Storage.base;

import Storage.base.Accessors.UserAccessor;
import Storage.base.Daos.UserDao;
import org.jdbi.v3.core.Jdbi;

public interface StorageManager
{
    UserDao getUserDao();

    UserAccessor getUserAccessor();

    Jdbi getJdbi();
}
