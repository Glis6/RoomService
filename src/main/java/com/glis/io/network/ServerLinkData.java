package com.glis.io.network;

import com.glis.io.network.networktype.LinkData;
import lombok.Data;

/**
 * @author Glis
 */
@Data
public class ServerLinkData implements LinkData {
    /**
     * The name of the network.
     */
    private final String networkName;
}
