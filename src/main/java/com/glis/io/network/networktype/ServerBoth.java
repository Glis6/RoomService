package com.glis.io.network.networktype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Glis
 */
@Component
public final class ServerBoth extends Both {
    /**
     * @param upstream   The {@link Upstream} to do the linking.
     * @param downstream The {@link Downstream} to do the linking.
     */
    @Autowired
    public ServerBoth(Upstream upstream, Downstream downstream) {
        super((channelHandlerContext, linkData) -> {}, upstream, downstream);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTypeIdentifier() {
        return 2;
    }
}
