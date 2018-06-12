package com.fireway.cpms.exception;

public class NotAuthorizedException extends ForbiddenException {
    public NotAuthorizedException() {
        super("Not authorized");
    }
}
