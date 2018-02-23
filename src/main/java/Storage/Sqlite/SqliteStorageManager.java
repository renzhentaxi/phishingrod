package Storage.Sqlite;

import Storage.DataBaseUrl;
import Storage.Sqlite.SqliteModule;
import Storage.StorageManager;
import dagger.BindsInstance;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = SqliteModule.class)
public interface SqliteStorageManager extends StorageManager
{
    @Component.Builder
    interface Builder
    {
        @BindsInstance
        Builder databaseUrl(@DataBaseUrl String url);

        StorageManager build();
    }

}
