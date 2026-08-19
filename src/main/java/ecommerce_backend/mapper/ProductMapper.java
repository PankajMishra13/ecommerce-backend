package ecommerce_backend.mapper;

import ecommerce_backend.dto.ProductRequestDto;
import ecommerce_backend.dto.ProductResponseDto;
import ecommerce_backend.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequestDto dto) {

        return Product.builder()
                .name(dto.getName())
                .shortDescription(dto.getShortDescription())
                .description(dto.getDescription())
                .sku(dto.getSku())
                .mrp(dto.getMrp())
                .sellingPrice(dto.getSellingPrice())
                .status(dto.getStatus())
                .build();
    }

    public ProductResponseDto toResponseDto(Product product) {

        Long categoryId = null;
        Long brandId = null;

        if (product.getCategory() != null) {
            categoryId = product.getCategory().getId();
        }

        if (product.getBrand() != null) {
            brandId = product.getBrand().getId();
        }

        return ProductResponseDto.builder()
                .id(product.getId())
                .categoryId(categoryId)
                .brandId(brandId)
                .name(product.getName())
                .shortDescription(product.getShortDescription())
                .description(product.getDescription())
                .sku(product.getSku())
                .mrp(product.getMrp())
                .sellingPrice(product.getSellingPrice())
                .status(product.getStatus())
                .isDeleted(product.getIsDeleted())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();

    }
}