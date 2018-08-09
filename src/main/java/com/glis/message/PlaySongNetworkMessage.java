package com.glis.message;

import lombok.Data;

/**
 * @author Glis
 */
@Data
public class PlaySongNetworkMessage implements NetworkMessage {
    /**
     * The song that is gonna be played.
     */
    private String songToPlay;
}
