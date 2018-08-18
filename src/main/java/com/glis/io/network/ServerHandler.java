package com.glis.io.network;

import com.glis.domain.DomainController;
import com.glis.io.network.input.InputSender;
import com.glis.io.network.input.SimpleMetaData;
import com.glis.message.Message;
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
     * The name of this network connection.
     */
    private final String networkName;

    /**
     * All {@link Runnable}s to execute when the channel closes.
     */
    private final Set<Runnable> executeOnClose = new HashSet<>();

    /**
     * @param domainController The {@link DomainController} to dispatch the traffic to.
     */
    public ServerHandler(DomainController domainController, final String networkName) {
        this.domainController = domainController;
        this.networkName = networkName;
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
    public void send(Message message) {
        if(channel != null) {
            channel.writeAndFlush(message);
        }
    }

    /**
     * {@inheritDoc}
     */
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
        cause.printStackTrace();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIdentifier() {
        return networkName;
    }
}
