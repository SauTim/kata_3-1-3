package com.example.kata_313.service;


import com.example.kata_313.entity.User;

import java.util.List;

public interface UserService {
    User findByLogin(String login);

    User findUserById(long id);

    List<User> findAllUsers();

    void addNewUser(User user);

    void updateUser(User user);

    void deleteUserById(long id);

}
