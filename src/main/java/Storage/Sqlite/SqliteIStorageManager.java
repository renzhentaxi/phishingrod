package Storage.Sqlite;

import Storage.base.DatabaseModule;
import Storage.base.IStorageManager;
import Storage.base.Util.DataBaseUrl;
import dagger.BindsInstance;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {DatabaseModule.class, SqliteModule.class, SqliteSqlLocatorModule.class})
public interface SqliteIStorageManager extends IStorageManager
{
    @Component.Builder
    interface Builder
    {
        @BindsInstance
        Builder databaseUrl(@DataBaseUrl String url);

        IStorageManager build();
    }


}
