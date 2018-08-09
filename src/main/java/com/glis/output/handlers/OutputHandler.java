package com.glis.output.handlers;

import com.glis.output.MessageSender;
import lombok.NonNull;

/**
 * @author Glis
 */
public interface OutputHandler {
    /**
     * @param identifier The identifier to check.
     * @return Whether or not the handler can handle the identifier.
     */
    boolean canHandle(String identifier);

    /**
     * @param messageSender The {@link MessageSender} that sent the request.
     */
    void handle(final @NonNull MessageSender messageSender) throws Exception;
}
