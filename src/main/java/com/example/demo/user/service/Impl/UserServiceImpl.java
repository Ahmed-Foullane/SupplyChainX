package com.example.demo.user.service.Impl;

import com.example.demo.user.DTO.UserDTO;
import com.example.demo.user.Mapper.UserMapper;
import com.example.demo.user.entity.User;
import com.example.demo.user.entity.enume.Role;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User createUser(Role role, UserDTO dto) {
        User user = userMapper.toEntity(dto);
        user.setRole(role);
        return userRepository.save(user);
    }

    @Override
    public User updateRole(Role role, Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("the user id not found: " + id));
        user.setRole(role);
        return userRepository.save(user);
    }
}
