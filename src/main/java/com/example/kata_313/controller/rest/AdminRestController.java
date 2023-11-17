package com.example.kata_313.controller.rest;

import com.example.kata_313.dto.InfoMessageDto;
import com.example.kata_313.dto.UserDto;
import com.example.kata_313.entity.Role;
import com.example.kata_313.service.RoleService;
import com.example.kata_313.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin")
public class AdminRestController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> response = userService.findAllUsers();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long id) {
        UserDto response = userService.findUserById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        Set<Role> rolesForAccount = roleService.getRolesByNames(userDto);
        UserDto response = userService.addNewUser(userDto, rolesForAccount);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/users")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        Set<Role> rolesForAccount = roleService.getRolesByNames(userDto);
        UserDto response = userService.updateUser(userDto, rolesForAccount);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<InfoMessageDto> deleteUser(@PathVariable long id) {
        InfoMessageDto response = userService.deleteUserById(id);
        return ResponseEntity.ok(response);
    }
}
