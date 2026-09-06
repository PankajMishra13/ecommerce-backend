package ecommerce_backend.exception;

public class ProductSpecificationAlreadyExistsException extends RuntimeException{
    public ProductSpecificationAlreadyExistsException(String message) {
        super(message);
    }
}
