package com.glis.spotify;

import com.glis.domain.memory.Memory;
import com.glis.exceptions.InvalidKeyException;
import com.wrapper.spotify.SpotifyApi;
import io.reactivex.Observable;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.glis.spotify.SpotifyConstants.*;

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
     * Whether or not Spotify is enabled. Defaults to false.
     */
    private boolean spotifyEnabled = false;

    /**
     * @param memory The {@link Memory} of the application.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public SpotifyController(Memory<String, String> memory) throws Exception {
        this.memory = memory;
        this.spotifyApi = SpotifyApi
                .builder()
                .build();
        this.tokenManager = new TokenManager(spotifyApi, memory.getSharedObservableMemory());
        try {
            this.memory.getSharedObservableMemory()
                    .getObservable(SPOTIFY_ENABLED, Boolean.class)
                    .subscribe(optionalEnabled -> optionalEnabled.ifPresent(enabled -> {
                        if (!enabled) {
                            try {
                                //We get the current state before we set it to nothing.
                                final String lastSpotifyString = memory.getSharedMemory()
                                        .getState(LAST_SPOTIFY_STRING, String.class);
                                setCurrentSong("");

                                //We overwrite the memory that holds the nothing with the last state, so it resumes this after.
                                memory.getSharedMemory().setState(LAST_SPOTIFY_STRING, lastSpotifyString);
                            } catch (InvalidKeyException ignored) {
                            } catch (Exception e) {
                                logger.log(Level.SEVERE, "Failed to set the current song to nothing and save the last state.", e);
                            }
                            spotifyEnabled = false;
                            return;
                        }
                        try {
                            spotifyEnabled = true;
                            setCurrentSong(memory.getSharedMemory()
                                    .getState(LAST_SPOTIFY_STRING, String.class));
                        } catch (InvalidKeyException ignored) {
                        } catch (Exception e) {
                            logger.log(Level.SEVERE, "Could not load the '" + LAST_SPOTIFY_STRING + "'.", e);
                        }
                    }));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not load the '" + SPOTIFY_ENABLED + "' state.", e);
        }
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
        //Save the requested state to the memory for possible later use.
        try {
            memory.getSharedMemory().setState(LAST_SPOTIFY_STRING, songId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Something went wrong saving the last spotify string.", e);
        }

        //If we don't have spotify enabled then we'll just ignore it.
        if(!spotifyEnabled) {
            logger.info("Not sending Spotify playback because it is disabled.");
            return;
        }

        //This is just to give the right message, it doesn't make a difference in the output.
        if(!songId.isEmpty()) {
            logger.info("Setting current song to '" + songId + "'.");
        } else {
            logger.info("Stopping Spotify playback.");
        }
        try {
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
