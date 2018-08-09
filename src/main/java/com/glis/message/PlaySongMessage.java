package com.glis.message;

import lombok.Data;

/**
 * @author Glis
 */
@Data
public class PlaySongMessage implements Message {
    /**
     * The song that is gonna be played.
     */
    private String songToPlay;
}
