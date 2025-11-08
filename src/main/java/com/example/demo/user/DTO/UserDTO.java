package com.example.demo.user.DTO;

import com.example.demo.user.entity.enume.Role;
import jakarta.validation.constraints.NotEmpty;

public record UserDTO(

        @NotEmpty(message = "the first name is required")
        String firstName,
        @NotEmpty(message = "the last name is required")
        String lastName,
        @NotEmpty(message = "the email is required")
        String email,
        @NotEmpty(message = "the password is required")
        String password


) {
}