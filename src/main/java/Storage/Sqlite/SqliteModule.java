package Storage.Sqlite;

import Storage.base.Accessors.ExceptionHandler;
import Storage.base.Accessors.Sender.ISenderAccessor;
import Storage.base.Accessors.Sender.SenderAccessor;
import Storage.base.Accessors.SessionType.ISessionTypeAccessor;
import Storage.base.Accessors.SessionType.SessionTypeAccessor;
import Storage.base.Accessors.User.IUserAccessor;
import Storage.base.Accessors.User.UserAccessor;
import Storage.base.Util.AlternativeSqlLocator;
import Storage.base.Util.DataBaseUrl;
import dagger.Module;
import dagger.Provides;
import org.jdbi.v3.core.Jdbi;
import org.sqlite.SQLiteDataSource;

import javax.inject.Singleton;
import javax.sql.DataSource;

@Module
public abstract class SqliteModule {
    @Provides
    @Singleton
    public static DataSource provideDataSource(@DataBaseUrl String databaseUrl) {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:" + databaseUrl);
        ds.setEnforceForeignKeys(true);
        return ds;
    }

    @Provides
    @Singleton
    public static IUserAccessor provideUserAccessor(Jdbi jdbi, AlternativeSqlLocator locator)
    {
        return new UserAccessor(jdbi, locator);
    }

    @Provides
    @Singleton
    public static ISessionTypeAccessor provideSessionTypeAccessor(Jdbi jdbi, AlternativeSqlLocator locator)
    {
        return new SessionTypeAccessor(jdbi, locator);
    }

    @Provides
    @Singleton
    public static ISenderAccessor provideSenderAccessor(Jdbi jdbi, AlternativeSqlLocator locator, IUserAccessor userAccessor, ISessionTypeAccessor sessionTypeAccessor)
    {
        return new SenderAccessor(jdbi, locator, userAccessor, sessionTypeAccessor);
    }

    @Provides
    @Singleton
    public static ExceptionHandler provideExceptionHandler()
    {
        return new SqliteExceptionHandler();
    }
}
