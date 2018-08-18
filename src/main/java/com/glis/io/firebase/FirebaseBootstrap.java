package com.glis.io.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;

/**
 * @author Glis
 */
public class FirebaseBootstrap {
    /**
     * Binds the Firebase credentials to the app.
     */
    public void bind() throws Exception {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(new FileInputStream("ignored/serviceAccountKey.json"))) //TODO CONFIG FILE
                .setDatabaseUrl("https://roomservice-2018.firebaseio.com")
                .build();
        FirebaseApp.initializeApp(options);
    }
}
