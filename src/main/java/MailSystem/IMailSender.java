package MailSystem;

import Entities.Senders.ISender;
import Entities.Users.IUser;

public interface IMailSender
{
    void Send(IMailTemplate mail, ISender from, IUser to);
}
