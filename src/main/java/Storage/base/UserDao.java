package Storage.base;

import Accounts.User;
import Storage.base.JdbiUtil.UseFilePathSqlLocator;

@UseFilePathSqlLocator
public interface UserDao extends DatabaseAccessor<User>
{
}
