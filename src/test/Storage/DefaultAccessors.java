package Storage;

import Storage.base.Accessors.Sender.SenderAccessor;
import Storage.base.Accessors.SessionType.ISessionTypeAccessor;
import Storage.base.Accessors.SessionType.SessionTypeAccessor;
import Storage.base.Accessors.User.IUserAccessor;
import Storage.base.Accessors.User.UserAccessor;
import org.jdbi.v3.core.Jdbi;

public class DefaultAccessors
{
    public static UserAccessor getDefaultUserAccessor(Jdbi jdbi)
    {
        return new UserAccessor(jdbi, AccessorTestHelper.getSimpleSqlLocator());
    }

    public static SessionTypeAccessor getDefaultSessionTypeAccessor(Jdbi jdbi)
    {
        return new SessionTypeAccessor(jdbi, AccessorTestHelper.getSimpleSqlLocator());
    }

    public static SenderAccessor getDefaultSenderAccessor(Jdbi jdbi, IUserAccessor userAccessor, ISessionTypeAccessor sessionTypeAccessor)
    {
        return new SenderAccessor(jdbi, AccessorTestHelper.getSimpleSqlLocator(), userAccessor, sessionTypeAccessor);
    }
}
