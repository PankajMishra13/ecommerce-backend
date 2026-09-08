package ecommerce_backend.service;

import ecommerce_backend.dto.ReviewRequestDto;
import ecommerce_backend.dto.ReviewResponseDto;

import java.util.List;

public interface ReviewService {

    ReviewResponseDto createReview(ReviewRequestDto request);

    List<ReviewResponseDto> getProductReviews(Long productId);

    ReviewResponseDto getReviewById(Long reviewId);

    ReviewResponseDto updateReview(Long reviewId, ReviewRequestDto request);

    void deleteReview(Long reviewId);

    ReviewResponseDto approveReview(Long reviewId);
}