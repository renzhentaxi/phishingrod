package Storage;

import Storage.Sqlite.SqliteSqlLocatorModule;
import Storage.base.Util.AlternativeSqlLocator;
import Storage.base.Util.StoredSqlLocator;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.Script;
import org.sqlite.SQLiteDataSource;

public class AccessorTestHelper
{
    private static final String path = System.getProperty("user.dir") + "/src/scripts/sql/sqlite/";

    public static Jdbi newJdbi(RowMapper... mappers)
    {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:" + System.getProperty("user.dir") + "/src/test/testDb.db");
        ds.setEnforceForeignKeys(true);

        Jdbi jdbi = Jdbi.create(ds);
        for (RowMapper mapper : mappers)
        {
            jdbi.registerRowMapper(mapper);
        }
        return jdbi;
    }

    public static void clearDatabase(Jdbi jdbi)
    {
        try (Handle handle = jdbi.open())
        {
            Script hardClean = handle.createScript(StoredSqlLocator.getScriptAt(path + "HardClean.sql"));
            hardClean.executeAsSeparateStatements();
        }
    }

    public static void setupDatabase(Jdbi jdbi)
    {
        try (Handle handle = jdbi.open())
        {
            Script setup = handle.createScript(StoredSqlLocator.getScriptAt(path + "setup.sql"));
            setup.executeAsSeparateStatements();
        }
    }
    public static AlternativeSqlLocator getSimpleSqlLocator()
    {
        return SqliteSqlLocatorModule.provideAlternativeSqlLocator();
    }

}
