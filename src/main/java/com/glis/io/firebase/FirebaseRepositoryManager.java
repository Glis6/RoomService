package com.glis.io.firebase;

import com.glis.domain.model.Profile;
import com.glis.io.repository.ProfileRepository;
import com.glis.io.repository.Repository;
import com.glis.io.repository.RepositoryManager;
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
    private final ProfileRepository profileRepository = new FirebaseProfileRepository(firestore);
}
