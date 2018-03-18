package Storage.base.Accessors.Sender;


import Entities.Senders.ISender;
import Entities.Senders.ISenderEntity;
import Storage.base.Accessors.IAccessor;
import Storage.base.Accessors.IWithHandleAccessor;

public interface ISenderAccessor extends IAccessor<ISenderEntity, ISender>, IWithHandleAccessor<ISenderEntity, ISender>
{
    ISenderEntity getByEmail(String emailAddress);

    boolean exist(String emailAddress);
}