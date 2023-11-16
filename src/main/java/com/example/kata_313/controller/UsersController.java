package com.example.kata_313.controller;

import com.example.kata_313.dto.UserDto;
import com.example.kata_313.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String showUser() {
        return "user";
    }

    @GetMapping("/info")
    public ResponseEntity<UserDto> getAuthorizedUser() {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto user = userService.findByLogin(userDetails.getUsername());

        return ResponseEntity.ok().body(user);
    }

}
