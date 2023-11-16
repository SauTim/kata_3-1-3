package com.example.kata_313.service.impl;

import com.example.kata_313.dto.InfoMessageDto;
import com.example.kata_313.dto.UserDto;
import com.example.kata_313.entity.Role;
import com.example.kata_313.entity.User;
import com.example.kata_313.repository.UserRepository;
import com.example.kata_313.service.RoleService;
import com.example.kata_313.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    public UserDto findByLogin(String login) {
        User user = userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User with such login does not exist"));
        return mapUserToDto(user);
    }

    @Override
    public ResponseEntity<UserDto> findUserById(long id) {
        return ResponseEntity.ok(mapUserToDto(userRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("User with such id does not exist"))));
    }

    @Override
    public ResponseEntity<List<UserDto>> findAllUsers() {
        Sort sort = Sort.by("id").descending();
        return ResponseEntity.ok(userRepository.findAll(sort).stream().map(this::mapUserToDto).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<UserDto> addNewUser(UserDto dto) {
        return ResponseEntity.ok(mapUserToDto(userRepository.save(mapDtoToUser(dto))));
    }

    @Override
    public ResponseEntity<UserDto> updateUser(UserDto userDto, long id) {
        userDto.setId(id);
        return ResponseEntity.ok(mapUserToDto(userRepository.save(mapDtoToUser(userDto))));
    }

    @Override
    public ResponseEntity<InfoMessageDto> deleteUserById(long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok(new InfoMessageDto(0, "User was successfully deleted"));
    }

    private User mapDtoToUser(UserDto dto) {

        Set<Role> roleSet = new HashSet<>();

        for (String role : dto.getRoles()) {
            if (role == null) continue;
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

    private UserDto mapUserToDto(User entity) {

        return UserDto.builder()
                .id(entity.getId())
                .age(entity.getAge())
                .login(entity.getLogin())
                .roles(entity.getRoles().stream().map(Role::getRole).toArray(String[]::new))
                .build();
    }

    @Scheduled(fixedDelay = 100000L)
    protected void populateDataBase() {
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
        System.out.println("""
                ADMIN / USER
                login: tim
                password: tim
                                
                ADMIN
                login: admin
                password: admin
                
                USER
                login: user
                password: user
                """);
    }
}
