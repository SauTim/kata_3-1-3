package com.example.kata_313.controller;

import com.example.kata_313.dto.InfoMessageDto;
import com.example.kata_313.dto.UserDto;
import com.example.kata_313.entity.User;
import com.example.kata_313.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> list = userService.findAllUsers();

        return (list != null && !list.isEmpty())
                ? new ResponseEntity<>(list, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {

        User user = userService.findUserById(id);

        return (user != null)
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        User createdUser = userService.addNewUser(userDto);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody UserDto userDto, @PathVariable long id) {
        User updatedUser = userService.updateUser(userDto, id);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<InfoMessageDto> deleteUser(@PathVariable long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok(new InfoMessageDto(0, "User successfully deleted"));
    }
}
