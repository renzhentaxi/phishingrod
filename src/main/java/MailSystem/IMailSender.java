package MailSystem;

import Entities.Data.UserData;
import Entities.Sender;

public interface IMailSender
{
    void Send(IMail mail, Sender from, UserData to);
}
