package Storage.base.Accessors;

import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.sqlite.SQLiteErrorCode;
import org.sqlite.SQLiteException;

public class AccessorUtil
{
    public static boolean isExceptionDueToEntityAlreadyExist(UnableToExecuteStatementException exception, String keyName)
    {
        Throwable cause = exception.getCause();
        if (cause instanceof SQLiteException)
        {
            SQLiteException sqLiteException = (SQLiteException) cause;
            return sqLiteException.getResultCode() == SQLiteErrorCode.SQLITE_CONSTRAINT_UNIQUE && sqLiteException.getMessage().contains(keyName);
        }
        return false;
    }
}
