package com.example.demo.supplier.DTO.response.rawMaterial;


import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDTOResponseWithoutList {
    private String firstName;

    private String lastName;

    private String contact;

    private Double rating;

    private Integer leadTime;

}
