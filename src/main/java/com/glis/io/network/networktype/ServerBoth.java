package com.glis.io.network.networktype;

/**
 * @author Glis
 */
public final class ServerBoth extends Both {
    /**
     * @param upstream   The {@link Upstream} to do the linking.
     * @param downstream The {@link Downstream} to do the linking.
     */
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
