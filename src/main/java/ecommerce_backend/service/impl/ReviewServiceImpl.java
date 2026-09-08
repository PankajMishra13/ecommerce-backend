package ecommerce_backend.service.impl;

import ecommerce_backend.dto.ReviewRequestDto;
import ecommerce_backend.dto.ReviewResponseDto;
import ecommerce_backend.entity.Product;
import ecommerce_backend.entity.Review;
import ecommerce_backend.entity.User;
import ecommerce_backend.exception.ProductNotFoundException;
import ecommerce_backend.exception.ReviewException;
import ecommerce_backend.exception.UnauthorizedAccessException;
import ecommerce_backend.mapper.ReviewMapper;
import ecommerce_backend.repository.OrderItemRepository;
import ecommerce_backend.repository.ProductRepository;
import ecommerce_backend.repository.ReviewRepository;
import ecommerce_backend.repository.UserRepository;
import ecommerce_backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;
    private final OrderItemRepository orderItemRepository;

    @Override
    public ReviewResponseDto createReview(ReviewRequestDto request) {

        User user = getCurrentUser();

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found"));

        Optional<Review> existingReview =
                reviewRepository.findByUserIdAndProductId(
                        user.getId(),
                        product.getId()
                );

        if (existingReview.isPresent()) {
            throw new ReviewException(
                    "You have already reviewed this product"
            );
        }

        orderItemRepository.findDeliveredOrderItem(
                user.getId(),
                product.getId()
        ).orElseThrow(() ->
                new ReviewException(
                        "You can review a product only after it is delivered"
                )
        );

        Review review = Review.builder()
                .product(product)
                .user(user)
                .rating(request.getRating())
                .reviewTitle(request.getReviewTitle())
                .reviewComment(request.getReviewComment())
                .isApproved(false)
                .build();

        review = reviewRepository.save(review);

        return reviewMapper.toResponseDto(review);
    }

    @Override
    public List<ReviewResponseDto> getProductReviews(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found"));

        List<Review> reviews =
                reviewRepository.findByProductIdAndIsApprovedTrue(product.getId());

        return reviews.stream()
                .map(reviewMapper::toResponseDto)
                .toList();
    }

    @Override
    public ReviewResponseDto getReviewById(Long reviewId) {

        User user = getCurrentUser();

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() ->
                        new ReviewException("Review not found"));

        if (!review.getIsApproved()
                && !review.getUser().getId().equals(user.getId())) {

            throw new UnauthorizedAccessException("Unauthorized access");
        }

        return reviewMapper.toResponseDto(review);
    }

    @Override
    public ReviewResponseDto updateReview(Long reviewId, ReviewRequestDto request) {

        User user = getCurrentUser();

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() ->
                        new ReviewException("Review not found"));

        if (!review.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException("Unauthorized access");
        }

        review.setRating(request.getRating());
        review.setReviewTitle(request.getReviewTitle());
        review.setReviewComment(request.getReviewComment());
        review.setIsApproved(false);

        review = reviewRepository.save(review);

        return reviewMapper.toResponseDto(review);
    }

    @Override
    public void deleteReview(Long reviewId) {

        User user = getCurrentUser();

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() ->
                        new ReviewException("Review not found"));

        if (!review.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException("Unauthorized access");
        }

        reviewRepository.delete(review);
    }

    @Override
    public ReviewResponseDto approveReview(Long reviewId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() ->
                        new ReviewException("Review not found"));

        review.setIsApproved(true);

        review = reviewRepository.save(review);

        return reviewMapper.toResponseDto(review);
    }

    private User getCurrentUser() {

        String email =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UnauthorizedAccessException(
                                "Unauthorized access"));
    }

}