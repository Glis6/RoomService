package com.glis.spotify;

import com.glis.memory.Memory;
import com.wrapper.spotify.SpotifyApi;
import io.reactivex.Observable;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.glis.spotify.SpotifyConstants.CURRENTLY_PLAYING_SONG_KEY;

/**
 * @author Glis
 */
public final class SpotifyController {
    /**
     * The {@link Logger} to use for this class.
     */
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    /**
     * The {@link SpotifyApi} to use.
     */
    private SpotifyApi spotifyApi;

    /**
     * The {@link Memory} of the application.
     */
    private final Memory<String, String> memory;

    /**
     * @param memory The {@link Memory} of the application.
     */
    public SpotifyController(Memory<String, String> memory) {
        this.memory = memory;
    }

    /**
     * Sets the currently playing song.
     *
     * @param songId The song that is going to be played next.
     */
    public void setCurrentSong(String songId) {
        try {
            memory.getSharedObservableMemory().setValue(CURRENTLY_PLAYING_SONG_KEY, songId);
        } catch(ClassCastException e) {
            logger.log(Level.WARNING, "'" + CURRENTLY_PLAYING_SONG_KEY + "' is saved in a wrong state.", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Something went wrong setting the next song.", e);
        }
    }

    /**
     * @return The currently playing song as an observable.
     */
    public Observable<String> getCurrentSongObservable() throws Exception {
        return memory.getSharedObservableMemory().getObservable(CURRENTLY_PLAYING_SONG_KEY, String.class);
    }
}
