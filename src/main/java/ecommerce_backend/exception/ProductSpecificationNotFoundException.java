package ecommerce_backend.exception;

public class ProductSpecificationNotFoundException extends RuntimeException{
    public ProductSpecificationNotFoundException(String message) {
        super(message);
    }
}
