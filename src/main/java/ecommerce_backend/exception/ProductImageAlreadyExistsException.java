package ecommerce_backend.exception;

public class ProductImageAlreadyExistsException extends RuntimeException{
    public ProductImageAlreadyExistsException(String message) {
        super(message);
    }
}
