package com.glis.io.firebase.converter;

import com.glis.domain.model.Model;
import com.google.cloud.firestore.DocumentReference;
import io.reactivex.Observable;
import io.reactivex.subjects.Subject;

import java.util.Optional;

/**
 * A converter to make a database reference return a {@link Observable}.
 *
 * @author Glis
 */
public class ObservableDocumentReferenceConverter<T extends Model> extends ObservableConverter<T, DocumentReference> {
    /**
     * {@inheritDoc}
     */
    @Override
    public void link(final DocumentReference object, final Subject<Optional<T>> subject, final Class<T> clazz) throws NullPointerException {
        object.addSnapshotListener((documentSnapshot, e) -> {
            if(documentSnapshot == null || !documentSnapshot.exists()) {
                subject.onNext(Optional.empty());
                return;
            }
            final T convertedObject = documentSnapshot.toObject(clazz);
            if(convertedObject == null) {
                subject.onNext(Optional.empty());
                return;
            }
            subject.onNext(Optional.of(convertedObject.withId(documentSnapshot.getId())));
        });
    }
}
