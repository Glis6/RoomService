package com.glis.spotify.output;

import com.glis.domain.DomainController;
import com.glis.io.network.output.MessageSender;
import com.glis.io.network.output.handlers.OutputHandler;
import com.glis.message.PlaybackMessage;
import com.glis.message.StopPlaybackMessage;
import com.glis.spotify.SpotifyController;
import io.reactivex.disposables.Disposable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

import static com.glis.spotify.SpotifyConstants.SPOTIFY_PLAYBACK_REQUEST_STRING;

/**
 * @author Glis
 */
@Component
public class PlaybackOutputHandler implements OutputHandler {
    /**
     * The {@link Logger} for this class.
     */
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    /**
     * The {@link SpotifyController} to use.
     */
    private final SpotifyController spotifyController;

    /**
     * @param domainController The {@link DomainController} for this instance.
     */
    @Autowired
    public PlaybackOutputHandler(DomainController domainController) {
        this.spotifyController = domainController.getSpotifyController();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canHandle(String identifier) {
        return identifier.equals(SPOTIFY_PLAYBACK_REQUEST_STRING);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(MessageSender messageSender) throws Exception {
        logger.info("Attempting to link current playback...");
        final Disposable disposable = spotifyController.getPlaybackObservable()
                .subscribe(playbackOptional ->
                        playbackOptional
                                .ifPresent(playback -> messageSender.send(playback.isEmpty() ? new StopPlaybackMessage() : new PlaybackMessage(playback))));
        messageSender.onClose(disposable::dispose);
        logger.info("Current playback linked.");
    }
}
