package com.glis.io.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.FileInputStream;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * @author Glis
 */
public class FirebaseBootstrap {
    /**
     * The {@link Logger} for this class.
     */
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    /**
     * Binds the Firebase credentials to the app.
     */
    public void bind() throws Exception {
        final String firebaseUrl = Dotenv.load().get("firebaseUrl");
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(new FileInputStream(Objects.requireNonNull(Dotenv.load().get("accountKeyLocation")))))
                .setDatabaseUrl(firebaseUrl)
                .build();
        FirebaseApp.initializeApp(options);
        logger.info(String.format("Firebase bound to '%s'.", firebaseUrl));
    }
}
