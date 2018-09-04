package com.glis.exceptions;

/**
 * @author Glis
 */
public class UnknownDecoderException extends RuntimeException {
    /**
     * {@inheritDoc}
     */
    public UnknownDecoderException() {
    }

    /**
     * {@inheritDoc}
     */
    public UnknownDecoderException(String message) {
        super(message);
    }
}
