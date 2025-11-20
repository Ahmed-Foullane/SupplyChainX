package com.example.demo.user.Mapper;

import com.example.demo.user.DTO.UserDTO;
import com.example.demo.user.entity.AppUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface UserMapper {
    AppUser toEntity(UserDTO dto);
}