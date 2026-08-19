package ecommerce_backend.mapper;

import ecommerce_backend.dto.BrandRequestDto;
import ecommerce_backend.dto.BrandResponseDto;
import ecommerce_backend.entity.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {

    public Brand toEntity(BrandRequestDto dto){

        return Brand.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .websiteUrl(dto.getWebsiteUrl())
                .country(dto.getCountry())
                .isActive(dto.getIsActive())
                .build();

    }

    public BrandResponseDto toResponseDto(Brand brand){

        return BrandResponseDto.builder()
                .id(brand.getId())
                .name(brand.getName())
                .description(brand.getDescription())
                .websiteUrl(brand.getWebsiteUrl())
                .country(brand.getCountry())
                .isActive(brand.getIsActive())
                .createdAt(brand.getCreatedAt())
                .updatedAt(brand.getUpdatedAt())
                .build();
    }
}
