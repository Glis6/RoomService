package com.glis.network;

import com.glis.ApplicationContextProvider;
import com.glis.exceptions.InvalidTypeException;
import com.glis.network.networktype.NetworkType;
import com.glis.network.networktype.TypeIdentifier;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Glis
 */
public final class NetworkTypeHandler extends ChannelInboundHandlerAdapter {
    /**
     * The {@link Logger} for this class.
     */
    private final Logger logger = Logger.getLogger(getClass().getName());

    /**
     * All network types we support.
     */
    private final Map<Integer, NetworkType> networkTypes;

    /**
     * Creates an instance with all beans that are
     */
    NetworkTypeHandler() {
        ApplicationContext context = ApplicationContextProvider.getApplicationContext();
        final Map<Integer, NetworkType> networkTypes = new HashMap<>();
        for (NetworkType networkType : context.getBeansOfType(NetworkType.class).values()) {
            if(!networkType.getClass().isAnnotationPresent(TypeIdentifier.class)) {
                logger.warning(NetworkType.class.getSimpleName() + " found without a type. Please add the " + TypeIdentifier.class.getSimpleName() + " annotation.");
                continue;
            }
            networkTypes.put(networkType.getClass().getAnnotation(TypeIdentifier.class).value(), networkType);
        }
        this.networkTypes = networkTypes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        try {
            if (byteBuf.isReadable()) {
                final int networkType = byteBuf.readInt();
                if (!networkTypes.containsKey(networkType)) {
                    throw new InvalidTypeException(String.format("The given type does not exist. Received: %d", networkType));
                }
                logger.info("Found a matching " + NetworkType.class.getSimpleName() + ", linking...");
                networkTypes.get(networkType).link(ctx);
            }
        } finally {
            byteBuf.release();
        }
    }

}
