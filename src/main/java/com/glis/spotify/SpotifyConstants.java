package com.glis.spotify;

/**
 * @author Glis
 */
public final class SpotifyConstants {
    /**
     * A prefix that is added to everything to do with Spotify.
     */
    private final static String SPOTIFY_PREFIX = "spotify_";

    /**
     * The key for the currently playing song.
     */
    final static String PLAYBACK_KEY = SPOTIFY_PREFIX + "playback";

    /**
     * The key for the currently playing song.
     */
    final static String ACCESS_TOKEN = SPOTIFY_PREFIX + "accessToken";

    /**
     * The key for the currently playing song.
     */
    final static String REFRESH_TOKEN = SPOTIFY_PREFIX + "refreshToken";

    /**
     * The key for the currently playing song.
     */
    final static String CLIENT_ID = SPOTIFY_PREFIX + "clientId";

    /**
     * The key for the currently playing song.
     */
    final static String CLIENT_SECRET = SPOTIFY_PREFIX + "clientSecret";

    /**
     * The key for whether or not spotify is enabled.
     */
    public final static String SPOTIFY_ENABLED = SPOTIFY_PREFIX + "enabled";

    /**
     * The key for the last spotify string that was given.
     */
    public final static String LAST_SPOTIFY_STRING = SPOTIFY_PREFIX + "lastSpotifyString";

    /**
     * The string that gets sent when requesting a Spotify access token.
     */
    public final static String SPOTIFY_ACCESS_TOKEN_REQUEST_STRING = "spotify.accessToken";

    /**
     * The string that gets sent when requesting the playback feed.
     */
    public final static String SPOTIFY_PLAYBACK_REQUEST_STRING = "spotify.playback";
}
