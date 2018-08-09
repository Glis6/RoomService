package com.glis.network.networktype;

import com.glis.network.codec.SubscribeMessageDecoder;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * @author Glis
 */
@Component
@TypeIdentifier(2)
public class Both implements NetworkType {
    /**
     * The {@link Logger} for this class.
     */
    private final Logger logger = Logger.getLogger(getClass().getName());

    /**
     * The {@link Upstream} object to link the upstream.
     */
    private final Upstream upstream;

    /**
     * The {@link Downstream} object to link the downstream.
     */
    private final Downstream downstream;

    /**
     *
     * @param upstream The {@link Upstream} object to link the upstream.
     * @param downstream The {@link Downstream} object to link the downstream.
     */
    @Autowired
    public Both(Upstream upstream, Downstream downstream) {
        this.upstream = upstream;
        this.downstream = downstream;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void passThrough(ChannelHandlerContext channelHandlerContext) {
        logger.info("Passing though to " + Upstream.class.getSimpleName() +  "...");
        upstream.passThrough(channelHandlerContext);
        logger.info("Passing though to " + Downstream.class.getSimpleName() +  "...");
        downstream.passThrough(channelHandlerContext);
        //We simply remove it here so we don't have to tamper with the non-shared code.
        channelHandlerContext.pipeline().remove(SubscribeMessageDecoder.class);
    }
}
