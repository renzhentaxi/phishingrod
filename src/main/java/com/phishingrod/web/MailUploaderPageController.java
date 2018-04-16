package com.phishingrod.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/mail/upload")

public class MailUploaderPageController
{
    @GetMapping()
    public String uploader()
    {
        return "uploadHtml";
    }
}
