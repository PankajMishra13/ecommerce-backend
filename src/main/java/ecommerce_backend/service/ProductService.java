package ecommerce_backend.service;

import ecommerce_backend.dto.ProductRequestDto;
import ecommerce_backend.dto.ProductResponseDto;

import java.util.List;

public interface ProductService {

    ProductResponseDto createProduct(ProductRequestDto requestDto);

    List<ProductResponseDto> getAllProducts();

    ProductResponseDto getProductById(Long id);

    ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto);

    void deleteProduct(Long id);
}