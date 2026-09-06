package ecommerce_backend.service;

import ecommerce_backend.dto.ProductImageRequestDto;
import ecommerce_backend.dto.ProductImageResponseDto;

import java.util.List;

public interface ProductImageService {

    ProductImageResponseDto createProductImage(ProductImageRequestDto request);

    List<ProductImageResponseDto> getAllProductImages();

    ProductImageResponseDto getProductImageById(Long id);

    ProductImageResponseDto updateProductImage(Long id, ProductImageRequestDto request);

    void deleteProductImage(Long id);

}
