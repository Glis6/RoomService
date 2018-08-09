package com.glis;

import com.glis.exceptions.UnknownHandlerException;
import com.glis.input.MetaData;
import com.glis.input.dispatcher.InputDispatcher;
import com.glis.memory.Memory;
import com.glis.output.MessageSender;
import com.glis.output.dispatcher.OutputDispatcher;
import com.glis.spotify.SpotifyController;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * @author Glis
 */
@Component
public class DomainController {
    /**
     * The {@link Logger} to use for this class.
     */
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    /**
     * The {@link InputDispatcher} used to send the input to the correct handler.
     */
    private final InputDispatcher inputDispatcher;

    /**
     * The {@link OutputDispatcher} used to link the output.
     */
    private final OutputDispatcher outputDispatcher;

    /**
     * The {@link SpotifyController} for this instance.
     */
    @Getter
    private final SpotifyController spotifyController;

    /**
     * The {@link Memory} of the application.
     */
    private final Memory<String, String> memory;

    /**
     * @param inputDispatcher The {@link InputDispatcher} used to send the input to the correct handler.
     * @param outputDispatcher The {@link OutputDispatcher} used to link the output.
     * @param memory The {@link Memory} of the application.
     */
    public DomainController(InputDispatcher inputDispatcher, OutputDispatcher outputDispatcher, Memory<String, String> memory) {
        this.inputDispatcher = inputDispatcher;
        this.outputDispatcher = outputDispatcher;
        this.spotifyController = new SpotifyController(memory);
        this.memory = memory;
    }

    /**
     * Sends the input through to all the handlers that are interested in it.
     *
     * @param input The input that is being send to the correct handler.
     * @param metaData The metadata to the message.
     */
    public void handleInput(Object input, MetaData metaData) throws UnknownHandlerException {
        inputDispatcher.dispatchToHandler(input, metaData);
    }

    /**
     * @param identifier The identifier that is being dispatched.
     * @param messageSender The {@link MessageSender} that is requesting the linking.
     */
    public void handleOutput(String identifier, MessageSender messageSender) throws UnknownHandlerException {
        outputDispatcher.dispatchToHandler(identifier, messageSender);
    }

    /**
     * Shuts down all applications, but not the connections.
     *
     * @param reason The reason why everything is being shut down.
     */
    public void shutdown(final @NonNull String reason) {
        logger.warning("Shutting down everything. Reason given: '" + reason + "'.");
        //TODO MAKE SHUTDOWN
    }
}
