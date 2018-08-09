package com.glis.output.handlers;

import com.glis.DomainController;
import com.glis.message.CurrentSongMessage;
import com.glis.output.MessageSender;
import com.glis.spotify.SpotifyController;
import io.reactivex.disposables.Disposable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * @author Glis
 */
@Component
public class CurrentSongOutputHandler implements OutputHandler {
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
    public CurrentSongOutputHandler(DomainController domainController) {
        this.spotifyController = domainController.getSpotifyController();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canHandle(String identifier) {
        return identifier.equals("spotify.currentSong");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(MessageSender messageSender) throws Exception {
        logger.info("Attempting to link current song...");
        final Disposable disposable = spotifyController.getCurrentSongObservable().subscribe(s -> messageSender.send(new CurrentSongMessage(s)));
        messageSender.onClose(disposable::dispose);
        logger.info("Current song linked.");
    }
}
