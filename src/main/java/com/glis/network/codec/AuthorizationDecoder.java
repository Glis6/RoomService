package com.glis.network.codec;

import com.glis.network.AuthorizationMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Glis
 */
public class AuthorizationDecoder extends ByteToMessageDecoder {

    /**
     * The length of the header.
     */
    private final static int HEADER_LENGTH = Integer.SIZE / Byte.SIZE;

    /**
     * The {@link Logger} for this class.
     */
    private final Logger logger = Logger.getLogger(getClass().getName());

    /**
     * {@inheritDoc}
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        logger.info("Decoding message...");
        //First we check if we have a type header.
        if (in.readableBytes() < HEADER_LENGTH) {
            throw new Exception("Received a message with invalid length.");
        }

        byte[] typeInBytes = new byte[HEADER_LENGTH];
        in.readBytes(typeInBytes, 0, HEADER_LENGTH);
        ByteBuffer typeBuffer = ByteBuffer.allocate(HEADER_LENGTH).put(typeInBytes, 0, HEADER_LENGTH);
        typeBuffer.rewind();
        final int type = typeBuffer.getInt();

        //See if we have enough space to read the second header.
        if (in.readableBytes() < HEADER_LENGTH) {
            throw new Exception("Received a message with invalid length.");
        }
        byte[] nameHeader = new byte[HEADER_LENGTH];
        in.readBytes(nameHeader, 0, HEADER_LENGTH);
        ByteBuffer nameHeaderBuffer = ByteBuffer.allocate(HEADER_LENGTH).put(nameHeader, 0, HEADER_LENGTH);
        nameHeaderBuffer.rewind();
        final int nameLength = nameHeaderBuffer.getInt();

        if (in.readableBytes() < nameLength) {
            throw new Exception("Received a message with invalid length.");
        }

        //Then we decode the name.
        byte[] encodedName = new byte[nameLength];
        in.readBytes(encodedName, 0, nameLength);
        final String name = new String(encodedName, StandardCharsets.UTF_8);
        out.add(new AuthorizationMessage(type, name));
    }
}
