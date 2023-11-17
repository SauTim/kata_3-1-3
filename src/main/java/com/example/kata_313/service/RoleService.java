package com.example.kata_313.service;


import com.example.kata_313.dto.UserDto;
import com.example.kata_313.entity.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<Role> getAllRoles();

    Role getRoleByName(String roleName);

    Role save(Role role);

    void appendRoles(long id, UserDto response);

    Set<Role> getRolesByNames(UserDto userDto);
}
