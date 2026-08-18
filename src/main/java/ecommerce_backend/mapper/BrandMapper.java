package ecommerce_backend.mapper;

import ecommerce_backend.dto.BrandRequestDto;
import ecommerce_backend.dto.BrandResponseDto;
import ecommerce_backend.entity.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {

    public Brand toEntity(BrandRequestDto dto){

        Brand brand = new Brand();

        brand.setName(dto.getName());
        brand.setDescription(dto.getDescription());
        brand.setWebsiteUrl(dto.getWebsiteUrl());
        brand.setCountry(dto.getCountry());
        brand.setIsActive(dto.getIsActive());

        return brand;

    }

    public BrandResponseDto toResponseDto(Brand brand){

        BrandResponseDto responseDto = new BrandResponseDto();

        responseDto.setId(brand.getId());
        responseDto.setName(brand.getName());
        responseDto.setDescription(brand.getDescription());
        responseDto.setWebsiteUrl(brand.getWebsiteUrl());
        responseDto.setCountry(brand.getCountry());
        responseDto.setIsActive(brand.getIsActive());
        responseDto.setCreatedAt(brand.getCreatedAt());
        responseDto.setUpdatedAt(brand.getUpdatedAt());

        return responseDto;

    }
}
