package MailSystem;

import Entities.Data.User;
import Entities.SenderEntity;

public interface IMailSender
{
    void Send(IMail mail, SenderEntity from, User to);
}
