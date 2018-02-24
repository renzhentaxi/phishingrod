package Storage.base.JdbiUtil;

import org.antlr.runtime.ANTLRFileStream;
import org.jdbi.v3.core.config.ConfigRegistry;
import org.jdbi.v3.core.internal.SqlScriptParser;
import org.jdbi.v3.sqlobject.internal.SqlAnnotations;
import org.jdbi.v3.sqlobject.locator.SqlLocator;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

public class FilePathSqlLocator implements SqlLocator {
    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String SQL_FOLDER_PATH = USER_DIR + File.separator + "src" + File.separator + "scripts" + File.separator + "sql";
    private static final SqlScriptParser SQL_SCRIPT_PARSER = new SqlScriptParser((t, sb) -> sb.append(t.getText()));


    @Override
    public String locate(Class<?> sqlObjectType, Method method, ConfigRegistry config) {
        String path = SqlAnnotations.getAnnotationValue(method).orElse(null);
        return locateRelative(path);
    }

    public static String locateRelative(String path) {
        if (path == null) throw new RuntimeException("Path cant be null!!");

        path = SQL_FOLDER_PATH + File.separator + path + ".sql";
        try {
            return SQL_SCRIPT_PARSER.parse(new ANTLRFileStream(path));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        throw new RuntimeException("Path cant be found!: " + path);
    }

    public static String locate(String path) {
        if (path == null) throw new RuntimeException("Path cant be null!!");
        try {
            return SQL_SCRIPT_PARSER.parse(new ANTLRFileStream(path));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        throw new RuntimeException("Path cant be found!: " + path);
    }
}
