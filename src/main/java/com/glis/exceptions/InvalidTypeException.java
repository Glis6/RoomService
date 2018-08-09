package com.glis.exceptions;

/**
 * @author Glis
 */
public class InvalidTypeException extends Exception {
    /**
     * {@inheritDoc}
     */
    public InvalidTypeException() {
    }

    /**
     * {@inheritDoc}
     */
    public InvalidTypeException(String message) {
        super(message);
    }
}
