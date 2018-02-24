package Storage.Sqlite;

import Storage.base.DataBaseUrl;
import Storage.base.DatabaseModule;
import Storage.base.UserAccessor;
import Storage.base.UserDao;
import dagger.Module;
import dagger.Provides;
import org.jdbi.v3.core.Jdbi;
import org.sqlite.SQLiteDataSource;

import javax.inject.Singleton;
import javax.sql.DataSource;

@Module(includes = DatabaseModule.class)
public class SqliteModule
{
    @Provides
    @Singleton
    public static DataSource provideDataSource(@DataBaseUrl String databaseUrl)
    {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:" + databaseUrl);
        return ds;
    }

    @Provides
    @Singleton
    public static UserAccessor provideUserAccessor(Jdbi jdbi)
    {
        return new SqliteUserAccessor(jdbi);
    }

    @Provides
    @Singleton
    public static UserDao provideUserDao(Jdbi jdbi)
    {
        return jdbi.onDemand(SqliteUserDao.class);
    }
}
