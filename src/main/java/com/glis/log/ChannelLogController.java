package com.glis.log;

import com.glis.io.network.input.MetaData;
import com.glis.io.repository.LogRepository;
import com.glis.log.model.*;
import com.glis.message.Message;
import com.google.gson.Gson;
import lombok.NonNull;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Everything to do with the logging of channel activity.
 *
 * @author Glis
 */
public class ChannelLogController {
    /**
     * The {@link Gson} to use convert classes to JSON.
     */
    private final static Gson GSON = new Gson();

    /**
     * The {@link Logger} for this class.
     */
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    /**
     * The {@link LogRepository} that is used to log to.
     */
    private final LogRepository logRepository;

    /**
     * @param logRepository The {@link LogRepository} that is used to log to.
     */
    public ChannelLogController(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    /**
     * @param log The {@link Log} to be inserted.
     */
    private void insertLog(@NonNull final Log log) {
        try {
            this.logRepository.insert(log);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Something went wrong saving log to the repository.", e);
        }
    }

    /**
     * Logs an exception.
     *
     * @param throwable        The {@link Throwable} that describes the exception.
     * @param sourceClassName  The {@link Class} that the exception occurred in.
     * @param sourceMethodName The Method that the exception occurred in.
     * @param message          The message attached to the exception.
     * @param threadId         The if of the {@link Thread} that caused the exception.
     */
    public void exception(@NonNull final Throwable throwable, @NonNull final String sourceClassName, @NonNull final String sourceMethodName, @NonNull final String message, final int threadId) {
        if (sourceClassName.equals(getClass().getName())) {
            return;
        }
        insertLog(new ExceptionLog(new SavableThrowable(throwable), sourceClassName, sourceMethodName, message, threadId));
    }

    /**
     * Logs that an attempt at a connection was made.
     *
     * @param host The host that is attempting to connect.
     * @param port The port that is being connected to.
     */
    public void connectionAttempt(@NonNull final String host, @NonNull final String port) {
        insertLog(new ConnectionAttemptLog(host, port));
    }

    /**
     * Logs that a connection was established.
     *
     * @param remoteAddress The remote that is connected.
     * @param localAddress  The local address that is being connected to.
     * @param networkName   The name of the network that is connected.
     */
    public void connected(@NonNull final String remoteAddress, @NonNull final String localAddress, @NonNull final String networkName) {
        insertLog(new ConnectionLog(remoteAddress, localAddress, networkName));
    }

    /**
     * Logs that a connection was closed.
     *
     * @param remoteAddress The remote that is connected.
     * @param localAddress  The local address that is being connected to.
     * @param networkName   The name of the network that is connected.
     */
    public void disconnected(@NonNull final String remoteAddress, @NonNull final String localAddress, @NonNull final String networkName) {
        insertLog(new DisconnectionLog(remoteAddress, localAddress, networkName));
    }

    /**
     * @param message The {@link Message} that is being logged.
     */
    public void message(@NonNull final Message message, @NonNull final MetaData metaData) {
        insertLog(new MessageLog(message.getTypeIdentifier(), metaData.getInputSender(), GSON.toJson(message)));
    }

    /**
     * @param memoryLoad The load on the memory.
     * @param cpuLoad    The load on the CPU.
     */
    public void resourceUsage(final double memoryLoad, final double cpuLoad) {
        insertLog(new ResourceLog(memoryLoad, cpuLoad));
    }
}
