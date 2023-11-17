package com.example.kata_313.service;


import com.example.kata_313.dto.UserDto;
import com.example.kata_313.entity.Role;

import java.util.Set;

public interface RoleService {

    Role getRoleByName(String roleName);

    Role save(Role role);

    Set<Role> getRolesByNames(UserDto userDto);
}
