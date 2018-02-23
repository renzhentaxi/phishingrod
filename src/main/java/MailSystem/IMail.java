package MailSystem;

import Accounts.Sender;
import Accounts.User;

import javax.mail.Message;

public interface IMail
{
    Message getMessage(Sender from, User to);

}
