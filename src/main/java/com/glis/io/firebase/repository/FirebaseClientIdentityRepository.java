package com.glis.io.firebase.repository;

import com.glis.domain.model.ClientIdentity;
import com.glis.io.firebase.FirebasePushIdGenerator;
import com.glis.io.repository.ClientIdentityRepository;
import com.google.cloud.firestore.Firestore;

/**
 * @author Glis
 */
public final class FirebaseClientIdentityRepository extends FirebaseRepository<ClientIdentity> implements ClientIdentityRepository {
    /**
     * @param firestore The {@link Firestore} that is used to access the database.
     * @param firebasePushIdGenerator The {@link FirebasePushIdGenerator} that generates push ids for new objects.
     */
    public FirebaseClientIdentityRepository(Firestore firestore, FirebasePushIdGenerator firebasePushIdGenerator) {
        super(firestore, firebasePushIdGenerator, ClientIdentity.class);
    }
}
