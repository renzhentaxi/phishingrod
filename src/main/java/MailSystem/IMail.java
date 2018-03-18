package MailSystem;

import Entities.Senders.old.SenderEntity;
import Entities.Users.User;

import javax.mail.Message;

public interface IMail
{
    Message getMessage(SenderEntity from, User to);

}
