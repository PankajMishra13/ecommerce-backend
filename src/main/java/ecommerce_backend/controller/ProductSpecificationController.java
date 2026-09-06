package ecommerce_backend.controller;

import ecommerce_backend.dto.ProductSpecificationRequestDto;
import ecommerce_backend.dto.ProductSpecificationResponseDto;
import ecommerce_backend.service.ProductSpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-specifications")
@RequiredArgsConstructor
public class ProductSpecificationController {

    private final ProductSpecificationService productSpecificationService;

    @PostMapping
    public ProductSpecificationResponseDto createProductSpecification(
            @RequestBody ProductSpecificationRequestDto request) {

        return productSpecificationService
                .createProductSpecification(request);
    }

    @GetMapping
    public List<ProductSpecificationResponseDto>
    getAllProductSpecifications() {

        return productSpecificationService
                .getAllProductSpecifications();
    }

    @GetMapping("/{id}")
    public ProductSpecificationResponseDto getProductSpecificationById(
            @PathVariable Long id) {

        return productSpecificationService
                .getProductSpecificationById(id);
    }

    @PutMapping("/{id}")
    public ProductSpecificationResponseDto updateProductSpecification(
            @PathVariable Long id,
            @RequestBody ProductSpecificationRequestDto request) {

        return productSpecificationService
                .updateProductSpecification(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteProductSpecification(
            @PathVariable Long id) {

        productSpecificationService
                .deleteProductSpecification(id);
    }
}