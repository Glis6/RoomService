package com.glis.exceptions;

/**
 * @author Glis
 */
public class UnknownHandlerException extends Exception {
    /**
     * {@inheritDoc}
     */
    public UnknownHandlerException() {
    }

    /**
     * {@inheritDoc}
     */
    public UnknownHandlerException(String message) {
        super(message);
    }
}
