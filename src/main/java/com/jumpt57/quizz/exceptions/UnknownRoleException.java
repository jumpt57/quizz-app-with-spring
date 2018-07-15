package com.jumpt57.quizz.exceptions;

public class UnknownRoleException extends RuntimeException {

    public UnknownRoleException() {
        super();
    }

    public UnknownRoleException(String message) {
        super(message);
    }

    public UnknownRoleException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownRoleException(Throwable cause) {
        super(cause);
    }

    protected UnknownRoleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
