package com.epam.redkin.model.exception;

public class UnauthorizedException extends ServiceException {

    public UnauthorizedException(String message) {
        super("401", message);
    }

    public UnauthorizedException() {
        super("401");
    }

    public UnauthorizedException(String message, Throwable cause) {
        super("401", message, cause);
    }
}
