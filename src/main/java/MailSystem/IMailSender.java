package MailSystem;

import Accounts.Entities.Data.UserData;
import Accounts.Entities.Sender;

public interface IMailSender
{
    void Send(IMail mail, Sender from, UserData to);
}
