package com.glis.domain;

import com.glis.domain.memory.Memory;
import com.glis.domain.model.Profile;
import com.glis.exceptions.InvalidKeyException;
import com.glis.exceptions.UnknownHandlerException;
import com.glis.io.network.input.MetaData;
import com.glis.io.network.input.dispatcher.InputDispatcher;
import com.glis.io.network.output.MessageSender;
import com.glis.io.network.output.dispatcher.OutputDispatcher;
import com.glis.io.repository.RepositoryManager;
import com.glis.log.ChannelLogController;
import com.glis.spotify.SpotifyController;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.logging.Level;
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
     * The {@link ChannelLogController} that logs all channel activity.
     */
    @Getter
    private final ChannelLogController channelLogController;

    /**
     * The {@link RepositoryManager} that handles all database traffic.
     */
    private final RepositoryManager repositoryManager;

    /**
     * The {@link Memory} of the application.
     */
    private final Memory<String, String> memory;

    /**
     * The current amount of connected clients.
     */
    private int connectedClients = 0;

    /**
     * @param inputDispatcher   The {@link InputDispatcher} used to send the input to the correct handler.
     * @param outputDispatcher  The {@link OutputDispatcher} used to link the output.
     * @param memory            The {@link Memory} of the application.
     * @param repositoryManager The {@link RepositoryManager} that handles all database traffic.
     */
    public DomainController(InputDispatcher inputDispatcher, OutputDispatcher outputDispatcher, Memory<String, String> memory, RepositoryManager repositoryManager) throws Exception {
        this.inputDispatcher = inputDispatcher;
        this.outputDispatcher = outputDispatcher;
        this.spotifyController = new SpotifyController(memory);
        this.channelLogController = new ChannelLogController(repositoryManager.getLogRepository());
        this.memory = memory;
        this.repositoryManager = repositoryManager;
    }

    /**
     * Sends the input through to all the handlers that are interested in it.
     *
     * @param input    The input that is being send to the correct handler.
     * @param metaData The metadata to the message.
     */
    public void handleInput(Object input, MetaData metaData) throws UnknownHandlerException {
        inputDispatcher.dispatchToHandler(input, metaData);
    }

    /**
     * @param identifier    The identifier that is being dispatched.
     * @param messageSender The {@link MessageSender} that is requesting the linking.
     */
    public void handleOutput(String identifier, MessageSender messageSender) throws UnknownHandlerException {
        outputDispatcher.dispatchToHandler(identifier, messageSender);
    }

    /**
     * @param id The id of the profile that is being selected.
     */
    public void pickProfile(final String id) {
        logger.info("Disposing old profile observer...");
        try {
            final Disposable oldDisposable = memory.getSharedMemory().getState("currentProfileDisposable", Disposable.class);
            oldDisposable.dispose();
        } catch (InvalidKeyException ignored) {
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Something went wrong getting the currentProfileDisposable, potentially not disposed.", e);
        }
        logger.info("Old profile observer disposed.");
        logger.info("Loading profile from repository...");
        try {
            final Observable<Optional<Profile>> observableProfileOptional = id.contains(":") ? repositoryManager.getProfileRepository().getBestMatch(id) : repositoryManager.getProfileRepository().get(id);
            final Disposable disposable = observableProfileOptional.subscribe(profileOptional -> {
                final Profile profile = profileOptional.orElse(Profile.EMPTY_PROFILE);
                spotifyController.setCurrentSongs(profile.getSpotifySongIdentifiers());
            });
            memory.getSharedMemory().setState("currentProfileDisposable", disposable);
            logger.info("Profile loaded from repository.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Something went wrong setting the profile.", e);
        }
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

    /**
     * Notifies the domain that a connection was made.
     *
     * @param remoteAddress The remote that is connected.
     * @param localAddress The local address that is being connected to.
     * @param networkName The name of the network that is connected.
     */
    public void notifyConnected(@NonNull final String remoteAddress, @NonNull final String localAddress, @NonNull final String networkName) {
        getChannelLogController().connected(remoteAddress, localAddress, networkName);
        try {
            memory.getSharedObservableMemory().setValue("server_connections", Integer.toString(++connectedClients));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Something went wrong adjusting the connected value in memory.", e);
        }
    }

    /**
     * Notifies the domain that a connection was broken.
     *
     * @param remoteAddress The remote that is connected.
     * @param localAddress The local address that is being connected to.
     * @param networkName The name of the network that is connected.
     */
    public void notifyDisconnected(@NonNull final String remoteAddress, @NonNull final String localAddress, @NonNull final String networkName) {
        getChannelLogController().disconnected(remoteAddress, localAddress, networkName);
        try {
            memory.getSharedObservableMemory().setValue("server_connections", Integer.toString(--connectedClients));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Something went wrong adjusting the connected value in memory.", e);
        }
    }

    /**
     * Displays the server as online.
     */
    public void serverOnline(@NonNull String host, int port) {
        try {
            memory.getSharedObservableMemory().setValue("server_state", "online");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Something went wrong setting server state to online in memory.", e);
        }
        logger.info(String.format("Server is now online at '%s:%d' and accepting connections.", host, port));
    }
}
