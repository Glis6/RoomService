package com.glis.log.model;

import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Glis
 */
@Data
public class SavableThrowable {
    /**
     * The detailed message.
     */
    private final String detailMessage;

    /**
     * The {@link List} of stacktraces.
     */
    private final List<StackTraceElement> stackTrace;

    /**
     * @param throwable The {@link Throwable} to convert.
     */
    public SavableThrowable(final Throwable throwable) {
        this.detailMessage = throwable.getMessage();
        this.stackTrace = Arrays.stream(throwable.getStackTrace()).collect(Collectors.toList());
    }
}
