package com.example.demo.user.controller;

import com.example.demo.user.DTO.UserDTO;
import com.example.demo.user.entity.enume.Role;
import com.example.demo.user.service.IUserService;
import com.example.demo.utils.Validation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "user", description = "manage users")
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final IUserService userService;

    @PostMapping("/create/{role}")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO dto,@PathVariable("role") Role role, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body(Validation.getValidationErrors(result));
        }
        return ResponseEntity.ok(userService.createUser(role, dto));
    }

    @PutMapping("/update/{id}/{role}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @PathVariable("role") Role role){
        return ResponseEntity.ok(userService.updateRole(role, id));
    }
}
