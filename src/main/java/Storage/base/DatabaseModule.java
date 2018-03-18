package Storage.base;

import Storage.base.Mappers.SenderMapper;
import Storage.base.Mappers.SessionTypeMapper;
import Storage.base.Mappers.UserMapper;
import Storage.base.Util.AlternativeSqlLocator;
import dagger.Module;
import dagger.Provides;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.jdbi.v3.sqlobject.SqlObjects;

import javax.inject.Singleton;
import javax.sql.DataSource;

@Module
public class DatabaseModule {
    @Provides
    @Singleton
    public static Jdbi provideJdbi(DataSource dataSource, AlternativeSqlLocator sqlLocator) {
        Jdbi jdbi = Jdbi.create(dataSource);
        jdbi.installPlugin(new SqlObjectPlugin());
        jdbi.registerRowMapper(new UserMapper());
        jdbi.registerRowMapper(new SenderMapper());
        jdbi.registerRowMapper(new SessionTypeMapper());
        jdbi.getConfig().get(SqlObjects.class).setSqlLocator(sqlLocator);
        return jdbi;
    }

}
