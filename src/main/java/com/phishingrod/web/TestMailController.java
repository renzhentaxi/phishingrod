package com.phishingrod.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestMailController
{
    @GetMapping("/testHtml")
    public String templateGenerator()
    {
        return "testHtml";
    }

}
