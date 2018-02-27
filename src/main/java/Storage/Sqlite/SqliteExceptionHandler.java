package Storage.Sqlite;

import Storage.base.Accessors.ExceptionHandler;

public class SqliteExceptionHandler implements ExceptionHandler
{
    @Override
    public RuntimeException Handle(RuntimeException exception)
    {
        throw exception;
    }
}
