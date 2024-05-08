package com.shopAminute.shopAminute.handlers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {


    @GetMapping("/authentication")
    public String login() {
        return "authentication";
    }

}