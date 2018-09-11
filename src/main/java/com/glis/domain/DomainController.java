package com.glis.domain;

import com.glis.domain.memory.Memory;
import com.glis.domain.model.Profile;
import com.glis.domain.model.ReadValuePair;
import com.glis.exceptions.InvalidKeyException;
import com.glis.exceptions.UnknownHandlerException;
import com.glis.io.network.input.MetaData;
import com.glis.io.network.input.dispatcher.InputDispatcher;
import com.glis.io.network.output.MessageSender;
import com.glis.io.network.output.dispatcher.OutputDispatcher;
import com.glis.io.repository.RepositoryManager;
import com.glis.led.LedController;
import com.glis.log.ChannelLogController;
import com.glis.security.SecurityController;
import com.glis.security.encryption.EncryptionStandard;
import com.glis.security.hash.HashingStandard;
import com.glis.spotify.SpotifyController;
import com.sun.management.OperatingSystemMXBean;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author Glis
 */
@Component
public class DomainController {
    /**
     * The maximum amount of server activity pairs that are kept.
     */
    private final static int MAXIMUM_SERVER_ACTIVITY = 50;

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
    private final OutputDispatcher subscriptionDispatcher;

    /**
     * The {@link SpotifyController} for this instance.
     */
    @Getter
    private final SpotifyController spotifyController;

    /**
     * The {@link LedController} for this instance.
     */
    @Getter
    private final LedController ledController;

    /**
     * The {@link ChannelLogController} that logs all channel activity.
     */
    @Getter
    private final ChannelLogController channelLogController;

    /**
     * The {@link SecurityController} to encrypt & hash objects.
     */
    @Getter
    private final SecurityController securityController;

    /**
     * The {@link RepositoryManager} that handles all database traffic.
     */
    private final RepositoryManager repositoryManager;

    /**
     * The {@link Memory} of the application.
     */
    private final Memory<String, String> memory;

    /**
     * A {@link Collection} of currently connected clients.
     */
    private Collection<String> connectedClients = new HashSet<>();

    /**
     * A {@link Queue} of the server activity.
     */
    private Queue<ReadValuePair> serverActivity = new ArrayDeque<>();

    /**
     * @param inputDispatcher    The {@link InputDispatcher} used to send the input to the correct handler.
     * @param outputDispatcher   The {@link OutputDispatcher} used to link the output.
     * @param memory             The {@link Memory} of the application.
     * @param repositoryManager  The {@link RepositoryManager} that handles all database traffic.
     * @param encryptionStandard The {@link EncryptionStandard} that encrypts the data.
     * @param hashingStandard    The {@link HashingStandard} that hashes the data.
     */
    @Autowired
    public DomainController(InputDispatcher inputDispatcher, OutputDispatcher outputDispatcher, Memory<String, String> memory, RepositoryManager repositoryManager, HashingStandard hashingStandard, EncryptionStandard encryptionStandard) throws Exception {
        this.inputDispatcher = inputDispatcher;
        this.subscriptionDispatcher = outputDispatcher;
        this.spotifyController = new SpotifyController(memory);
        this.ledController = new LedController(memory);
        this.channelLogController = new ChannelLogController(repositoryManager.getLogRepository());
        this.securityController = new SecurityController(hashingStandard, encryptionStandard);
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
        subscriptionDispatcher.dispatchToHandler(identifier, messageSender);
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
            final Observable<Optional<Profile>> observableProfileOptional = id.contains(":") ? repositoryManager.getProfileRepository().getBestMatch(id) : repositoryManager.getProfileRepository().getProfileByTag(id);
            final Disposable disposable = observableProfileOptional.subscribe(profileOptional -> {
                final Profile profile = profileOptional.orElse(Profile.EMPTY_PROFILE);
                if (profile.getSpotifySongIdentifiers() != null) {
                    spotifyController.setCurrentSongs(profile.getSpotifySongIdentifiers());
                } else {
                    spotifyController.setCurrentSong("");
                }
                if (profile.getLedSettings() != null) {
                    ledController.changeLedSettings(profile.getLedSettings());
                } else {
                    ledController.changeLedSettings("");
                }
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
     * @param localAddress  The local address that is being connected to.
     * @param networkName   The name of the network that is connected.
     */
    public void notifyConnected(@NonNull final String remoteAddress, @NonNull final String localAddress, @NonNull final String networkName) {
        getChannelLogController().connected(remoteAddress, localAddress, networkName);
        connectedClients.add(networkName);
        try {
            memory.getSharedObservableMemory().setValue("server_connections", String.join(";", connectedClients));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Something went wrong adjusting the connected value in memory.", e);
        }
    }

    /**
     * Notifies the domain that a connection was broken.
     *
     * @param remoteAddress The remote that is connected.
     * @param localAddress  The local address that is being connected to.
     * @param networkName   The name of the network that is connected.
     */
    public void notifyDisconnected(@NonNull final String remoteAddress, @NonNull final String localAddress, @NonNull final String networkName) {
        getChannelLogController().disconnected(remoteAddress, localAddress, networkName);
        connectedClients.remove(networkName);
        try {
            memory.getSharedObservableMemory().setValue("server_connections", String.join(";", connectedClients));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Something went wrong adjusting the connected value in memory.", e);
        }
    }

    /**
     * Displays the server as online.
     */
    public void serverOnline(int port) {
        try {
            memory.getSharedObservableMemory().setValue("server_state", "online");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Something went wrong setting server state to online in memory.", e);
        }
        logger.info(String.format("Server is now online on port %d and accepting connections.", port));
    }

    /**
     * Writes the thread activity to the memory.
     */
    public void writeServerMonitoring() {
        //First we'll save the threads activity.
        try {
            memory.getSharedObservableMemory().setValue("system_thread_activity", Thread.getAllStackTraces()
                    .keySet()
                    .stream()
                    .map(entry -> entry.getName() + "," + entry.getState())
                    .collect(Collectors.joining(";"))
            );
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Something went wrong attempting to adjust the thread activity.", e);
        }

        //Then we'll save the memory & CPU usage.
        try {
            OperatingSystemMXBean osMBean = ManagementFactory.newPlatformMXBeanProxy(
                    ManagementFactory.getPlatformMBeanServer(),
                    ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME,
                    OperatingSystemMXBean.class
            );
            final double totalMemory = osMBean.getTotalPhysicalMemorySize();
            final double usedMemory = totalMemory - osMBean.getFreePhysicalMemorySize();
            final double percentageMemoryUsed = (usedMemory / totalMemory) * 100;
            final double percentageCpuUsed = osMBean.getSystemCpuLoad() * 100;
            final ReadValuePair readValuePair = new ReadValuePair(percentageMemoryUsed, percentageCpuUsed);
            //Add it first.
            serverActivity.add(readValuePair);
            //Check if the length is over the maximum.
            if (serverActivity.size() > MAXIMUM_SERVER_ACTIVITY) {
                //If so remove one.
                serverActivity.poll();
            }
            memory.getSharedObservableMemory().setValue("system_resources_usage", serverActivity
                    .stream()
                    .map(pair -> pair.getMemoryUsage() + "," + pair.getCpuUsage())
                    .collect(Collectors.joining(";")));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Something went wrong attempting to adjust the cpu & memory load.", e);
        }
    }
}
