package Storage.base.Accessors.Sender;


import Entities.Senders.ISender;
import Entities.Senders.ISenderEntity;
import Storage.base.Accessors.IWithHandleAccessor;

public interface ISenderAccessor extends IWithHandleAccessor<ISenderEntity, ISender>
{
    ISenderEntity getByEmail(String emailAddress);

    boolean exist(String emailAddress);
}