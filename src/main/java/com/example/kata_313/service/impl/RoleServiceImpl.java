package com.example.kata_313.service.impl;

import com.example.kata_313.dto.UserDto;
import com.example.kata_313.entity.Role;
import com.example.kata_313.repository.RoleRepository;
import com.example.kata_313.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleRepository.findByRole(roleName).orElseThrow(() -> new EntityNotFoundException("Role not found"));
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Set<Role> getRolesByNames(UserDto userDto) {
        Set<Role> roleSet = new HashSet<>();

        for (String role : userDto.getRoles()) {
            if (role == null) continue;
            roleSet.add(getRoleByName(role));
        }

        return roleSet;
    }
}
