package com.example.kata_313.service;


import com.example.kata_313.dto.UserDto;
import com.example.kata_313.entity.User;

import java.util.List;

public interface UserService {
    UserDto findByLogin(String login);

    User findUserById(long id);

    List<User> findAllUsers();

    User addNewUser(UserDto user);

    User updateUser(UserDto userDto, long id);

    void deleteUserById(long id);
}
