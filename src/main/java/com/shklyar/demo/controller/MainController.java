package com.shklyar.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class MainController {
    @RequestMapping({"", "/"})
    public String index()
    {
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

}
