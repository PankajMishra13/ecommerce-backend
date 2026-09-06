package ecommerce_backend.repository;

import ecommerce_backend.entity.ProductSpecification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductSpecificationRepository
        extends JpaRepository<ProductSpecification, Long> {

    Optional<ProductSpecification> findByProductIdAndDisplayOrder(
            Long productId,
            Integer displayOrder
    );
}