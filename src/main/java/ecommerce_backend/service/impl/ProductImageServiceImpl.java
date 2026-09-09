package ecommerce_backend.service.impl;

import ecommerce_backend.dto.ProductImageRequestDto;
import ecommerce_backend.dto.ProductImageResponseDto;
import ecommerce_backend.entity.Product;
import ecommerce_backend.entity.ProductImage;
import ecommerce_backend.exception.ConflictException;
import ecommerce_backend.exception.ResourceNotFoundException;
import ecommerce_backend.mapper.ProductImageMapper;
import ecommerce_backend.repository.ProductImageRepository;
import ecommerce_backend.repository.ProductRepository;
import ecommerce_backend.service.ProductImageService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;
    private final ProductImageMapper productImageMapper;
    private final ProductRepository productRepository;

    @Override
    public List<ProductImageResponseDto> getAllProductImages() {

        List<ProductImage> productImages =
                productImageRepository.findAll();

        return productImages.stream()
                .map(productImageMapper::toResponseDto)
                .toList();
    }

    @Override
    public ProductImageResponseDto getProductImageById(Long id) {

        ProductImage productImage = productImageRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product image not found"));

        return productImageMapper.toResponseDto(productImage);
    }

    @Transactional
    @Override
    public ProductImageResponseDto updateProductImage(Long id, ProductImageRequestDto request) {

        ProductImage productImage = productImageRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product image not found"));

        Product product = productRepository.findById(
                request.getProductId()
        ).orElseThrow(() ->
                new ResourceNotFoundException("Product not found"));

        Optional<ProductImage> existingImage =
                productImageRepository.findByProductIdAndDisplayOrder(
                        request.getProductId(),
                        request.getDisplayOrder());

        if (existingImage.isPresent()
                && !existingImage.get().getId().equals(id)) {

            throw new ConflictException(
                    "Image already exists for this product and display order");
        }

        if (Boolean.TRUE.equals(request.getIsThumbnail())) {

            Optional<ProductImage> existingThumbnail =
                    productImageRepository.findByProductIdAndIsThumbnailTrue(
                            request.getProductId());

            existingThumbnail.ifPresent(image -> {

                if (!image.getId().equals(id)) {
                    image.setIsThumbnail(false);
                    productImageRepository.save(image);
                }
            });
        }

        productImage.setProduct(product);
        productImage.setImageUrl(request.getImageUrl());
        productImage.setDisplayOrder(request.getDisplayOrder());
        productImage.setIsThumbnail(request.getIsThumbnail());

        ProductImage updatedProductImage =
                productImageRepository.save(productImage);

        return productImageMapper.toResponseDto(updatedProductImage);
    }

    @Override
    @Transactional
    public void deleteProductImage(Long id) {

            ProductImage productImage = productImageRepository.findById(id)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Product image not found"));

            productImageRepository.delete(productImage);
        }

    @Transactional
    @Override
    public ProductImageResponseDto createProductImage(ProductImageRequestDto request) {
        Product product = productRepository.findById(
                request.getProductId()
        ).orElseThrow(() ->
                new ResourceNotFoundException("Product not found"));

        if (productImageRepository
                .findByProductIdAndDisplayOrder(
                        request.getProductId(),
                        request.getDisplayOrder())
                .isPresent()) {

            throw new ConflictException(
                    "Image already exists for this product and display order");
        }

        ProductImage productImage = productImageMapper.toEntity(request);

        if (Boolean.TRUE.equals(request.getIsThumbnail())) {
            Optional<ProductImage> existingThumbnail =
                    productImageRepository.findByProductIdAndIsThumbnailTrue(
                            request.getProductId());

            existingThumbnail.ifPresent(image -> {
                image.setIsThumbnail(false);
                productImageRepository.save(image);
            });
        }
        productImage.setProduct(product);

        ProductImage savedProductImage =
                productImageRepository.save(productImage);

        return productImageMapper.toResponseDto(savedProductImage);

    }

    }

