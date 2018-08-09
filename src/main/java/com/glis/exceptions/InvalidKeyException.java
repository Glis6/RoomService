package com.glis.exceptions;

/**
 * @author Glis
 */
public class InvalidKeyException extends RuntimeException {
    /**
     * {@inheritDoc}
     */
    public InvalidKeyException() {
    }

    /**
     * {@inheritDoc}
     */
    public InvalidKeyException(String message) {
        super(message);
    }
}
