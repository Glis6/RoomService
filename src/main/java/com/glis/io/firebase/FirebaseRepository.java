package com.glis.io.firebase;

import com.glis.domain.model.Model;
import com.glis.io.repository.Repository;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import io.reactivex.Observable;

import java.util.Optional;

/**
 * @author Glis
 */
public class FirebaseRepository<T extends Model> implements Repository<T> {
    /**
     * The {@link CollectionReference} for this type.
     */
    protected final CollectionReference collectionReference;

    /**
     * The class of the {@link T}.
     */
    private final Class<T> clazz;

    /**
     * @param firestore The {@link Firestore} that is used to access the database.
     * @param clazz The class of the {@link T}.
     */
    FirebaseRepository(final Firestore firestore, Class<T> clazz) {
        this.collectionReference = firestore.collection(clazz.getSimpleName().toLowerCase());
        this.clazz = clazz;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Observable<Optional<T>> get(String key) throws Exception {
        return new ObservableDocumentReferenceConverter<T>().convert(collectionReference.document(key), clazz);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(T object) throws Exception {
        collectionReference.add(object);
    }
}
