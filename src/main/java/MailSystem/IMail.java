package MailSystem;

import Entities.Data.User;
import Entities.SenderEntity;

import javax.mail.Message;

public interface IMail
{
    Message getMessage(SenderEntity from, User to);

}
