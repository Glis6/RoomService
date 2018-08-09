package com.glis.network;

import com.glis.DomainController;
import com.glis.input.InputSender;
import com.glis.input.SimpleMetaData;
import com.glis.message.NetworkMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author Glis
 */
public final class ServerHandler extends ChannelInboundHandlerAdapter implements InputSender {
    /**
     * The {@link Logger} for this class.
     */
    private final Logger logger = Logger.getLogger(getClass().getName());

    /**
     * The {@link DomainController} to dispatch the traffic to.
     */
    private final DomainController domainController;

    /**
     * The channel that gets used to communicate.
     */
    private Channel channel;

    /**
     * All {@link Runnable}s to execute when the channel closes.
     */
    private final Set<Runnable> executeOnClose = new HashSet<>();

    /**
     * @param domainController The {@link DomainController} to dispatch the traffic to.
     */
    public ServerHandler(DomainController domainController) {
        this.domainController = domainController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("Received message as a " + msg.getClass().getSimpleName() + ", dispatching to " + DomainController.class.getSimpleName() + ".");
        domainController.handleInput(msg, new SimpleMetaData(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channel = ctx.channel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(NetworkMessage networkMessage) {
        if(channel != null) {
            channel.writeAndFlush(networkMessage);
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        executeOnClose.forEach(Runnable::run);
        logger.info("Channel has been unregistered.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClose(Runnable runnable) {
        executeOnClose.add(runnable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

    @Override
    public String getIdentifier() {
        return "TODO"; //TODO
    }
}
