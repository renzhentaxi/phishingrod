package MailSystem;

import Entities.Senders.ISender;
import Entities.Users.IUser;

import javax.mail.Message;

public interface IMail
{
    Message getMessage(ISender from, IUser to);

}
