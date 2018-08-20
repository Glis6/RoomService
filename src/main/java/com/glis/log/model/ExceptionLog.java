package com.glis.log.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Glis
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class ExceptionLog extends Log {
    /**
     * The exception that was thrown.
     */
    private final SavableThrowable throwable;

    /**
     * @serial Class that issued logging call
     */
    private final String sourceClassName;

    /**
     * @serial Method that issued logging call
     */
    private final String sourceMethodName;

    /**
     * @serial Non-localized raw message text
     */
    private final String message;

    /**
     * @serial Thread ID for thread that issued logging call.
     */
    private final int threadId;
}
