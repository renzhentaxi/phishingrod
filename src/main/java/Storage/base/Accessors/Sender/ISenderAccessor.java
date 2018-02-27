package Storage.base.Accessors.Sender;

import Entities.Data.SenderData;
import Entities.Sender;
import Storage.base.Accessors.IAccessor;

public interface ISenderAccessor extends IAccessor<Sender, SenderData>
{
    Sender getByEmail(String emailAddress);
}
