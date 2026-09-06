package ecommerce_backend.service;

import ecommerce_backend.dto.ProductSpecificationRequestDto;
import ecommerce_backend.dto.ProductSpecificationResponseDto;

import java.util.List;

public interface ProductSpecificationService {

    ProductSpecificationResponseDto createProductSpecification(
            ProductSpecificationRequestDto request);

    List<ProductSpecificationResponseDto> getAllProductSpecifications();

    ProductSpecificationResponseDto getProductSpecificationById(Long id);

    ProductSpecificationResponseDto updateProductSpecification(
            Long id,
            ProductSpecificationRequestDto request);

    void deleteProductSpecification(Long id);
}