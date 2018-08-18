package com.glis.spotify;

import com.glis.domain.memory.Memory;
import com.wrapper.spotify.SpotifyApi;
import io.reactivex.Observable;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.glis.spotify.SpotifyConstants.ACCESS_TOKEN;
import static com.glis.spotify.SpotifyConstants.PLAYBACK_KEY;

/**
 * @author Glis
 */
public final class SpotifyController {
    /**
     * The string that is used to split the command in multiple parts.
     */
    private final static String COMMAND_SPLIT_STRING = ";";

    /**
     * The {@link Logger} to use for this class.
     */
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    /**
     * The {@link TokenManager} that managed the access token.
     */
    private final TokenManager tokenManager;

    /**
     * The {@link SpotifyApi} to use.
     */
    private final SpotifyApi spotifyApi;

    /**
     * The {@link Memory} of the application.
     */
    private final Memory<String, String> memory;

    /**
     * @param memory The {@link Memory} of the application.
     */
    public SpotifyController(Memory<String, String> memory) throws Exception {
        this.memory = memory;
        this.spotifyApi = SpotifyApi
                .builder()
                .build();
        this.tokenManager = new TokenManager(spotifyApi, memory.getSharedObservableMemory());
    }

    /**
     * @return The current access token for Spotify.
     */
    public Observable<Optional<String>> getAccessTokenObservable() throws Exception {
        return memory.getSharedObservableMemory().getObservable(ACCESS_TOKEN, String.class);
    }

    /**
     * Sets the currently playing song.
     *
     * @param songId The song that is going to be played next.
     */
    public void setCurrentSong(String songId) {
        try {
            logger.info("Setting current song to '" + songId + "'.");
            memory.getSharedObservableMemory().setValue(PLAYBACK_KEY, songId);
        } catch(ClassCastException e) {
            logger.log(Level.WARNING, "'" + PLAYBACK_KEY + "' is saved in a wrong state.", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Something went wrong setting the next song.", e);
        }
    }

    /**
     * Sets the currently playing songs.
     *
     * @param songIds The songs that are going to be played next.
     */
    public void setCurrentSongs(List<String> songIds) {
        setCurrentSong(String.join(COMMAND_SPLIT_STRING, songIds));
    }

    /**
     * @return The currently playing song as an observable.
     */
    public Observable<Optional<String>> getPlaybackObservable() throws Exception {
        return memory.getSharedObservableMemory().getObservable(PLAYBACK_KEY, String.class);
    }
}
