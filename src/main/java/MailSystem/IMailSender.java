package MailSystem;

import Entities.Senders.old.SenderEntity;
import Entities.Users.User;

public interface IMailSender
{
    void Send(IMail mail, SenderEntity from, User to);
}
