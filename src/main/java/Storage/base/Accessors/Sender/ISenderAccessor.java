package Storage.base.Accessors.Sender;

import Accounts.Entities.Data.SenderData;
import Accounts.Entities.Sender;
import Storage.base.Accessors.IAccessor;

public interface ISenderAccessor extends IAccessor<Sender, SenderData>
{
    Sender getByEmail(String emailAddress);
}
