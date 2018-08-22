package com.glis.io.firebase.converter;

import com.glis.domain.model.Model;
import com.google.cloud.firestore.Query;
import io.reactivex.Observable;
import io.reactivex.subjects.Subject;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * A converter to make a database reference return a {@link Observable}.
 *
 * @author Glis
 */
public class ObservableQueryConverter<T extends Model> extends ObservableCollectionConverter<T, Query> {
    /**
     * {@inheritDoc}
     */
    @Override
    public void link(final Query object, final Subject<Optional<Collection<T>>> subject, final Class<T> clazz) throws NullPointerException {
        object.addSnapshotListener((queryDocumentSnapshots, e) -> {
            if(queryDocumentSnapshots == null || !queryDocumentSnapshots.isEmpty()) {
                subject.onNext(Optional.empty());
                return;
            }
            final Set<T> convertedObjects = new HashSet<>();
            queryDocumentSnapshots.getDocuments().forEach(queryDocumentSnapshot -> {
                final T convertedObject = queryDocumentSnapshot.toObject(clazz);
                convertedObjects.add(convertedObject.withId(queryDocumentSnapshot.getId()));
            });
            if(convertedObjects.isEmpty()) {
                subject.onNext(Optional.empty());
                return;
            }
            subject.onNext(Optional.of(convertedObjects));
        });
    }
}
