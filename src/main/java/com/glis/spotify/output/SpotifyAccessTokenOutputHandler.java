package com.glis.spotify.output;

import com.glis.domain.DomainController;
import com.glis.io.network.output.MessageSender;
import com.glis.io.network.output.handlers.OutputHandler;
import com.glis.message.AccessTokenMessage;
import com.glis.spotify.SpotifyController;
import io.reactivex.disposables.Disposable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

import static com.glis.spotify.SpotifyConstants.SPOTIFY_ACCESS_TOKEN_REQUEST_STRING;

/**
 * @author Glis
 */
@Component
public class SpotifyAccessTokenOutputHandler implements OutputHandler {
    /**
     * The {@link Logger} for this class.
     */
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    /**
     * The {@link SpotifyController} to request the token from.
     */
    private final SpotifyController spotifyController;

    /**
     * @param domainController The {@link DomainController} for this instance.
     */
    @Autowired
    public SpotifyAccessTokenOutputHandler(DomainController domainController) {
        this.spotifyController = domainController.getSpotifyController();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canHandle(String identifier) {
        return identifier.equals(SPOTIFY_ACCESS_TOKEN_REQUEST_STRING);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(MessageSender messageSender) throws Exception {
        logger.info("Attempting to link Spotify access token...");
        final Disposable disposable = spotifyController.getAccessTokenObservable()
                .subscribe(accessTokenOptional ->
                        accessTokenOptional.ifPresent(accessToken ->
                                messageSender.send(new AccessTokenMessage(accessToken))));
        messageSender.onClose(disposable::dispose);
        logger.info("Spotify access token linked.");
    }
}
