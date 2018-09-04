package com.glis.log.input;

import com.glis.domain.DomainController;
import com.glis.io.network.input.MetaData;
import com.glis.io.network.input.handlers.DomainControllerRequiredInputHandler;
import com.glis.io.network.input.handlers.HandlerPriority;
import com.glis.log.ChannelLogController;
import com.glis.message.Message;
import lombok.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author Glis
 */
@Component
@HandlerPriority(HandlerPriority.Priority.MONITOR)
public class MessageLogInputHandler extends DomainControllerRequiredInputHandler<Message> {
    /**
     * The {@link ChannelLogController} to write the logs to.
     */
    private final ChannelLogController channelLogController = domainController.getChannelLogController();

    /**
     * @param domainController The {@link DomainController} for this instance.
     */
    public MessageLogInputHandler(@NonNull final DomainController domainController) {
        super(domainController);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canHandle(Object o) {
        return o instanceof Message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Message convert(Object o) {
        return (Message)o;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object handleInput(Message message, MetaData metaData) {
        channelLogController.message(message, metaData);
        return null;
    }
}
