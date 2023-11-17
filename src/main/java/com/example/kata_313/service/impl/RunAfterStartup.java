package com.example.kata_313.service.impl;

import com.example.kata_313.entity.Role;
import com.example.kata_313.entity.User;
import com.example.kata_313.repository.UserRepository;
import com.example.kata_313.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@Slf4j
public class RunAfterStartup {
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    public RunAfterStartup(UserRepository userRepository, RoleService roleService, PasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void populateDataBase() {
        Optional<User> tim = userRepository.findByLogin("tim");
        if (tim.isPresent()) {
            return;
        }

        Role roleAdmin = roleService.save(new Role("ROLE_ADMIN"));
        Role roleUser = roleService.save(new Role("ROLE_USER"));

        User userTim = User.builder()
                .age(23)
                .login("tim")
                .password(bCryptPasswordEncoder.encode("tim"))
                .roles(Set.of(roleUser, roleAdmin))
                .build();
        User user = User.builder()
                .age(24)
                .login("user")
                .password(bCryptPasswordEncoder.encode("user"))
                .roles(Set.of(roleUser))
                .build();
        User admin = User.builder()
                .age(24)
                .login("admin")
                .password(bCryptPasswordEncoder.encode("admin"))
                .roles(Set.of(roleAdmin))
                .build();

        userRepository.save(userTim);
        userRepository.save(user);
        userRepository.save(admin);
        log.info("""
                Created accounts
                                
                ADMIN / USER
                login: {}
                password: tim
                                
                ADMIN
                login: {}
                password: admin
                                
                USER
                login: {}
                password: user
                """, userTim.getLogin(), admin.getLogin(), user.getLogin());
    }
}