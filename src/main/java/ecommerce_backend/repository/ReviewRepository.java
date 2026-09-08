package ecommerce_backend.repository;

import ecommerce_backend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByUserIdAndProductId(Long userId, Long productId);

    List<Review> findByProductIdAndIsApprovedTrue(Long productId);
}