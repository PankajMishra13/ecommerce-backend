package ecommerce_backend.mapper;

import ecommerce_backend.dto.ProductRequestDto;
import ecommerce_backend.dto.ProductResponseDto;
import ecommerce_backend.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequestDto dto) {

        Product product = new Product();

        product.setName(dto.getName());
        product.setShortDescription(dto.getShortDescription());
        product.setDescription(dto.getDescription());
        product.setSku(dto.getSku());
        product.setMrp(dto.getMrp());
        product.setSellingPrice(dto.getSellingPrice());
        product.setStatus(dto.getStatus());

        return product;
    }

    public ProductResponseDto toResponseDto(Product product) {

        ProductResponseDto responseDto = new ProductResponseDto();

        responseDto.setId(product.getId());

        if (product.getCategory() != null) {
            responseDto.setCategoryId(product.getCategory().getId());
        }

        if (product.getBrand() != null) {
            responseDto.setBrandId(product.getBrand().getId());
        }

        responseDto.setName(product.getName());
        responseDto.setShortDescription(product.getShortDescription());
        responseDto.setDescription(product.getDescription());
        responseDto.setSku(product.getSku());
        responseDto.setMrp(product.getMrp());
        responseDto.setSellingPrice(product.getSellingPrice());
        responseDto.setStatus(product.getStatus());
        responseDto.setIsDeleted(product.getIsDeleted());
        responseDto.setCreatedAt(product.getCreatedAt());
        responseDto.setUpdatedAt(product.getUpdatedAt());

        return responseDto;
    }
}