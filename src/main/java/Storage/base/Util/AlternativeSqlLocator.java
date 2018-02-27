package Storage.base.Util;

import org.jdbi.v3.sqlobject.locator.SqlLocator;

public interface AlternativeSqlLocator extends SqlLocator {
    String locate(String identifier);
}
