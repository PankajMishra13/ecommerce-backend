package ecommerce_backend.exception;

public class InvalidCredentialsException extends RuntimeException{

    public InvalidCredentialsException(String msg){
        super(msg);
    }
}
