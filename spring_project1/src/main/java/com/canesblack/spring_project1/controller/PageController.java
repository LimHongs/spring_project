package com.canesblack.spring_project1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String Home() {
        return "index";
    }

    @GetMapping("/register")
    public String registerPage() {
    	return "login/register";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
    	return "login/login";
    }
}