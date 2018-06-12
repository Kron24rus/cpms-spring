package com.fireway.cpms.exception;

public class BadRequestException extends Exception {
    public BadRequestException(String message) { super(message); }

    public BadRequestException(String message, Exception cause) { super(message, cause); }
}
