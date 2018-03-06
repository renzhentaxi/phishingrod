package MailSystem;

import Entities.Users.User;
import Entities.Senders.SenderEntity;

public interface IMailSender
{
    void Send(IMail mail, SenderEntity from, User to);
}
