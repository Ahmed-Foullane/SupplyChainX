package com.example.demo.user.DTO;

import jakarta.validation.constraints.NotEmpty;

public record LoginUserDto(
        @NotEmpty(message = "the email is required")
        String email,
        @NotEmpty(message = "the password is required")
        String password
) {
}
