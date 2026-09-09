package ecommerce_backend.exception;

import ecommerce_backend.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFound(
            ResourceNotFoundException ex) {

        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDto> handleConflict(
            ConflictException ex) {

        return buildErrorResponse(
                HttpStatus.CONFLICT,
                ex.getMessage()
        );
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidCredentials(
            InvalidCredentialsException ex){

        return buildErrorResponse(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage());
          }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidRefreshToken(
            InvalidRefreshTokenException ex) {

        return buildErrorResponse(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ErrorResponseDto> handleUnauthorizedAccess(
            UnauthorizedAccessException ex) {

        return buildErrorResponse(
                HttpStatus.FORBIDDEN,
                ex.getMessage());
    }

    @ExceptionHandler(InvalidCategoryException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidCategory(
            InvalidCategoryException ex) {

        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage());
    }

    @ExceptionHandler(EmptyCartException.class)
    public ResponseEntity<ErrorResponseDto> handleEmptyCart(
            EmptyCartException ex) {

        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage());
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponseDto> handleInsufficientStock(
            InsufficientStockException ex) {

        return buildErrorResponse(
                HttpStatus.CONFLICT,
                ex.getMessage());
    }

    @ExceptionHandler(OrderCancellationException.class)
    public ResponseEntity<ErrorResponseDto> handleOrderCancellation(
            OrderCancellationException ex) {

        return buildErrorResponse(
                HttpStatus.CONFLICT,
                ex.getMessage());
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ErrorResponseDto> handlePaymentException(
            PaymentException ex) {

        return buildErrorResponse(
                HttpStatus.CONFLICT,
                ex.getMessage());
    }

    @ExceptionHandler(ReviewException.class)
    public ResponseEntity<ErrorResponseDto> handleReviewException(
            ReviewException ex) {

        return buildErrorResponse(
                HttpStatus.CONFLICT,
                ex.getMessage());
    }

    @ExceptionHandler(OrderStatusException.class)
    public ResponseEntity<ErrorResponseDto> handleOrderStatusException(
            OrderStatusException ex) {

        return buildErrorResponse(
                HttpStatus.CONFLICT,
                ex.getMessage());
    }

    @ExceptionHandler(CouponException.class)
    public ResponseEntity<ErrorResponseDto> handleCouponException(
            CouponException ex) {

        return buildErrorResponse(
                HttpStatus.CONFLICT,
                ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto("Validation failed", errors));
    }

    private ResponseEntity<ErrorResponseDto> buildErrorResponse(
            HttpStatus status, String message) {

        return ResponseEntity
                .status(status)
                .body(new ErrorResponseDto(message, null));
    }

}




