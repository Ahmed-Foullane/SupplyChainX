package com.example.demo.supplier.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class SupplierDTO {
    @NotBlank(message = "the first name is required")
    private String firstName;

    @NotBlank(message = "the last name is required")
    private String lastName;

    private String contact;

    @NotNull(message = "the rating is required")
    private Double rating;

    @NotNull(message = "the average date is required")
    private Integer leadTime;
}
