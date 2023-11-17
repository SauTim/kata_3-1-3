package com.example.kata_313.service.impl;

import com.example.kata_313.dto.UserDto;
import com.example.kata_313.entity.Role;
import com.example.kata_313.repository.RoleRepository;
import com.example.kata_313.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleRepository.findByRole(roleName).orElseThrow(() -> new EntityNotFoundException("Role not found"));
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void appendRoles(long id, UserDto response) {

    }

    @Override
    public Set<Role> getRolesByNames(UserDto userDto) {
        Set<Role> roleSet = new HashSet<>();

        for (String role : userDto.getRoles()) {
            if (role == null) continue;
            roleSet.add(getRoleByName(role));
        }

        return roleSet;
    }
}
