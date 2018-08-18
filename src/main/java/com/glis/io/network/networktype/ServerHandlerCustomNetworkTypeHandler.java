package com.glis.io.network.networktype;

import com.glis.domain.DomainController;
import com.glis.io.network.ServerHandler;
import com.glis.io.network.codec.AuthorizationDecoder;
import com.glis.io.network.server.ServerAuthorizationHandler;
import com.glis.io.network.server.ServerLinkData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;

import java.util.NoSuchElementException;

/**
 * @author Glis
 */
public class ServerHandlerCustomNetworkTypeHandler implements CustomNetworkTypeHandler {
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
        channelHandlerContext.writeAndFlush(channelHandlerContext.alloc().buffer(1).writeByte(1));
        final ChannelPipeline pipeline = channelHandlerContext.pipeline();
        try {
            pipeline.remove(AuthorizationDecoder.class);
            pipeline.remove(ServerAuthorizationHandler.class);
        } catch (NoSuchElementException ignored) {}
        String networkName = "Unknown";
        if (linkData instanceof ServerLinkData) {
            final ServerLinkData serverLinkData = (ServerLinkData) linkData;
            networkName = serverLinkData.getNetworkName();
        }
        pipeline.addFirst(new ServerHandler(domainController, networkName));
    }
}
