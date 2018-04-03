package com.phishingrod.services.api;


import com.phishingrod.domain.PhishingTarget;
import com.phishingrod.domain.Sender;
import com.phishingrod.domain.SpoofTarget;

public interface IMailSender
{
    void Send(String mail, PhishingTarget to, SpoofTarget from, Sender sender);
}
