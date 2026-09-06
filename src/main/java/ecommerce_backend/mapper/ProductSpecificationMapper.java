package ecommerce_backend.mapper;

import ecommerce_backend.dto.ProductSpecificationRequestDto;
import ecommerce_backend.dto.ProductSpecificationResponseDto;
import ecommerce_backend.entity.ProductSpecification;
import org.springframework.stereotype.Component;

@Component
public class ProductSpecificationMapper {

    public ProductSpecification toEntity(ProductSpecificationRequestDto dto) {

        return ProductSpecification.builder()
                .specificationName(dto.getSpecificationName())
                .specificationValue(dto.getSpecificationValue())
                .displayOrder(dto.getDisplayOrder())
                .build();
    }

    public ProductSpecificationResponseDto toResponseDto(
            ProductSpecification productSpecification) {

        Long productId = null;

        if (productSpecification.getProduct() != null) {
            productId = productSpecification.getProduct().getId();
        }

        return ProductSpecificationResponseDto.builder()
                .id(productSpecification.getId())
                .productId(productId)
                .specificationName(
                        productSpecification.getSpecificationName())
                .specificationValue(
                        productSpecification.getSpecificationValue())
                .displayOrder(productSpecification.getDisplayOrder())
                .createdAt(productSpecification.getCreatedAt())
                .build();
    }
}