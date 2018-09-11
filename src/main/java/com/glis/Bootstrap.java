package com.glis;

import com.glis.domain.DomainController;
import com.glis.io.network.ServerNetworkPipeline;
import io.github.cdimascio.dotenv.Dotenv;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Objects;

/**
 * @author Glis
 */
public class Bootstrap {
    /**
     * The {@link DomainController} for this instance.
     */
    private final DomainController domainController;

    /**
     * @param domainController The {@link DomainController} for this instance.
     */
    public Bootstrap(DomainController domainController) {
        this.domainController = domainController;
    }

    /**
     * Turns on the server.
     */
    void bind() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            final int port = Integer.valueOf(Objects.requireNonNull(Dotenv.load().get("port")));
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerNetworkPipeline())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture channelFuture = serverBootstrap
                    .bind(port)
                    .sync();
            domainController.serverOnline(port);
            channelFuture.channel()
                    .closeFuture()
                    .sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
