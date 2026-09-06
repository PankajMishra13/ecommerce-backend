package ecommerce_backend.controller;
import ecommerce_backend.dto.ProductImageRequestDto;
import ecommerce_backend.dto.ProductImageResponseDto;
import ecommerce_backend.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-images")
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImageService productImageService;

    @PostMapping
    public ProductImageResponseDto createProductImage(
            @RequestBody ProductImageRequestDto request) {

        return productImageService.createProductImage(request);
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
    public void deleteProductImage(@PathVariable Long id) {

        productImageService.deleteProductImage(id);
    }
}
