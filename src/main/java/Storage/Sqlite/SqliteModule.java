package Storage.Sqlite;

import Storage.base.Accessors.ExceptionHandler;
import Storage.base.Accessors.Sender.SenderAccessor;
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
        return ds;
    }

    @Provides
    @Singleton
    public static UserAccessor provideUserAccessor(Jdbi jdbi, ExceptionHandler exceptionHandler, AlternativeSqlLocator locator)
    {
        return new UserAccessor(jdbi, exceptionHandler, locator);
    }

    @Provides
    @Singleton
    public static SenderAccessor provideSenderAccessor(Jdbi jdbi, AlternativeSqlLocator locator, ExceptionHandler exceptionHandler, UserAccessor userAccessor)
    {
        return new SenderAccessor(jdbi, exceptionHandler, locator, userAccessor);
    }

    @Provides
    @Singleton
    public static ExceptionHandler provideExceptionHandler()
    {
        return new SqliteExceptionHandler();
    }
}
