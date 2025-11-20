package com.example.demo.user.service;

import com.example.demo.user.DTO.UserDTO;
import com.example.demo.user.entity.AppUser;
import com.example.demo.user.entity.enume.Role;

public interface IUserService {
    AppUser createUser(Role role, UserDTO dto);
    AppUser updateRole(Role role, Long id);
    AppUser searchUserByEmail(String email);
}
