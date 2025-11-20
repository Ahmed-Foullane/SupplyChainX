package com.example.demo.user.repository;

import com.example.demo.user.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findUserByEmailContainingIgnoreCase(String email);
}
