package ecommerce_backend.repository;

import ecommerce_backend.entity.Product;
import ecommerce_backend.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByIsDeletedFalse();

    Optional<Product> findByIdAndIsDeletedFalseAndStatus(
            Long id,
            ProductStatus status
    );

    boolean existsByCategoryId(Long categoryId);
    boolean existsByBrandId(Long brandId);
}
