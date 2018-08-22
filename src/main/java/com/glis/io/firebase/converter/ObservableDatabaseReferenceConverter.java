package com.glis.io.firebase.converter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import io.reactivex.Observable;
import io.reactivex.subjects.Subject;

import java.util.Optional;

/**
 * A converter to make a database reference return a {@link Observable}.
 *
 * @author Glis
 */
public class ObservableDatabaseReferenceConverter<T> extends ObservableConverter<T, DatabaseReference> {
    /**
     * {@inheritDoc}
     */
    @Override
    public void link(DatabaseReference object, Subject<Optional<T>> subject, Class<T> clazz) throws Exception {
        object.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot == null || !dataSnapshot.exists()) {
                    subject.onNext(Optional.empty());
                    return;
                }
                final T value = dataSnapshot.getValue(clazz);
                if (value == null) {
                    subject.onNext(Optional.empty());
                    return;
                }
                subject.onNext(Optional.of(value));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                subject.onComplete();
            }
        });
    }
}
