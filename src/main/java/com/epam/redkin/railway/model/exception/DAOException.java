package com.epam.redkin.railway.model.exception;

public class DAOException extends ServiceException {

    public DAOException() {
        super("500");
    }

    public DAOException(String message) {
        super("500", message);
    }

    public DAOException(String message, Throwable cause) {
        super("500", message, cause);
    }
}
