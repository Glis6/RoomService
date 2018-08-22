package com.glis.io.network;

import com.glis.io.network.codec.AuthorizationDecoder;
import com.glis.io.network.codec.AuthorizationResponseEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author Glis
 */
public final class ServerNetworkPipeline extends ChannelInitializer<SocketChannel> {
    /**
     * {@inheritDoc}
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        final ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addFirst(new AuthorizationDecoder());
        pipeline.addLast(new ServerAuthorizationHandler());
        pipeline.addLast(new AuthorizationResponseEncoder());
    }
}
