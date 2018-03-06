package MailSystem;

import Entities.Users.User;
import Entities.Senders.SenderEntity;

import javax.mail.Message;

public interface IMail
{
    Message getMessage(SenderEntity from, User to);

}
