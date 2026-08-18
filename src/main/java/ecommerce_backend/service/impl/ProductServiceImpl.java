package ecommerce_backend.service.impl;

import ecommerce_backend.dto.ProductRequestDto;
import ecommerce_backend.dto.ProductResponseDto;
import ecommerce_backend.entity.Brand;
import ecommerce_backend.entity.Category;
import ecommerce_backend.entity.Product;
import ecommerce_backend.exception.BrandNotFoundException;
import ecommerce_backend.exception.CategoryNotFoundException;
import ecommerce_backend.exception.ProductNotFoundException;
import ecommerce_backend.mapper.ProductMapper;
import ecommerce_backend.repository.BrandRepository;
import ecommerce_backend.repository.CategoryRepository;
import ecommerce_backend.repository.ProductRepository;
import ecommerce_backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {

        Product product = productMapper.toEntity(requestDto);

        Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() ->
                        new CategoryNotFoundException(
                                "Category not found with id: "
                                        + requestDto.getCategoryId()
                        )
                );

        Brand brand = brandRepository.findById(requestDto.getBrandId())
                .orElseThrow(() ->
                        new BrandNotFoundException(
                                "Brand not found with id: "
                                        + requestDto.getBrandId()
                        )
                );

        product.setCategory(category);
        product.setBrand(brand);

        product.setIsDeleted(false);

        Product savedProduct = productRepository.save(product);

        return productMapper.toResponseDto(savedProduct);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {

        List<Product> products = productRepository.findByIsDeletedFalse();

        return products.stream()
                .map(productMapper::toResponseDto)
                .toList();
    }

    @Override
    public ProductResponseDto getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product not found with id: " + id
                        )
                );

        if (Boolean.TRUE.equals(product.getIsDeleted())) {
            throw new ProductNotFoundException(
                    "Product not found with id: " + id
            );
        }

        return productMapper.toResponseDto(product);

    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product not found with id: " + id
                        )
                );

        if (Boolean.TRUE.equals(product.getIsDeleted())) {
            throw new ProductNotFoundException(
                    "Product not found with id: " + id
            );
        }

        Category category = categoryRepository.findById(
                requestDto.getCategoryId()
        ).orElseThrow(() ->
                new CategoryNotFoundException(
                        "Category not found with id: "
                                + requestDto.getCategoryId()
                )
        );

        Brand brand = brandRepository.findById(
                requestDto.getBrandId()
        ).orElseThrow(() ->
                new BrandNotFoundException(
                        "Brand not found with id: "
                                + requestDto.getBrandId()
                )
        );

        product.setCategory(category);
        product.setBrand(brand);

        product.setName(requestDto.getName());
        product.setShortDescription(requestDto.getShortDescription());
        product.setDescription(requestDto.getDescription());
        product.setSku(requestDto.getSku());
        product.setMrp(requestDto.getMrp());
        product.setSellingPrice(requestDto.getSellingPrice());
        product.setStatus(requestDto.getStatus());

        Product updatedProduct = productRepository.save(product);

        return productMapper.toResponseDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product not found with id: " + id
                        )
                );

        if (Boolean.TRUE.equals(product.getIsDeleted())) {
            throw new ProductNotFoundException(
                    "Product not found with id: " + id
            );
        }

        product.setIsDeleted(true);

        productRepository.save(product);

    }
}