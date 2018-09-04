package com.glis.log;

import com.glis.domain.DomainController;
import com.glis.io.repository.LogRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * A {@link Handler} that will write all exceptions thrown in logs to
 * a {@link com.glis.io.repository.Repository}.
 *
 * @author Glis
 */
@Service
public class RepositoryExceptionLogHandler extends Handler {
    /**
     * The {@link Logger} used in this class.
     */
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    /**
     * The {@link LogRepository} to write to.
     */
    private final ChannelLogController channelLogController;

    /**
     * @param domainController The {@link DomainController} for this instance.
     */
    @Autowired
    public RepositoryExceptionLogHandler(@NonNull final DomainController domainController) {
        this.channelLogController = domainController.getChannelLogController();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void publish(LogRecord record) {
        if(record.getThrown() != null) {
            channelLogController.exception(record.getThrown(), record.getSourceClassName(), record.getSourceMethodName(), record.getMessage(), record.getThreadID());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void flush() {
        logger.log(Level.INFO, "Flush was called on the " + getClass().getSimpleName() + ".");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws SecurityException {
        logger.log(Level.INFO, "Close was called on the " + getClass().getSimpleName() + ".");
    }
}
