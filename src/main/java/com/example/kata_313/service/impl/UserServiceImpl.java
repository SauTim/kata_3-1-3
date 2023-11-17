package com.example.kata_313.service.impl;

import com.example.kata_313.dto.InfoMessageDto;
import com.example.kata_313.dto.UserDto;
import com.example.kata_313.entity.Role;
import com.example.kata_313.entity.User;
import com.example.kata_313.mapper.UserMapper;
import com.example.kata_313.repository.UserRepository;
import com.example.kata_313.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public UserDto findByLogin(String login) {
        User user = userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User with such login does not exist"));
        UserDto response = userMapper.toUserDto(user);
        response.setRoles(user.getRoles().stream().map(Role::getRole).toArray(String[]::new));
        return response;
    }

    @Override
    public UserDto findUserById(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User with such id does not exist"));
        UserDto response = userMapper.toUserDto(user);
        response.setRoles(user.getRoles().stream().map(Role::getRole).toArray(String[]::new));
        return response;
    }

    @Override
    public List<UserDto> findAllUsers() {
        Sort sort = Sort.by("id").descending();
        List<UserDto> response = userRepository.findAll(sort).stream().map(user -> {
            UserDto dto = userMapper.toUserDto(user);
            dto.setRoles(user.getRoles().stream().map(Role::getRole).toArray(String[]::new));
            return dto;
        }).toList();

        return response;
    }

    @Override
    public UserDto addNewUser(UserDto dto, Set<Role> rolesForAccount) {
        User entity = userMapper.toUserEntity(dto);
        entity.setRoles(rolesForAccount);
        entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));

        return userMapper.toUserDto(userRepository.save(entity));
    }

    @Override
    public UserDto updateUser(UserDto userDto, Set<Role> rolesForAccount) {
        User entity = userMapper.toUserEntity(userDto);
        entity.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        entity.setRoles(rolesForAccount);

        return userMapper.toUserDto(userRepository.save(entity));
    }

    @Override
    public InfoMessageDto deleteUserById(long id) {
        userRepository.deleteById(id);
        return new InfoMessageDto(0, "User was successfully deleted");
    }
}
