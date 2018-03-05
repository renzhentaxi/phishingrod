package Storage.Sqlite;

import Storage.base.Accessors.ExceptionHandler;

public class SqliteExceptionHandler implements ExceptionHandler
{
    @Override
    public RuntimeException Strip(RuntimeException exception)
    {
        throw exception;
    }
}
