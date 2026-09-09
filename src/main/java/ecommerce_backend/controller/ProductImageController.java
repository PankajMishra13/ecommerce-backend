package ecommerce_backend.controller;
import ecommerce_backend.dto.ProductImageRequestDto;
import ecommerce_backend.dto.ProductImageResponseDto;
import ecommerce_backend.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-images")
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImageService productImageService;

    @PostMapping
    public ResponseEntity<ProductImageResponseDto> createProductImage(
            @RequestBody ProductImageRequestDto request) {

        ProductImageResponseDto response =
                productImageService.createProductImage(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public List<ProductImageResponseDto> getAllProductImages() {

        return productImageService.getAllProductImages();
    }

    @GetMapping("/{id}")
    public ProductImageResponseDto getProductImageById(
            @PathVariable Long id) {

        return productImageService.getProductImageById(id);
    }

    @PutMapping("/{id}")
    public ProductImageResponseDto updateProductImage(
            @PathVariable Long id,
            @RequestBody ProductImageRequestDto request) {

        return productImageService.updateProductImage(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductImage(@PathVariable Long id) {

        productImageService.deleteProductImage(id);

        return ResponseEntity.noContent().build();
    }
}
