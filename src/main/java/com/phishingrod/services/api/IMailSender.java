package com.phishingrod.services.api;


import com.phishingrod.domain.Sender;
import com.phishingrod.domain.SpoofTarget;
import com.phishingrod.domain.phishingTarget.PhishingTarget;

public interface IMailSender
{
    void Send(String mail, PhishingTarget to, SpoofTarget from, Sender sender);
}
