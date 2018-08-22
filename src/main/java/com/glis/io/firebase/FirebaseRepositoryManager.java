package com.glis.io.firebase;

import com.glis.domain.model.ClientIdentity;
import com.glis.domain.model.Profile;
import com.glis.io.firebase.repository.FirebaseClientIdentityRepository;
import com.glis.io.firebase.repository.FirebaseLogRepository;
import com.glis.io.firebase.repository.FirebaseProfileRepository;
import com.glis.io.repository.*;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import lombok.Data;

/**
 * @author Glis
 */
@Data
public final class FirebaseRepositoryManager implements RepositoryManager {
    /**
     * The {@link Firestore} that is being used.
     */
    private final Firestore firestore = FirestoreClient.getFirestore();

    /**
     * The {@link Repository} that handles all {@link Profile} traffic.
     */
    private final ProfileRepository profileRepository;

    /**
     * The {@link Repository} that handles all {@link Profile} traffic.
     */
    private final LogRepository logRepository;

    /**
     * The {@link Repository} that handles all {@link ClientIdentity} traffic.
     */
    private final ClientIdentityRepository clientIdentityRepository;

    /**
     * @param firebasePushIdGenerator The {@link FirebasePushIdGenerator} that generates the push ids.
     */
    public FirebaseRepositoryManager(FirebasePushIdGenerator firebasePushIdGenerator) {
        profileRepository =  new FirebaseProfileRepository(firestore, firebasePushIdGenerator);
        logRepository = new FirebaseLogRepository(firestore, firebasePushIdGenerator);
        clientIdentityRepository = new FirebaseClientIdentityRepository(firestore, firebasePushIdGenerator);
    }
}
