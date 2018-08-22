package com.glis.io.network.networktype;

import com.glis.io.network.codec.SubscribeMessageDecoder;
import com.glis.io.network.networktype.Both;
import com.glis.io.network.networktype.Downstream;
import com.glis.io.network.networktype.Upstream;

import java.util.NoSuchElementException;

/**
 * @author Glis
 */
public final class ServerBoth extends Both {
    /**
     * @param upstream   The {@link Upstream} to do the linking.
     * @param downstream The {@link Downstream} to do the linking.
     */
    public ServerBoth(Upstream upstream, Downstream downstream) {
        super((channelHandlerContext, linkData) -> {
            try {
                channelHandlerContext.pipeline().remove(SubscribeMessageDecoder.class);
            } catch (NoSuchElementException ignored) {}
        }, upstream, downstream);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTypeIdentifier() {
        return 2;
    }
}
