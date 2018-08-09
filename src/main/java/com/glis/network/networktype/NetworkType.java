package com.glis.network.networktype;

import com.glis.ApplicationContextProvider;
import com.glis.DomainController;
import com.glis.network.NetworkTypeHandler;
import com.glis.network.ServerHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author Glis
 */
public interface NetworkType {
    /**
     * Makes the network connect to the new handlers.
     *
     * @param channelHandlerContext The channel context.
     */
    void passThrough(final ChannelHandlerContext channelHandlerContext);

    /**
     * Does all the neccessary networking to connect to the new handlers and disconnect the old ones.
     *
     * @param channelHandlerContext The channel context.
     */
    default void link(final ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.writeAndFlush(channelHandlerContext.alloc().buffer(1).writeByte(1));
        channelHandlerContext.pipeline().remove(NetworkTypeHandler.class.getTypeName());
        channelHandlerContext.pipeline().addLast(new ServerHandler(ApplicationContextProvider.getApplicationContext().getBean(DomainController.class)));
        passThrough(channelHandlerContext);
        channelHandlerContext.pipeline().fireChannelActive();
    }
}
