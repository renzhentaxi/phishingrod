package Storage.Sqlite;

import Storage.base.DatabaseModule;
import Storage.base.StorageManager;
import Storage.base.Util.DataBaseUrl;
import dagger.BindsInstance;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {DatabaseModule.class, SqliteModule.class, SqliteSqlLocatorModule.class})
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
