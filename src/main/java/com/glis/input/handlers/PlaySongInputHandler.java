package com.glis.input.handlers;

import com.glis.DomainController;
import com.glis.input.MetaData;
import com.glis.message.PlaySongMessage;
import com.glis.spotify.SpotifyController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Glis
 */
@Component
public class PlaySongInputHandler implements InputHandler<PlaySongMessage> {
    /**
     * The {@link SpotifyController} for this instance.
     */
    private final SpotifyController spotifyController;

    /**
     * @param domainController The {@link DomainController} for this instance.
     */
    @Autowired
    public PlaySongInputHandler(DomainController domainController) {
        this.spotifyController = domainController.getSpotifyController();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canHandle(Object o) {
        return o instanceof PlaySongMessage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlaySongMessage convert(Object o) {
        return (PlaySongMessage)o;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object handleInput(PlaySongMessage input, MetaData metaData) {
        spotifyController.setCurrentSong(input.getSongToPlay());
        return null;
    }
}
