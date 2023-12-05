package ru.clevertec.exception;

public class ConcurrencyException extends ApplicationException {

    public ConcurrencyException() {
        super();
    }

    public ConcurrencyException(String message) {
        super(message);
    }

    public ConcurrencyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConcurrencyException(Throwable cause) {
        super(cause);
    }
}
