package Storage.base;

import org.jdbi.v3.core.Jdbi;

public interface StorageManager
{
    UserDao getUserDao();

    UserAccessor getUserAccessor();

    Jdbi getJdbi();
}
