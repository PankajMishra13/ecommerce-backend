package ecommerce_backend.service.impl;

import ecommerce_backend.dto.ProductSpecificationRequestDto;
import ecommerce_backend.dto.ProductSpecificationResponseDto;
import ecommerce_backend.entity.Product;
import ecommerce_backend.entity.ProductSpecification;
import ecommerce_backend.exception.ProductNotFoundException;
import ecommerce_backend.exception.ProductSpecificationAlreadyExistsException;
import ecommerce_backend.exception.ProductSpecificationNotFoundException;
import ecommerce_backend.mapper.ProductSpecificationMapper;
import ecommerce_backend.repository.ProductRepository;
import ecommerce_backend.repository.ProductSpecificationRepository;
import ecommerce_backend.service.ProductSpecificationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductSpecificationServiceImpl implements ProductSpecificationService {

    private final ProductSpecificationRepository productSpecificationRepository;
    private final ProductSpecificationMapper productSpecificationMapper;
    private final ProductRepository productRepository;

    @Transactional
    @Override
    public ProductSpecificationResponseDto createProductSpecification(
            ProductSpecificationRequestDto request) {

        Product product = productRepository.findById(
                request.getProductId()
        ).orElseThrow(() ->
                new ProductNotFoundException("Product not found"));

        if (productSpecificationRepository
                .findByProductIdAndDisplayOrder(
                        request.getProductId(),
                        request.getDisplayOrder())
                .isPresent()) {

            throw new ProductSpecificationAlreadyExistsException(
                    "Specification already exists for this product and display order");
        }

        ProductSpecification productSpecification =
                productSpecificationMapper.toEntity(request);

        productSpecification.setProduct(product);

        ProductSpecification savedProductSpecification =
                productSpecificationRepository.save(productSpecification);

        return productSpecificationMapper.toResponseDto(
                savedProductSpecification);
    }

    @Override
    public List<ProductSpecificationResponseDto> getAllProductSpecifications() {

        List<ProductSpecification> productSpecifications =
                productSpecificationRepository.findAll();

        return productSpecifications.stream()
                .map(productSpecificationMapper::toResponseDto)
                .toList();
    }

    @Override
    public ProductSpecificationResponseDto getProductSpecificationById(Long id) {

        ProductSpecification productSpecification =
                productSpecificationRepository.findById(id)
                        .orElseThrow(() ->
                                new ProductSpecificationNotFoundException(
                                        "Product specification not found"));

        return productSpecificationMapper.toResponseDto(productSpecification);
    }

    @Transactional
    @Override
    public ProductSpecificationResponseDto updateProductSpecification(
            Long id,
            ProductSpecificationRequestDto request) {

        ProductSpecification productSpecification =
                productSpecificationRepository.findById(id)
                        .orElseThrow(() ->
                                new ProductSpecificationNotFoundException(
                                        "Product specification not found"));

        Product product = productRepository.findById(
                request.getProductId()
        ).orElseThrow(() ->
                new ProductNotFoundException("Product not found"));

        Optional<ProductSpecification> existingSpecification =
                productSpecificationRepository.findByProductIdAndDisplayOrder(
                        request.getProductId(),
                        request.getDisplayOrder());

        if (existingSpecification.isPresent()
                && !existingSpecification.get().getId().equals(id)) {

            throw new ProductSpecificationAlreadyExistsException(
                    "Specification already exists for this product and display order");
        }

        productSpecification.setProduct(product);
        productSpecification.setSpecificationName(
                request.getSpecificationName());
        productSpecification.setSpecificationValue(
                request.getSpecificationValue());
        productSpecification.setDisplayOrder(
                request.getDisplayOrder());

        ProductSpecification updatedProductSpecification =
                productSpecificationRepository.save(productSpecification);

        return productSpecificationMapper.toResponseDto(
                updatedProductSpecification);
    }

    @Override
    public void deleteProductSpecification(Long id) {

        ProductSpecification productSpecification =
                productSpecificationRepository.findById(id)
                        .orElseThrow(() ->
                                new ProductSpecificationNotFoundException(
                                        "Product specification not found"));

        productSpecificationRepository.delete(productSpecification);
    }
}
