package com.epam.redkin.railway.model.exception;

public class IncorrectDataException extends ServiceException {
    public IncorrectDataException() {
        super("400");
    }

    public IncorrectDataException(String message) {
        super("400", message);
    }

    public IncorrectDataException(String message, Throwable cause) {
        super("400", message, cause);
    }
}