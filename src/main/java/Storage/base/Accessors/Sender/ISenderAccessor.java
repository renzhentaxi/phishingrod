package Storage.base.Accessors.Sender;

import Entities.Senders.Sender;
import Entities.Senders.SenderEntity;
import Storage.base.Accessors.IAccessor;

public interface ISenderAccessor extends IAccessor<SenderEntity, Sender>
{
    SenderEntity getByEmail(String emailAddress);
}
