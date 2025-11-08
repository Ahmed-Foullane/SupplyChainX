package com.example.demo.supplier.DTO.response.supplier;

import lombok.*;


import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDTOResponse {
    private String firstName;
    private String lastName;
    private String contact;
    private Double rating;
    private Integer leadTime;
    private List<RawMaterialDTOResponseWithoutList> rawMaterials;
}
