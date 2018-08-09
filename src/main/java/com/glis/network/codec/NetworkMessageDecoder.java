package com.glis.network.codec;

import com.glis.exceptions.InvalidTypeException;
import com.glis.message.NetworkMessage;
import com.glis.message.library.MessageLibrary;
import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Glis
 */
public class NetworkMessageDecoder extends ByteToMessageDecoder {

    /**
     * The length of the header.
     */
    private final static int HEADER_LENGTH = Integer.SIZE / Byte.SIZE;

    /**
     * The {@link Gson} that gets used for this instance.
     */
    private final static Gson GSON = new Gson();

    /**
     * The {@link Logger} for this class.
     */
    private final Logger logger = Logger.getLogger(getClass().getName());

    /**
     * The {@link MessageLibrary} to use to decode which message goes where.
     */
    private final MessageLibrary messageLibrary;

    /**
     * @param messageLibrary The {@link MessageLibrary} to use to decode which message goes where.
     */
    public NetworkMessageDecoder(MessageLibrary messageLibrary) {
        this.messageLibrary = messageLibrary;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        logger.info("Decoding message...");
        //First we check if we have a type header.
        if (in.readableBytes() < HEADER_LENGTH) {
            throw new Exception("Received a message with invalid length.");
        }

        //Then we decode the type header.
        byte[] typeHeader = new byte[HEADER_LENGTH];
        in.readBytes(typeHeader, 0, HEADER_LENGTH);
        ByteBuffer typeHeaderBuffer = ByteBuffer.allocate(HEADER_LENGTH).put(typeHeader, 0, HEADER_LENGTH);
        typeHeaderBuffer.rewind();
        final int typeLength = typeHeaderBuffer.getInt();

        //Then we check if the type is complete
        if(in.readableBytes() < typeLength) {
            throw new Exception("Received a message with invalid length.");
        }

        //Then we decode the type.
        byte[] encodedType = new byte[typeLength];
        in.readBytes(encodedType, 0, typeLength);
        final String type = new String(encodedType, StandardCharsets.UTF_8);

        logger.info("Decoded type as " + type);

        //Now we check the library to see if this is a valid type.
        try {
            final Class<? extends NetworkMessage> networkMessageClass = messageLibrary.getClassForIdentifier(type);

            //Next we decode the JSON string.
            //We start by taking the message header.
            if (in.readableBytes() < HEADER_LENGTH) {
                throw new Exception("Received a message with invalid length.");
            }

            //Then we decode the message header.
            byte[] messageHeader = new byte[HEADER_LENGTH];
            in.readBytes(messageHeader, 0, HEADER_LENGTH);
            ByteBuffer messageHeaderBuffer = ByteBuffer.allocate(HEADER_LENGTH).put(messageHeader, 0, HEADER_LENGTH);
            messageHeaderBuffer.rewind();
            final int messageLength = messageHeaderBuffer.getInt();

            //Then we check if the message is complete
            if(in.readableBytes() < messageLength) {
                throw new Exception("Received a message with invalid length.");
            }

            //Then we decode the message itself.
            byte[] encodedMessage = new byte[messageLength];
            in.readBytes(encodedMessage, 0, messageLength);
            final String messageAsJson = new String(encodedMessage, StandardCharsets.UTF_8);

            //Then we unpack the object from JSON.
            out.add(GSON.fromJson(messageAsJson, networkMessageClass));
        } catch(InvalidTypeException e) {
            logger.log(Level.WARNING, "An error was thrown when getting the message type.", e);
        } finally {
            in.clear();
        }
    }
}
