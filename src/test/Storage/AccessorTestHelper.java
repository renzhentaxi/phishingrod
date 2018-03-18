package Storage;

import Storage.Sqlite.SqliteSqlLocatorModule;
import Storage.base.Mappers.SessionTypeMapper;
import Storage.base.Mappers.UserMapper;
import Storage.base.Util.AlternativeSqlLocator;
import Storage.base.Util.StoredSqlLocator;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.Script;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccessorTestHelper
{
    public static final String path = System.getProperty("user.dir") + "/src/scripts/sql/sqlite/";

    public static Jdbi getJdbi(RowMapper... mappers)
    {
        Jdbi jdbi = Jdbi.create("jdbc:sqlite:" + System.getProperty("user.dir") + "/src/test/testDb.db");
        try (Handle handle = jdbi.open())
        {
            Script hardClean = handle.createScript(StoredSqlLocator.getScriptAt(path + "HardClean.sql"));
            hardClean.executeAsSeparateStatements();
            Script setup = handle.createScript(StoredSqlLocator.getScriptAt(path + "setup.sql"));
            setup.executeAsSeparateStatements();
        }
        for (RowMapper mapper : mappers)
        {
            jdbi.registerRowMapper(mapper);
        }
        return jdbi;
    }

    public static AlternativeSqlLocator getMockSqlLocator(String... data)
    {
        AlternativeSqlLocator sqlLocator = mock(StoredSqlLocator.class);
        if (data.length % 2 == 1) throw new IllegalArgumentException("odd number argument!!");
        for (int i = 0; i < data.length; i += 2)
        {
            String name = data[i];
            String location = data[i + 1];
            when(sqlLocator.locate(name))
                    .thenReturn(StoredSqlLocator.getScriptAt(path + location + ".sql"));
        }

        return sqlLocator;
    }

    public static Jdbi getSimpleJdbi()
    {
        return getJdbi(new UserMapper(), new SessionTypeMapper());
    }

    public static AlternativeSqlLocator getSimpleSqlLocator()
    {
        return SqliteSqlLocatorModule.provideAlternativeSqlLocator();
    }

}
