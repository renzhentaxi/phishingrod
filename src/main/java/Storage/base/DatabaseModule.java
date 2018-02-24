package Storage.base;

import dagger.Module;
import dagger.Provides;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import javax.inject.Singleton;
import javax.sql.DataSource;

@Module
public class DatabaseModule
{
    @Provides
    @Singleton
    public static Jdbi provideJdbi(DataSource dataSource)
    {
        Jdbi jdbi = Jdbi.create(dataSource);
        jdbi.installPlugin(new SqlObjectPlugin());
        jdbi.registerRowMapper(new UserMapper());
        return jdbi;
    }

}
