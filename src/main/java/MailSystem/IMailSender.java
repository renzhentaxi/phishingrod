package MailSystem;

import Accounts.Receiver;
import Accounts.Sender;

public interface IMailSender
{
    void Send(IMail mail, Sender from, Receiver to);
}
