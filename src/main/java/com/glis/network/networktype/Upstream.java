package com.glis.network.networktype;

import com.glis.network.codec.NetworkMessageEncoder;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * @author Glis
 */
@Component
@TypeIdentifier(1)
public class Upstream implements NetworkType {
    /**
     * The {@link Logger} for this class.
     */
    private final Logger logger = Logger.getLogger(getClass().getName());

    /**
     * {@inheritDoc}
     */
    @Override
    public void passThrough(ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.pipeline().addLast(new NetworkMessageEncoder());
        logger.info(getClass().getSimpleName() + " has been linked.");
    }
}
