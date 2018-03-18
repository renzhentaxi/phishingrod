package Entities.Senders;

import Entities.IEntity;
import Entities.Users.IUserEntity;

/**
 * There is no setUser or setUserEntity method because a sender is technically an extension of a user. So the user is part of the sender's identity and is unmodifiable.
 * If you need to modify user's properties such as emailAddress, you can modify it directly by using getUserEntity.
 */
public interface ISenderEntity extends IEntity, ISender, IUserEntity
{
    void setPassword(String password);

    void setSessionTypeName(String sessionTypeName);

    boolean equals(ISenderEntity other);
}
