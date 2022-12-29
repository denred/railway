package com.epam.redkin.model.exception;

public class UserAlreadyExistException extends ServiceException {
    public UserAlreadyExistException() {
        super("400");
    }

    public UserAlreadyExistException(String message) {
        super("400", message);
    }

    public UserAlreadyExistException(String message, Throwable cause) {
        super("400", message, cause);
    }
}