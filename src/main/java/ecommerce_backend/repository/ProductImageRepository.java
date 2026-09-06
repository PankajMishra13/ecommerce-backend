package ecommerce_backend.repository;

import ecommerce_backend.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    Optional<ProductImage> findByProductIdAndDisplayOrder(
            Long productId,
            Integer displayOrder
    );

    Optional<ProductImage> findByProductIdAndIsThumbnailTrue(
            Long productId
    );
}
