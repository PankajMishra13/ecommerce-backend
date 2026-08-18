package ecommerce_backend.service.impl;

import ecommerce_backend.dto.BrandRequestDto;
import ecommerce_backend.dto.BrandResponseDto;
import ecommerce_backend.entity.Brand;
import ecommerce_backend.exception.BrandNotFoundException;
import ecommerce_backend.mapper.BrandMapper;
import ecommerce_backend.repository.BrandRepository;
import ecommerce_backend.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Override
    public BrandResponseDto createBrand(BrandRequestDto requestDto) {

        Brand brand = brandMapper.toEntity(requestDto);

        Brand savedBrand = brandRepository.save(brand);

        return brandMapper.toResponseDto(savedBrand);    }

    @Override
    public List<BrandResponseDto> getAllBrands() {

        List<Brand> brands = brandRepository.findAll();

        return brands.stream()
                .map(brandMapper::toResponseDto)
                .toList();
    }

    @Override
    public BrandResponseDto getBrandById(Long id) {

        Brand brand = brandRepository.findById(id)
                .orElseThrow(() ->
                        new BrandNotFoundException("Brand not found with id: " + id
                        )
                );

        return brandMapper.toResponseDto(brand);
    }

    @Override
    public BrandResponseDto updateBrand(Long id, BrandRequestDto requestDto) {

        Brand brand = brandRepository.findById(id)
                .orElseThrow(() ->
                        new BrandNotFoundException("Brand not found with id: " + id
                        )
                );

        brand.setName(requestDto.getName());
        brand.setDescription(requestDto.getDescription());
        brand.setWebsiteUrl(requestDto.getWebsiteUrl());
        brand.setCountry(requestDto.getCountry());
        brand.setIsActive(requestDto.getIsActive());

        Brand updatedBrand = brandRepository.save(brand);

        return brandMapper.toResponseDto(updatedBrand);
    }

    @Override
    public void deleteBrand(Long id) {

        Brand brand = brandRepository.findById(id)
                .orElseThrow(() ->
                        new BrandNotFoundException(
                                "Brand not found with id: " + id
                        )
                );

        brandRepository.delete(brand);


    }
}
