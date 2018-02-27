package MailSystem;

import Entities.Data.UserData;
import Entities.Sender;

import javax.mail.Message;

public interface IMail
{
    Message getMessage(Sender from, UserData to);

}
