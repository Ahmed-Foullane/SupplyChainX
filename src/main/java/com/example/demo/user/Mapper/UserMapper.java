package com.example.demo.user.Mapper;

import com.example.demo.supplier.mapper.ISupplierMapper;
import com.example.demo.user.DTO.UserDTO;
import com.example.demo.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface UserMapper {
    User toEntity(UserDTO dto);
}