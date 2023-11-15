package com.example.kata_313.service.impl;

import com.example.kata_313.dto.UserDto;
import com.example.kata_313.entity.Role;
import com.example.kata_313.entity.User;
import com.example.kata_313.repository.UserRepository;
import com.example.kata_313.service.RoleService;
import com.example.kata_313.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(RoleService roleService, UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User with such login does not exist"));
    }

    @Override
    public User findUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User with such id does not exist"));
    }

    @Override
    public List<User> findAllUsers() {
        Sort sort = Sort.by("id").descending();
        return userRepository.findAll(sort);
    }

    @Override
    public User addNewUser(UserDto dto) {
        return userRepository.save(mapDtoToUser(dto));
    }

    @Override
    public User updateUser(UserDto userDto, long id) {
        userDto.setId(id);
        return userRepository.save(mapDtoToUser(userDto));
    }

    @Override
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    private User mapDtoToUser(UserDto dto) {

        Set<Role> roleSet = new HashSet<>();

        for (String role : dto.getRoles()) {
            roleSet.add(roleService.getRoleByName(role));
        }

        return User.builder()
                .id(dto.getId() == null ? null : dto.getId())
                .age(dto.getAge())
                .login(dto.getLogin())
                .roles(roleSet)
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build();
    }
}
