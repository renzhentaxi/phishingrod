package Storage.Sqlite;

import Storage.base.Util.DataBaseUrl;
import Storage.base.DatabaseModule;
import Storage.base.Util.StoredSqlLocator;
import Storage.base.Accessors.UserAccessor;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.locator.SqlLocator;
import org.sqlite.SQLiteDataSource;

import javax.inject.Singleton;
import javax.sql.DataSource;

@Module(includes = DatabaseModule.class)
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
    public static UserAccessor provideUserAccessor(Jdbi jdbi, StoredSqlLocator locator) {

        return new UserAccessor(jdbi, locator);
    }

    @Provides
    @Singleton
    public static StoredSqlLocator provideStoredSqlLocator() {
        String rootPath = System.getProperty("user.dir") + "/src/scripts/sql";
        String parentPath = "sqlite";
        StoredSqlLocator locator = new StoredSqlLocator(rootPath, parentPath);

        locator.register("addUser", "user/addUser");
        locator.register("updateUser", "user/updateUser");
        locator.register("getUser", "user/getUser");
        return locator;
    }

    @Binds
    @Singleton
    public abstract SqlLocator provideSqlLocator(StoredSqlLocator storedSqlLocator);
}
