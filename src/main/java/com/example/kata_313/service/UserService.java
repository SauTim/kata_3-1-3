package com.example.kata_313.service;


import com.example.kata_313.dto.InfoMessageDto;
import com.example.kata_313.dto.UserDto;
import com.example.kata_313.entity.Role;

import java.util.List;
import java.util.Set;

public interface UserService {
    UserDto findByLogin(String login);

    UserDto findUserById(long id);

    List<UserDto> findAllUsers();

    UserDto addNewUser(UserDto user, Set<Role> rolesForAccount);

    UserDto updateUser(UserDto userDto, Set<Role> rolesForAccount);

    InfoMessageDto deleteUserById(long id);
}
