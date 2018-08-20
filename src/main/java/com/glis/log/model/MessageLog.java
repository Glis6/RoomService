package com.glis.log.model;

import com.glis.io.network.input.InputSender;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Glis
 */
@Data
@EqualsAndHashCode(callSuper = false)
public final class MessageLog extends Log {
    /**
     * The type identifier of the message.
     */
    private final String typeIdentifier;

    /**
     * The meta data that was provided.
     */
    private final InputSender sender;

    /**
     * The payload of the message as a Json string.
     */
    private final String message;
}
