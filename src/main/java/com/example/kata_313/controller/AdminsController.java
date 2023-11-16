package com.example.kata_313.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminsController {

    @GetMapping("/admin")
    public String getAdminPage() {
        return "admin";
    }

}
