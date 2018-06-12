package com.fireway.cpms.exception;

public class DataAccessException extends Exception {
    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Exception cause) {
        super(message, cause);
    }
}
