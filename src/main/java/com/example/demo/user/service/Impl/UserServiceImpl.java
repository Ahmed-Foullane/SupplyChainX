package com.example.demo.user.service.Impl;

import com.example.demo.user.DTO.UserDTO;
import com.example.demo.user.Mapper.UserMapper;
import com.example.demo.user.entity.AppUser;
import com.example.demo.user.entity.enume.Role;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private PasswordEncoder passwordEncoded;

    @Override
    public AppUser createUser(Role role, UserDTO dto) {
        AppUser user = userMapper.toEntity(dto);
        String encodedPassowrd = passwordEncoded.encode(dto.password());
        user.setPassword(encodedPassowrd);
        user.setRole(role);
        return userRepository.save(user);
    }

    @Override
    public AppUser updateRole(Role role, Long id) {
        AppUser user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("the user id not found: " + id));
        user.setRole(role);
        return userRepository.save(user);
    }

        @Override
        public AppUser searchUserByEmail(String email) {
            return userRepository.findUserByEmailContainingIgnoreCase(email)
                    .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        }
}
