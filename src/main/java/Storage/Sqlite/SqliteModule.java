package Storage.Sqlite;

import Storage.base.DataBaseUrl;
import Storage.base.DatabaseModule;
import Storage.base.JdbiUtil.StoredSqlLocator;
import Storage.base.UserAccessor;
import Storage.base.UserDao;
import dagger.Module;
import dagger.Provides;
import org.jdbi.v3.core.Jdbi;
import org.sqlite.SQLiteDataSource;

import javax.inject.Singleton;
import javax.sql.DataSource;

@Module(includes = DatabaseModule.class)
public class SqliteModule {
    @Provides
    @Singleton
    public static DataSource provideDataSource(@DataBaseUrl String databaseUrl) {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:" + databaseUrl);
        return ds;
    }

    @Provides
    @Singleton
    public static UserAccessor provideUserAccessor(Jdbi jdbi) {
        String rootPath = System.getProperty("user.dir") + "/src/scripts/sql";
        String parentPath = "sqlite";
        StoredSqlLocator locator = new StoredSqlLocator(rootPath, parentPath);

        locator.register("addUser", "user/addUser");
        locator.register("updateUser", "user/updateUser");
        locator.register("getUser", "user/getUser");
        return new UserAccessor(jdbi, locator);
    }

    @Provides
    @Singleton
    public static UserDao provideUserDao(Jdbi jdbi) {
        return jdbi.onDemand(SqliteUserDao.class);
    }
}
