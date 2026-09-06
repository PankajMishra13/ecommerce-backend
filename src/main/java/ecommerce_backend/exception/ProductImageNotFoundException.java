package ecommerce_backend.exception;

public class ProductImageNotFoundException extends RuntimeException{
    public ProductImageNotFoundException(String message) {
        super(message);
    }
}
