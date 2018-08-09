package com.glis.message;

import lombok.Data;

/**
 * @author Glis
 */
@Data
public class CurrentSongNetworkMessage implements NetworkMessage {
    /**
     * The song that is currently playing.
     */
    private String currentSong;

    /**
     * @param currentSong The song that is currently playing.
     */
    public CurrentSongNetworkMessage(String currentSong) {
        this.currentSong = currentSong;
    }

    /**
     * A default constructor.
     */
    public CurrentSongNetworkMessage() {
    }
}
