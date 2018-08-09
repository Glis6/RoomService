package com.glis.message;

import lombok.Data;

/**
 * @author Glis
 */
@Data
public class CurrentSongMessage implements Message {
    /**
     * The song that is currently playing.
     */
    private String currentSong;

    /**
     * @param currentSong The song that is currently playing.
     */
    public CurrentSongMessage(String currentSong) {
        this.currentSong = currentSong;
    }

    /**
     * A default constructor.
     */
    public CurrentSongMessage() {
    }
}
