package com.example.kata_313.service;


import com.example.kata_313.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();

    Role getRoleByName(String roleName);

    Role save(Role role);
}
