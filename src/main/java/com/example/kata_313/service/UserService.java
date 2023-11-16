package com.example.kata_313.service;


import com.example.kata_313.dto.InfoMessageDto;
import com.example.kata_313.dto.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    UserDto findByLogin(String login);

    ResponseEntity<UserDto> findUserById(long id);

    ResponseEntity<List<UserDto>> findAllUsers();

    ResponseEntity<UserDto> addNewUser(UserDto user);

    ResponseEntity<UserDto> updateUser(UserDto userDto, long id);

    ResponseEntity<InfoMessageDto> deleteUserById(long id);
}
