package com.example.kata_313.repository;

import com.example.kata_313.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}