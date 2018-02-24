package Storage.base.JdbiUtil;

import org.jdbi.v3.core.config.ConfigRegistry;
import org.jdbi.v3.sqlobject.SqlObjects;
import org.jdbi.v3.sqlobject.config.Configurer;
import org.jdbi.v3.sqlobject.locator.SqlLocator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class UseFilePathSqlLocatorImpl implements Configurer {

    @Override
    public void configureForType(ConfigRegistry registry, Annotation annotation, Class<?> sqlObjectType) {
        registry.get(SqlObjects.class).setSqlLocator(new FilePathSqlLocator());
    }

    @Override
    public void configureForMethod(ConfigRegistry registry, Annotation annotation, Class<?> sqlObjectType, Method method) {
        configureForType(registry, annotation, sqlObjectType);
    }
}
