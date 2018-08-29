package com.glis.io.network;

import com.glis.io.network.networktype.LinkData;
import lombok.Data;

import java.util.List;

/**
 * @author Glis
 */
@Data
public class ServerLinkData implements LinkData {
    /**
     * The name of the network.
     */
    private final String networkName;

    /**
     * The subscriptions that the client is permitted.
     */
    private final List<String> subscriptions;
}
