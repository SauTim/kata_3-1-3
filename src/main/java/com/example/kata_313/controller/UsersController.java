package com.example.kata_313.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class UsersController {

    @GetMapping("/user")
    public String showUser() {
        return "user";
    }

}
