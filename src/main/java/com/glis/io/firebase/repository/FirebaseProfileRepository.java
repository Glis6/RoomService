package com.glis.io.firebase.repository;

import com.glis.domain.model.Profile;
import com.glis.io.firebase.FirebasePushIdGenerator;
import com.glis.io.repository.ProfileRepository;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import lombok.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Glis
 */
public class FirebaseProfileRepository extends FirebaseRepository<Profile> implements ProfileRepository {
    /**
     * @param firestore The {@link Firestore} that is used to access the database.
     * @param firebasePushIdGenerator The {@link FirebasePushIdGenerator} that generates the push ids.
     */
    public FirebaseProfileRepository(@NonNull final Firestore firestore, @NonNull final FirebasePushIdGenerator firebasePushIdGenerator) {
        super(firestore, firebasePushIdGenerator, Profile.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Observable<Optional<Profile>> getBestMatch(String key) throws Exception {
        final String[] pieces = key.split(":");
        for (int i = pieces.length; i >= 0; i--) {
            final String searchTag = Arrays.stream(pieces).limit(i).collect(Collectors.joining(":"));
            final List<QueryDocumentSnapshot> documentSnapshots = collectionReference.whereEqualTo("tag", searchTag)
                    .get()
                    .get()
                    .getDocuments();
            if(!documentSnapshots.isEmpty()) {
                return get(documentSnapshots.stream().findAny().get().getId());
            }
        }
        return BehaviorSubject.create();
    }
}
