package MailSystem;

import Accounts.Sender;
import Accounts.User;

public interface IMailSender
{
    void Send(IMail mail, Sender from, User to);
}
