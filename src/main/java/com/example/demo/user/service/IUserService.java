package com.example.demo.user.service;

import com.example.demo.user.DTO.UserDTO;
import com.example.demo.user.entity.User;
import com.example.demo.user.entity.enume.Role;

public interface IUserService {
    User createUser(Role role, UserDTO dto);
    User updateRole(Role role, Long id);
}
