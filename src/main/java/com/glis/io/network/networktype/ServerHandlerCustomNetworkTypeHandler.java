package com.glis.io.network.networktype;

import com.glis.domain.DomainController;
import com.glis.io.network.ServerAuthorizationHandler;
import com.glis.io.network.ServerHandler;
import com.glis.io.network.ServerLinkData;
import com.glis.io.network.codec.AuthorizationDecoder;
import com.glis.io.network.codec.AuthorizationResponseEncoder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;

import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Glis
 */
public class ServerHandlerCustomNetworkTypeHandler implements CustomNetworkTypeHandler {
    /**
     * The {@link Logger} for this class.
     */
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    /**
     * The {@link DomainController} that is used for this instance.
     */
    private final DomainController domainController;

    /**
     * @param domainController The {@link DomainController} that is used for this instance.
     */
    public ServerHandlerCustomNetworkTypeHandler(DomainController domainController) {
        this.domainController = domainController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doCustom(ChannelHandlerContext channelHandlerContext, LinkData linkData) {
        final ChannelPipeline pipeline = channelHandlerContext.pipeline();
        try {
            pipeline.remove(ServerAuthorizationHandler.class);
        } catch (NoSuchElementException ignored) {}
        try {
            pipeline.remove(AuthorizationResponseEncoder.class);
        } catch (NoSuchElementException ignored) {}
        try {
            pipeline.remove(AuthorizationDecoder.class);
        } catch (NoSuchElementException ignored) {}
        String networkName = "Unknown";
        if (linkData instanceof ServerLinkData) {
            final ServerLinkData serverLinkData = (ServerLinkData) linkData;
            networkName = serverLinkData.getNetworkName();
        }
        final ServerHandler serverHandler = new ServerHandler(domainController, networkName);
        pipeline.addFirst(serverHandler);
        try {
            serverHandler.channelRegistered(channelHandlerContext);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Was unable to register the channel for the server handler.", e);
        }
    }
}
