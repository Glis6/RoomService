package com.glis.network.codec;

import com.glis.message.NetworkMessage;
import com.google.gson.Gson;
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
public class NetworkMessageEncoder extends MessageToByteEncoder<NetworkMessage> {
    /**
     * The {@link Logger} for this class.
     */
    private final Logger logger = Logger.getLogger(getClass().getName());

    /**
     * The {@link Gson} that gets used for this instance.
     */
    private final static Gson GSON = new Gson();

    /**
     * {@inheritDoc}
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, NetworkMessage networkMessage, ByteBuf out) throws Exception {
        logger.info("Encoding message...");
        byte[] typeIdentifier = networkMessage.getTypeIdentifier().getBytes(UTF_8);
        byte[] typeHeader = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE).putInt(typeIdentifier.length).array();
        byte[] message = GSON.toJson(networkMessage).getBytes(UTF_8);
        byte[] messageHeader = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE).putInt(message.length).array();
        byte[] output = new byte[typeHeader.length + typeIdentifier.length + messageHeader.length + message.length];
        System.arraycopy(typeHeader, 0, output, 0, typeHeader.length);
        System.arraycopy(typeIdentifier, 0, output, typeHeader.length, typeIdentifier.length);
        System.arraycopy(messageHeader, 0, output, typeHeader.length + typeIdentifier.length, messageHeader.length);
        System.arraycopy(message, 0, output, messageHeader.length + typeHeader.length + typeIdentifier.length, message.length);
        out.writeBytes(output);
        logger.info("Message encoded as " + Arrays.toString(output));
    }
}
