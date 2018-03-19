package MailSystem;

import Entities.Senders.ISender;
import Entities.Users.IUser;

import javax.mail.Message;

public interface IMailTemplate
{
    Message generateMail(ISender from, IUser to);

}
