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
     * The {@link FirebasePushIdGenerator} that generates push ids for new objects.
     */
    protected final FirebasePushIdGenerator firebasePushIdGenerator;

    /**
     * The class of the {@link T}.
     */
    private final Class<T> clazz;

    /**
     * @param firestore The {@link Firestore} that is used to access the database.
     * @param firebasePushIdGenerator The {@link FirebasePushIdGenerator} that generates push ids for new objects.
     * @param clazz The class of the {@link T}.
     */
    FirebaseRepository(final Firestore firestore, final FirebasePushIdGenerator firebasePushIdGenerator, Class<T> clazz) {
        this.collectionReference = firestore.collection(clazz.getSimpleName().toLowerCase());
        this.firebasePushIdGenerator = firebasePushIdGenerator;
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
        if(object.id == null) {
            object.id = firebasePushIdGenerator.generate();
        }
        collectionReference.document(object.getId()).set(object);
    }
}
