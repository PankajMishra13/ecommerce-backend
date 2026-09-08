package ecommerce_backend.controller;

import ecommerce_backend.dto.ReviewRequestDto;
import ecommerce_backend.dto.ReviewResponseDto;
import ecommerce_backend.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(
            @Valid @RequestBody ReviewRequestDto request) {

        ReviewResponseDto response =
                reviewService.createReview(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewResponseDto>> getProductReviews(
            @PathVariable Long productId) {

        List<ReviewResponseDto> reviews =
                reviewService.getProductReviews(productId);

        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> getReviewById(
            @PathVariable Long reviewId) {

        ReviewResponseDto response =
                reviewService.getReviewById(reviewId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> updateReview(
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewRequestDto request) {

        ReviewResponseDto response =
                reviewService.updateReview(reviewId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long reviewId) {

        reviewService.deleteReview(reviewId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{reviewId}/approve")
    public ResponseEntity<ReviewResponseDto> approveReview(
            @PathVariable Long reviewId) {

        ReviewResponseDto response =
                reviewService.approveReview(reviewId);

        return ResponseEntity.ok(response);
    }

}