package Storage.base;

import Storage.base.Daos.UserDao;
import Storage.base.Util.StoredSqlLocator;
import Storage.base.Util.UserMapper;
import dagger.Module;
import dagger.Provides;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.jdbi.v3.sqlobject.SqlObjects;
import org.jdbi.v3.sqlobject.locator.SqlLocator;

import javax.inject.Singleton;
import javax.sql.DataSource;

@Module
public class DatabaseModule {
    @Provides
    @Singleton
    public static Jdbi provideJdbi(DataSource dataSource, SqlLocator sqlLocator) {
        Jdbi jdbi = Jdbi.create(dataSource);
        jdbi.installPlugin(new SqlObjectPlugin());
        jdbi.registerRowMapper(new UserMapper());
        jdbi.getConfig().get(SqlObjects.class).setSqlLocator(sqlLocator);
        return jdbi;
    }

    @Provides
    @Singleton
    public static UserDao provideUserDao(Jdbi jdbi) {
        return jdbi.onDemand(UserDao.class);
    }
}
