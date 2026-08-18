package ecommerce_backend.service;

import ecommerce_backend.dto.BrandRequestDto;
import ecommerce_backend.dto.BrandResponseDto;

import java.util.List;

public interface BrandService {

    BrandResponseDto createBrand(BrandRequestDto requestDto);

    List<BrandResponseDto> getAllBrands();

    BrandResponseDto getBrandById(Long id);

    BrandResponseDto updateBrand(Long id, BrandRequestDto requestDto);

    void deleteBrand(Long id);
}
