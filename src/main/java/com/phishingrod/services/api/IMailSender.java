package com.phishingrod.services.api;


import com.phishingrod.domain.Sender;
import com.phishingrod.domain.phishingTarget.PhishingTarget;
import com.phishingrod.domain.spoofTarget.SpoofTarget;

public interface IMailSender
{
    void Send(String mail, PhishingTarget to, SpoofTarget from, Sender sender);
}
