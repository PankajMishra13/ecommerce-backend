package ecommerce_backend.mapper;

import ecommerce_backend.dto.ReviewResponseDto;
import ecommerce_backend.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ReviewResponseDto toResponseDto(Review review) {

        Long productId = null;
        Long userId = null;

        if (review.getProduct() != null) {
            productId = review.getProduct().getId();
        }

        if (review.getUser() != null) {
            userId = review.getUser().getId();
        }

        return ReviewResponseDto.builder()
                .id(review.getId())
                .productId(productId)
                .userId(userId)
                .rating(review.getRating())
                .reviewTitle(review.getReviewTitle())
                .reviewComment(review.getReviewComment())
                .isApproved(review.getIsApproved())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}