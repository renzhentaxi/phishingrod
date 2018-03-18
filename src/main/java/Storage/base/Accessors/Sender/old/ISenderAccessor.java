package Storage.base.Accessors.Sender.old;

import Entities.Senders.old.Sender;
import Entities.Senders.old.SenderEntity;
import Storage.base.Accessors.IAccessor;

public interface ISenderAccessor extends IAccessor<SenderEntity, Sender>
{
    SenderEntity getByEmail(String emailAddress);
}
