package com.example.demo.supplier.controller;

import com.example.demo.ApiResponse.ResponseMessage;
import com.example.demo.supplier.DTO.RawMaterialDTO;
import com.example.demo.supplier.DTO.response.rawMaterial.RawMaterialDTOResponse;
import com.example.demo.supplier.entity.enume.SupplyOrderStatus;
import com.example.demo.supplier.service.IRawMaterialService;
import com.example.demo.utils.Validation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "raw material crud", description = "manage raw material")
@RequestMapping("/api/raw-material")
public class RawMaterialController {
    private final IRawMaterialService rawMaterialService;

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Valid @RequestBody RawMaterialDTO rawMaterialDTO, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body(Validation.getValidationErrors(result));
        }
        return ResponseEntity.ok(rawMaterialService.create(rawMaterialDTO));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(rawMaterialService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<RawMaterialDTOResponse>> getAll(){
        return ResponseEntity.ok(rawMaterialService.getAll());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable("id") Long id, @RequestBody  RawMaterialDTO rawMaterialDTO, BindingResult result ){
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Validation.getValidationErrors(result));
        }
        return ResponseEntity.ok(rawMaterialService.update(rawMaterialDTO , id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseMessage> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(rawMaterialService.delete(id));
    }

    @GetMapping("/filter/stock/less/{stock}")
    public ResponseEntity<List<RawMaterialDTOResponse>> filterByStockLessThenOrEqual(@PathVariable("stock") Integer stock){
        List<RawMaterialDTOResponse> rawMaterialDTOResponses = rawMaterialService.getByLessThenOrEqualStock(stock);
        return ResponseEntity.ok(rawMaterialDTOResponses);
    }

    @GetMapping("/filter/stock/greater/{stock}")
    public ResponseEntity<List<RawMaterialDTOResponse>> filterByStockGreaterThenOrEqual(@PathVariable("stock") Integer stock){
        List<RawMaterialDTOResponse> rawMaterialDTOResponses = rawMaterialService.getByGreaterThenOrEqualStock(stock);
        return ResponseEntity.ok(rawMaterialDTOResponses);
    }




}
