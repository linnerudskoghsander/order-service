package app.order.config;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}

