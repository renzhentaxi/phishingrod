package Storage.Sqlite;

import Storage.DataBaseUrl;
import Storage.ConnectionProvider;
import Storage.UserAccessor;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class SqliteModule
{

    @Provides
    @Singleton
    public ConnectionProvider provideConnectionProvider(@DataBaseUrl String databaseUrl)
    {
        return new SqliteConnectionProvider(databaseUrl);
    }

    @Provides
    @Singleton
    public static UserAccessor provideUserAccessor(ConnectionProvider connectionProvider)
    {
        return new SqliteUserAccessor(connectionProvider);
    }

}
