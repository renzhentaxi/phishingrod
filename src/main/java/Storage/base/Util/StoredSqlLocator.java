package Storage.base.Util;

import org.antlr.runtime.ANTLRFileStream;
import org.jdbi.v3.core.config.ConfigRegistry;
import org.jdbi.v3.core.internal.SqlScriptParser;
import org.jdbi.v3.sqlobject.internal.SqlAnnotations;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class StoredSqlLocator implements AlternativeSqlLocator {
    private static final SqlScriptParser SQL_SCRIPT_PARSER = new SqlScriptParser((t, sb) -> sb.append(t.getText()));

    public static char DEFAULT_SEPARATOR = '/';
    private HashMap<String, String> _scripts;
    private String _rootDirectory;
    private String _parentDirectory;

    public StoredSqlLocator(String rootPath, String parentPath) {
        _scripts = new HashMap<>();
        setRootDirectory(rootPath);
        setParentDirectory(parentPath);

    }

    @Override
    public String locate(Class<?> sqlObjectType, Method method, ConfigRegistry config) {
        String name = SqlAnnotations.getAnnotationValue(method).orElse(null);
        return locate(name);
    }

    public String locate(String name) {
        if (name == null) throw new RuntimeException("name cant be null!!");
        String script = _scripts.get(name);
        if (script == null) throw new RuntimeException("no such script registered: " + name + "!!");
        return script;
    }

    public void setRootDirectory(String rootPath) {
        _rootDirectory = formatDirectory(rootPath, File.separator, File.separator);
    }

    public void setParentDirectory(String parentPath) {
        _parentDirectory = formatDirectory(parentPath, File.separator, File.separator);
    }

    public void register(String name, String relativeLocation) {

        String formattedLocation = _rootDirectory + _parentDirectory + formatDirectory(relativeLocation, ".sql", File.separator);
        try {
            _scripts.put(name, SQL_SCRIPT_PARSER.parse(new ANTLRFileStream(formattedLocation)));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private String formatDirectory(String pathString, String suffix, String noPrefix) {
        pathString = pathString.replace(DEFAULT_SEPARATOR, File.separatorChar);
        if (pathString.startsWith(noPrefix)) {
            pathString = pathString.substring(noPrefix.length());
        }
        if (!pathString.endsWith(suffix)) {
            pathString += suffix;
        }
        return pathString;
    }
}
