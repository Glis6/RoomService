package com.glis.firebase;

import com.google.firebase.database.*;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

/**
 * A converter to make a database reference return a {@link Observable}.
 *
 * @author Glis
 */
class ObservableDatabaseObjectConverter<T> {
    /**
     * @param databaseReference The {@link DatabaseReference} that will become the observable.
     * @param clazz The class the observable is.
     * @return An {@link Observable} that watched the {@link DatabaseReference}.
     */
    Observable<T> convert(final DatabaseReference databaseReference, Class<T> clazz) {
        final Subject<T> subject = BehaviorSubject.create();
        databaseReference
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        subject.onNext(dataSnapshot.getValue(clazz));
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        subject.onComplete();
                    }
                });
        return subject;
    }
}
