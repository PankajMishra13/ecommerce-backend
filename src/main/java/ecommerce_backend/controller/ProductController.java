package ecommerce_backend.controller;

import ecommerce_backend.dto.ProductRequestDto;
import ecommerce_backend.dto.ProductResponseDto;
import ecommerce_backend.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductResponseDto createProduct(
            @Valid @RequestBody ProductRequestDto requestDto) {

        return productService.createProduct(requestDto);
    }

    @GetMapping
    public List<ProductResponseDto> getAllProducts() {

        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponseDto getProductById(@PathVariable Long id) {

        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public ProductResponseDto updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDto requestDto) {

        return productService.updateProduct(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);
    }
}