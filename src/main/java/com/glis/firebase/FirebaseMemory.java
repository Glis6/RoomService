package com.glis.firebase;

import com.glis.memory.SharedObservableMemory;
import com.google.firebase.database.FirebaseDatabase;
import io.reactivex.Observable;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Glis
 */
public final class FirebaseMemory implements SharedObservableMemory<String> {
    /**
     * The {@link Logger} for this instance.
     */
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    /**
     * The {@link FirebaseDatabase} that we're reading from and writing to.
     */
    private final FirebaseDatabase firebaseDatabase;

    /**
     * @param firebaseDatabase The {@link FirebaseDatabase} that we're reading from and writing to.
     */
    public FirebaseMemory(FirebaseDatabase firebaseDatabase) {
        this.firebaseDatabase = firebaseDatabase;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Observable<T> getObservable(String key, Class<T> clazz) throws Exception {
        return new ObservableDatabaseObjectConverter<T>().convert(firebaseDatabase.getReference(key), clazz);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> void setValue(String key, T value) throws Exception {
        firebaseDatabase.getReference(key).setValue(value, (databaseError, databaseReference) -> {
            if(databaseError != null) {
                logger.log(Level.SEVERE, "Was unable to set a value in Firebase. Failed to set '" + value + "' with key '" + key + "'.");
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(String key) throws Exception {
        firebaseDatabase.getReference(key).removeValue((databaseError, databaseReference) -> {
            if(databaseError != null) {
                logger.log(Level.SEVERE, "Was unable to remove a value in Firebase with key '" + key + "'.");
            }
        });
    }
}
