package MailSystem;

import Accounts.Entities.Data.UserData;
import Accounts.Entities.Sender;

import javax.mail.Message;

public interface IMail
{
    Message getMessage(Sender from, UserData to);

}
