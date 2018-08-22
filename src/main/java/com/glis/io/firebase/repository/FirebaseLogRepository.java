package com.glis.io.firebase.repository;

import com.glis.io.firebase.FirebasePushIdGenerator;
import com.glis.log.model.Log;
import com.glis.io.repository.LogRepository;
import com.google.cloud.firestore.Firestore;
import lombok.NonNull;

/**
 * @author Glis
 */
public class FirebaseLogRepository extends FirebaseRepository<Log> implements LogRepository {
    /**
     * @param firestore The {@link Firestore} that is used to access the database.
     * @param firebasePushIdGenerator The {@link FirebasePushIdGenerator} that generates the push ids.
     */
    public FirebaseLogRepository(@NonNull final Firestore firestore, @NonNull final FirebasePushIdGenerator firebasePushIdGenerator) {
        super(firestore, firebasePushIdGenerator, Log.class);
    }
}
