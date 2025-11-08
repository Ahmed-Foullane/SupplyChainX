package com.example.demo.supplier.DTO.response.supplier;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RawMaterialDTOResponseWithoutList {
    private String name;
    private String unit;
    private Integer stock;
    private Integer stockMin;
}
