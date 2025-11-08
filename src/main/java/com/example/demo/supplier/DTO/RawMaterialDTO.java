package com.example.demo.supplier.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RawMaterialDTO {

    @NotEmpty(message = "then name is required")
    private String name;
    @NotEmpty(message = "then unit is required")
    private String unit;
    @NotNull(message = "then stock is required")
    private Integer stock;
    @NotNull(message = "then stock min is required")
    private Integer stockMin;
    private List<Long> supplierIds;
}
