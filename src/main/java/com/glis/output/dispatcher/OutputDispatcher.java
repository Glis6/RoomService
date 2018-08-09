package com.glis.output.dispatcher;

import com.glis.exceptions.UnknownHandlerException;
import com.glis.output.MessageSender;
import lombok.NonNull;

/**
 * @author Glis
 */
public interface OutputDispatcher {
    /**
     * @param identifier The identifier that is being dispatched.
     * @param messageSender The {@link MessageSender} that is requesting the linking.
     */
    void dispatchToHandler(final @NonNull String identifier, final @NonNull MessageSender messageSender) throws UnknownHandlerException;
}
