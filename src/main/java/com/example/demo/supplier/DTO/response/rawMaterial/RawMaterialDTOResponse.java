package com.example.demo.supplier.DTO.response.rawMaterial;

import lombok.*;


import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RawMaterialDTOResponse {

    private String name;
    private String unit;
    private Integer stock;
    private Integer stockMin;
    private List<SupplierDTOResponseWithoutList> suppliers;
}
