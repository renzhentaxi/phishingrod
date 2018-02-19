package MailSystem;

import Accounts.Receiver;
import Accounts.Sender;

import javax.mail.Message;

public interface IMail
{
    Message getMessage(Sender from, Receiver to);

}
