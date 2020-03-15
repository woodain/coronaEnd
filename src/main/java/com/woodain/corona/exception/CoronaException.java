package com.woodain.corona.exception;

public class CoronaException extends RuntimeException {

    public CoronaException(String message) {
        super(message);
    }

    public CoronaException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoronaException(Throwable cause) {
            super(cause);
    }

    public CoronaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
