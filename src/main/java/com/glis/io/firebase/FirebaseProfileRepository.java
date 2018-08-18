package com.glis.io.firebase;

import com.glis.domain.model.Profile;
import com.glis.io.repository.ProfileRepository;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

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
     */
    FirebaseProfileRepository(Firestore firestore) {
        super(firestore, Profile.class);
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
