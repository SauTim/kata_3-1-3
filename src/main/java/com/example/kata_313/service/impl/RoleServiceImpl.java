package com.example.kata_313.service.impl;

import com.example.kata_313.entity.Role;
import com.example.kata_313.repository.RoleRepository;
import com.example.kata_313.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
