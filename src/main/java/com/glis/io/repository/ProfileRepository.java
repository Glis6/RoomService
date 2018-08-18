package com.glis.io.repository;

import com.glis.domain.model.Profile;
import io.reactivex.Observable;

import java.util.Optional;

/**
 * @author Glis
 */
public interface ProfileRepository extends Repository<Profile> {
    /**
     * Finds the key that fits the given key the best.
     * This is a heavier search so should be used with caution.
     *
     * @param key The key to look up.
     * @return An {@link Observable} created from looking up the key.
     */
    Observable<Optional<Profile>> getBestMatch(String key) throws Exception;
}
