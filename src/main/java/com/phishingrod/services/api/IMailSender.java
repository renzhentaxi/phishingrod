package com.phishingrod.services.api;


import com.phishingrod.domain.Sender;
import com.phishingrod.domain.phishingTarget.PhishingTarget;
import com.phishingrod.spoofTarget.SpoofTarget;

public interface IMailSender
{
    void Send(String mail, PhishingTarget to, SpoofTarget from, Sender sender);
}
