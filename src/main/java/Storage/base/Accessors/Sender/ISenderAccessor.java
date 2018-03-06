package Storage.base.Accessors.Sender;

import Entities.Data.Sender;
import Entities.SenderEntity;
import Storage.base.Accessors.IAccessor;

public interface ISenderAccessor extends IAccessor<SenderEntity, Sender>
{
    SenderEntity getByEmail(String emailAddress);
}
