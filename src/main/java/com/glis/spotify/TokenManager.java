package com.glis.spotify;

import com.glis.domain.memory.SharedObservableMemory;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.glis.spotify.SpotifyConstants.*;

/**
 * @author Glis
 */
class TokenManager {
    /**
     * The amount of seconds before a failed retry will try again.
     */
    private final static int RETRY_DELAY = 5;

    /**
     * The {@link Logger} for this class.
     */
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    /**
     * The {@link Executor} that will refresh our token.
     */
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    /**
     * The {@link SpotifyApi} that we're refreshing.
     */
    private final SpotifyApi spotifyApi;

    /**
     * The memory that we're writing the data to.
     */
    private final SharedObservableMemory<String> observableMemory;
    /**
     * The last available refresh token.
     */
    private String refreshToken;

    /**
     * The last available client id.
     */
    private String clientId;

    /**
     * The last available client secret
     */
    private String clientSecret;

    /**
     * @param spotifyApi       The {@link SpotifyApi} that we're refreshing.
     * @param observableMemory The memory that we're writing the data to.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    TokenManager(SpotifyApi spotifyApi, SharedObservableMemory<String> observableMemory) throws Exception {
        this.spotifyApi = spotifyApi;
        this.observableMemory = observableMemory;
        observableMemory.getObservable(REFRESH_TOKEN, String.class)
                .subscribe(refreshTokenOptional -> refreshTokenOptional
                        .ifPresent(refreshToken -> this.refreshToken = refreshToken));
        observableMemory.getObservable(CLIENT_ID, String.class)
                .subscribe(clientIdOptional -> clientIdOptional
                        .ifPresent(clientId -> this.clientId = clientId));
        observableMemory.getObservable(CLIENT_SECRET, String.class)
                .subscribe(clientSecretOptional -> clientSecretOptional
                        .ifPresent(clientSecret -> this.clientSecret = clientSecret));
        scheduleRefresh(5);
    }

    /**
     * Schedules a {@link TokenRefresh}.
     *
     * @param delay The delay before a new token is generated.
     */
    private void scheduleRefresh(long delay) {
        executor.schedule(new TokenRefresh(), delay, TimeUnit.SECONDS);
    }

    private class TokenRefresh implements Runnable {
        /**
         * {@inheritDoc}
         */
        @Override
        public void run() {
            try {
                if (refreshToken == null) {
                    throw new IllegalArgumentException("No refresh token yet.");
                }
                if(clientId == null) {
                    throw new IllegalArgumentException("No client id yet.");
                }
                if(clientSecret == null) {
                    throw new IllegalArgumentException("No client secret yet.");
                }
                logger.info("Attempting to refresh Spotify Access Token...");
                final AuthorizationCodeCredentials authorizationCodeCredentials = spotifyApi
                        .authorizationCodeRefresh(clientId, clientSecret, refreshToken)
                        .build()
                        .execute();

                //Save the access token and set it.
                observableMemory.setValue(ACCESS_TOKEN, authorizationCodeCredentials.getAccessToken());
                spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());

                final int secondsBeforeExpire = authorizationCodeCredentials.getExpiresIn();
                scheduleRefresh(secondsBeforeExpire);
                logger.info("Refreshing Spotify Access Token successful, next refresh is in " + secondsBeforeExpire / 60 + " minutes.");
            } catch (Exception e) {
                logger.log(Level.WARNING, "Failed to refresh Spotify Access Token, retrying in " + RETRY_DELAY + " seconds.", e);
                scheduleRefresh(RETRY_DELAY);
            }
        }
    }
}
