package com.glis.io.firebase.converter;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Glis
 */
public abstract class ObservableCollectionConverter<T, V> {
    /**
     * @param object The {@link T} that is being converted.
     * @param clazz The class of the {@link T}.
     * @return An {@link Observable} that can be observed to see the changes.
     */
    public final Observable<Optional<Collection<T>>> convert(final V object, final Class<T> clazz) throws Exception {
        final Subject<Optional<Collection<T>>> subject = BehaviorSubject.create();
        link(object, subject, clazz);
        return subject;
    }

    /**
     * @param object The object to link.
     * @param subject The subject that will become the observable.
     * @param clazz The class of the generic.
     */
    public abstract void link(V object, Subject<Optional<Collection<T>>> subject, Class<T> clazz) throws Exception;
}
