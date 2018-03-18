package Entities.Senders;

import Entities.Users.IUser;

public interface ISender extends IUser
{
    String getSessionTypeName();

    String getPassword();

    boolean equals(ISender other);
}
