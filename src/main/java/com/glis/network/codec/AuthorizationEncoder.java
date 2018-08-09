package com.glis.network.codec;

import com.glis.network.AuthorizationMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.logging.Logger;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author Glis
 */
public class AuthorizationEncoder extends MessageToByteEncoder<AuthorizationMessage> {
    /**
     * The {@link Logger} for this class.
     */
    private final Logger logger = Logger.getLogger(getClass().getName());

    /**
     * {@inheritDoc}
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, AuthorizationMessage authorizationMessage, ByteBuf out) throws Exception {
        logger.info("Encoding authorization...");
        byte[] type = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE).putInt(authorizationMessage.getNetworkType()).array();
        byte[] name = authorizationMessage.getNetworkName().getBytes(UTF_8);
        byte[] nameHeader = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE).putInt(name.length).array();
        byte[] output = new byte[type.length + name.length + nameHeader.length];

        System.arraycopy(type, 0, output, 0, type.length);
        System.arraycopy(nameHeader, 0, output, type.length, nameHeader.length);
        System.arraycopy(name, 0, output, type.length + nameHeader.length, name.length);

        out.writeBytes(output);
        logger.info("Authorization encoded as " + Arrays.toString(output));
    }
}
