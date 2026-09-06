package ecommerce_backend.mapper;

import ecommerce_backend.dto.ProductImageRequestDto;
import ecommerce_backend.dto.ProductImageResponseDto;
import ecommerce_backend.entity.ProductImage;
import org.springframework.stereotype.Component;

@Component
public class ProductImageMapper {

    public ProductImage toEntity(ProductImageRequestDto dto){

        return ProductImage.builder()
                .imageUrl(dto.getImageUrl())
                .displayOrder(dto.getDisplayOrder())
                .isThumbnail(dto.getIsThumbnail())
                .build();
    }

    public ProductImageResponseDto toResponseDto(ProductImage productImage){

        Long productId = null;

        if(productImage.getProduct() !=null){

            productId = productImage.getProduct().getId();

        }

        return  ProductImageResponseDto.builder()
                .id(productImage.getId())
                .productId(productId)
                .imageUrl(productImage.getImageUrl())
                .displayOrder(productImage.getDisplayOrder())
                .isThumbnail(productImage.getIsThumbnail())
                .createdAt(productImage.getCreatedAt())
                .build();
    }
}
